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
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.TableNode;

/**
 * Кастомная модель для {@link CellTree}.<br><br>
 * 
 * Использование:<br><br>
 *      <code><i>model = new CustomTreeModel();</i></code><br>
 *      <code><i>model.setRoot() с валидным root</i></code><br>
 *      <code><i>new CellTree(model, ...)</i></code>
 * @author Sergey Medelyan
 */
public class CustomTreeModel implements TreeViewModel
{
    private DatabaseNode root;

    public CustomTreeModel()
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
            // return tables
            return getNodeInfo(((DatabaseNode) value).getChildrenOfType(
                n -> n instanceof TableNode, n -> (TableNode) n));
        }
        else if (value instanceof TableNode)
        {
            // return columns
            return getNodeInfo(((TableNode) value).getChildrenOfType(
                n -> n instanceof ColumnNode, n -> (ColumnNode) n));
        }

        return null;
    }

    /**
     * Конструирует {@link NodeInfo} для указанного типа узла по переданным данным для
     * {@link ListDataProvider}
     * 
     * @param dataForDataProvider данные для {@link ListDataProvider} 
     * @return 
     */
    private <T extends AbstractNode> NodeInfo<T> getNodeInfo(List<T> dataForDataProvider)
    {
        ListDataProvider<T> dataProvider = new ListDataProvider<T>(dataForDataProvider);
        AbstractCell<T> cell = new AbstractCell<T>()
        {

            @Override
            public void render(Context context, T value, SafeHtmlBuilder sb)
            {
                if(value != null)
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
                            switch(value.getState())
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
                            
                            return "<span" + (cssClass == null ? "" : " class=" + cssClass) +
                                    ">" + value.getName() + "</span>";
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
        return value instanceof ColumnNode;
    }

    public void setRoot(DatabaseNode root)
    {
        this.root = root;
    }

}
