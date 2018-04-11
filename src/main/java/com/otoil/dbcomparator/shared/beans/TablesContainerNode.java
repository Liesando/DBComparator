package com.otoil.dbcomparator.shared.beans;


import java.util.Iterator;
import java.util.List;

import com.otoil.dbcomparator.client.resources.internationalization.DBComparatorMessages;


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
