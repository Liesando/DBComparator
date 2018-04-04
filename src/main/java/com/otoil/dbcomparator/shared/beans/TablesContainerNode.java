package com.otoil.dbcomparator.shared.beans;

public class TablesContainerNode extends ContainerNode
{
    public TablesContainerNode()
    {
        super("tables", "table");
    }
    
    @Override
    public void setName(String name)
    {
    }

    @Override
    public ContainerNode clone()
    {
        return new TablesContainerNode();
    }

    @Override
    protected boolean canContainChild(AbstractNode child)
    {
        return child instanceof TableNode;
    }

}
