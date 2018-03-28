package com.otoil.dbcomparator.shared;


/**
 * Узел-столбец.
 * @author kakeru
 *
 */
public class ColumnNode extends AbstractNode
{
    public ColumnNode(String name)
    {
        super(name);
    }

    @Override
    protected boolean canContainChild(AbstractNode child)
    {
        return false;
    }
}
