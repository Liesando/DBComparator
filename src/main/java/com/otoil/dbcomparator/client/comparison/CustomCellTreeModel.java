package com.otoil.dbcomparator.client.comparison;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.otoil.dbcomparator.client.resources.internationalization.DBComparatorMessages;
import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.ColumnNode;
import com.otoil.dbcomparator.shared.beans.ColumnsContainerNode;
import com.otoil.dbcomparator.shared.beans.ContainerNode;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;
import com.otoil.dbcomparator.shared.beans.TableNode;
import com.otoil.dbcomparator.shared.beans.TablesContainerNode;
import com.otoil.dbcomparator.shared.beans.AbstractNode.NodeState;


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
    private static final DBComparatorResources resources = DBComparatorResources.INSTANCE;
    private SingleSelectionModel<AbstractNode> selectionModel = new SingleSelectionModel<AbstractNode>();
    private static DBComparatorTemplates templates;
    private static DBComparatorMessages messages;

    private DatabaseNode root;
    private boolean hideNonChanged;

    static
    {
        resources.css().ensureInjected();
        templates = GWT.create(DBComparatorTemplates.class);
        messages = GWT.create(DBComparatorMessages.class);
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
            skipNonChanged(dataForDataProvider));
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

                            // first icon goes if any
                            String html = getHtmlIconForNode(value);

                            // then content goes
                            html += templates
                                .treeItem(cssClass,
                                    value.getLocalizedName())
                                .asString();

                            // in the end comments go
                            if (value.hasCommentary())
                            {
                                html += templates
                                    .treeItemComment(value.getCommentary())
                                    .asString();
                            }

                            // done
                            return html;
                        }
                    });
                }
            }
        };

        return new DefaultNodeInfo<T>(dataProvider, cell, selectionModel, null);
    }

    private <T extends AbstractNode> List<T> skipNonChanged(List<T> children)
    {
        if (hideNonChanged)
        {
            return children.stream()
                .filter(c -> c.getState() != NodeState.NON_CHANGED)
                .collect(Collectors.toList());
        }
        else
        {
            return children;
        }
    }

    private String getHtmlIconForNode(AbstractNode node)
    {
        if (node instanceof ContainerNode)
        {
            ContainerNode container = (ContainerNode) node;
            if (container.getSubelementsType().equals("table"))
            {
                return templates
                    .treeItemIcon(resources.css().tableSpriteClass())
                    .asString();
            }
        }
        else if (node instanceof ColumnNode)
        {
            ColumnNode column = (ColumnNode) node;
            String type = column.getType().toUpperCase().trim();

            if (type.contains("CLOB"))
            {
                return templates
                    .treeItemIcon(resources.css().colClobSpriteClass())
                    .asString();
            }
            else if (type.contains("BLOB"))
            {
                return templates
                    .treeItemIcon(resources.css().colBlobSpriteClass())
                    .asString();
            }
            else if (type.contains("DATE") || type.contains("TIMESTAMP"))
            {
                return templates
                    .treeItemIcon(resources.css().colDateSpriteClass())
                    .asString();
            }
            else if (type.contains("BOOLEAN"))
            {
                return templates
                    .treeItemIcon(resources.css().colBooleanSpriteClass())
                    .asString();
            }
            else if (type.contains("NUMBER") || type.contains("INTEGER")
                || type.contains("BINARY_FLOAT")
                || type.contains("BINARY_DOUBLE"))
            {
                return templates
                    .treeItemIcon(resources.css().colNumberSpriteClass())
                    .asString();
            }
            else if (type.contains("CHAR"))
            {
                // CHAR, VARCHAR, VARCHAR2
                return templates
                    .treeItemIcon(resources.css().colStringSpriteClass())
                    .asString();
            }
        }

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

    public DatabaseNode getRoot()
    {
        return root;
    }

    public void setRoot(DatabaseNode root)
    {
        this.root = root;
    }

    public void setHideNonChanged(boolean hideNonChanged)
    {
        this.hideNonChanged = hideNonChanged;
    }
}
