package com.otoil.dbcomparator.client;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;
import com.otoil.dbcomparator.shared.AbstractNode;
import com.otoil.dbcomparator.shared.ColumnNode;
import com.otoil.dbcomparator.shared.ColumnsContainerNode;
import com.otoil.dbcomparator.shared.CommentaryNode;
import com.otoil.dbcomparator.shared.ContainerNode;
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.TableNode;
import com.otoil.dbcomparator.shared.TablesContainerNode;


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
    private DatabaseNode root;

    public CustomCellTreeModel()
    {
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
        else if (value instanceof ColumnNode)
        {
            ColumnNode column = (ColumnNode) value;
            if (column.getChildren().size() > 0)
            {
                return getNodeInfo(column.getChildren());
            }
            else
            {
                return null;
            }
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
                            if (value instanceof CommentaryNode)
                            {
                                cssClass = "db-commentary";
                            }
                            else
                            {
                                switch (value.getState())
                                {
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
                            }

                            return "<span"
                                + (cssClass == null ? "" : " class=" + cssClass)
                                + ">" + value.getName() + "</span>";
                        }
                    });
                }
            }
        };

        return new DefaultNodeInfo<T>(dataProvider, cell);
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
