package com.otoil.dbcomparator.client.comparison;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.otoil.dbcomparator.client.resources.DBComparatorTemplates;
import com.otoil.dbcomparator.client.resources.icons.DBComparatorResources;
import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.ColumnNode;
import com.otoil.dbcomparator.shared.beans.ColumnsContainerNode;
import com.otoil.dbcomparator.shared.beans.ContainerNode;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;
import com.otoil.dbcomparator.shared.beans.TableNode;
import com.otoil.dbcomparator.shared.beans.TablesContainerNode;


/**
 * Кастомная модель для {@link CellTree}.<br>
 * <br>
 * Использование:<br>
 * <br>
 * <code><i>model = new CustomTreeModel();</i></code><br>
 * <code><i>model.setRoot() с валидным root</i></code><br>
 * <code><i>new CellTree(model, ...)</i></code>
 * 
 * @author Sergey Medelyan
 */
public class CustomCellTreeModel implements TreeViewModel
{
    private SingleSelectionModel<AbstractNode> selectionModel = new SingleSelectionModel<AbstractNode>();
    private DatabaseNode root;
    private static DBComparatorTemplates templates;

    static
    {
        DBComparatorResources.INSTANCE.css().ensureInjected();
        templates = GWT.create(DBComparatorTemplates.class);
    }

    public CustomCellTreeModel()
    {
    }

    public void addSelectionChangeHandler(
        CellTreeSelectionChangedHandler handler)
    {
        selectionModel.addSelectionChangeHandler(
            event -> handler.onSelectionChanged(selectionModel, event));
    }

    @Override
    public <T> NodeInfo<?> getNodeInfo(T value)
    {
        if (value == null)
        {
            // null = root value; return db node
            return getNodeInfo(new ArrayList<DatabaseNode>()
            {
                {
                    add(root);
                }
            });
        }
        else if (value instanceof DatabaseNode)
        {
            // return db containers
            return getNodeInfo(((DatabaseNode) value).getChildrenOfType(
                n -> n instanceof ContainerNode, n -> (ContainerNode) n));
        }

        // from here db containers are being handled
        else if (value instanceof TablesContainerNode)
        {
            // return tables
            return getNodeInfo(((TablesContainerNode) value).getChildrenOfType(
                n -> n instanceof TableNode, n -> (TableNode) n));
        }

        // from here db containers elements are being handled
        else if (value instanceof TableNode)
        {
            // return table containers
            return getNodeInfo(((TableNode) value).getChildren());
        }
        else if (value instanceof ColumnsContainerNode)
        {
            // return columns
            return getNodeInfo(((ColumnsContainerNode) value).getChildrenOfType(
                n -> n instanceof ColumnNode, n -> (ColumnNode) n));
        }

        return null;
    }

    /**
     * Конструирует {@link NodeInfo} для указанного типа узла по переданным
     * данным для {@link ListDataProvider}
     * 
     * @param dataForDataProvider данные для {@link ListDataProvider}
     * @return
     */
    private <T extends AbstractNode> NodeInfo<T> getNodeInfo(
        List<T> dataForDataProvider)
    {
        ListDataProvider<T> dataProvider = new ListDataProvider<T>(
            dataForDataProvider);
        AbstractCell<T> cell = new AbstractCell<T>()
        {
            @Override
            public void render(Context context, T value, SafeHtmlBuilder sb)
            {
                if (value != null)
                {
                    sb.append(new SafeHtml()
                    {

                        // тут мы оборачиваем имя в тег <span>,
                        // чтобы задать красивишный css-класс в зависимости
                        // от состояния узла
                        @Override
                        public String asString()
                        {
                            String cssClass = null;
                            switch (value.getState())
                            {
                                case NON_CHANGED:
                                    cssClass = "db-non-changed";
                                    break;
                                case ADDED:
                                    cssClass = "db-added";
                                    break;
                                case CHANGED:
                                    cssClass = "db-changed";
                                    break;
                                case DELETED:
                                    cssClass = "db-deleted";
                                    break;
                            }

                            String html = "";
                            if (value instanceof TableNode)
                            {

                                html = templates
                                    .treeItemIcon(DBComparatorResources.INSTANCE
                                        .css().tableSpriteClass())
                                    .asString();
                            }

                            html += templates
                                .treeItem(cssClass, value.getName()).asString();

                            if (value instanceof TableNode
                                && value.hasCommentary())
                            {
                                html += templates
                                    .treeItemComment(value.getCommentary())
                                    .asString();
                            }

                            return html;
                        }
                    });
                }
            }
        };

        return new DefaultNodeInfo<T>(dataProvider, cell, selectionModel, null);
    }

    private String getHtmlIconForNode(AbstractNode node)
    {
        if (node instanceof ContainerNode)
        {
            ContainerNode container = (ContainerNode) node;
            if (container.getSubelementsType().equals("table"))
            {
                return "<span class="
                    + DBComparatorResources.INSTANCE.css().tableSpriteClass()
                    + ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>";
            }
        }
        // else if (node instanceof ColumnNode)
        // {
        // ColumnNode column = (ColumnNode) node;
        //
        // }

        return "";
    }

    @Override
    public boolean isLeaf(Object value)
    {
        if (!(value instanceof AbstractNode) || value == null)
        {
            return false;
        }
        AbstractNode node = (AbstractNode) value;
        return node.getChildren().size() == 0;
    }

    public void setRoot(DatabaseNode root)
    {
        this.root = root;
    }

}
