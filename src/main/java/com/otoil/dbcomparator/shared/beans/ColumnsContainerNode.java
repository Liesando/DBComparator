package com.otoil.dbcomparator.shared.beans;

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
    public void setName(String name)
    {
        // do we need to throw here?
//        if(!this.getName().equals(name))
//        {
//        }
    }

    @Override
    public ContainerNode clone()
    {
        // не копируем детей
        return new ColumnsContainerNode();
    }
}
