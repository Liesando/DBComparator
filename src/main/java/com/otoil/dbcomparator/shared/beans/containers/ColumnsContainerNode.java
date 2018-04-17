package com.otoil.dbcomparator.shared.beans.containers;


import com.otoil.dbcomparator.client.resources.internationalization.DBComparatorMessages;
import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.ColumnNode;


/**
 * Контейнер столбцов
 * 
 * @author Sergey Medelyan
 */
public class ColumnsContainerNode extends ContainerNode
{
    public ColumnsContainerNode()
    {
        super("columns", "column");
    }

    @Override
    protected boolean canContainChild(AbstractNode child)
    {
        return child instanceof ColumnNode;
    }

    @Override
    public ContainerNode clone()
    {
        // не копируем детей
        return new ColumnsContainerNode();
    }

    @Override
    public String getLocalizedName(DBComparatorMessages localizer)
    {
        return localizer.columns();
    }
}
