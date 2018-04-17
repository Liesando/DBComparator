package com.otoil.dbcomparator.shared.beans;


import com.otoil.dbcomparator.client.resources.DBComparatorTemplates;
import com.otoil.dbcomparator.client.resources.internationalization.DBComparatorMessages;


/**
 * Узел-столбец.
 * 
 * @author Sergey Medelyan
 */
public class ColumnNode extends AbstractNode
{
    private String type;
    private boolean isNullable;
    private boolean isVirtual;

    public ColumnNode()
    {
        this(null);
    }

    public ColumnNode(String name)
    {
        super(name);
    }

    @Override
    protected boolean canContainChild(AbstractNode child)
    {
        return false;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public boolean isNullable()
    {
        return isNullable;
    }

    public void setNullable(boolean isNullable)
    {
        this.isNullable = isNullable;
    }

    public boolean isVirtual()
    {
        return isVirtual;
    }

    public void setVirtual(boolean isVirtual)
    {
        this.isVirtual = isVirtual;
    }

    @Override
    protected String generateChangesSummary(String prefix,
        DBComparatorMessages localizer, DBComparatorTemplates templates)
    {
        return null;
    }
}
