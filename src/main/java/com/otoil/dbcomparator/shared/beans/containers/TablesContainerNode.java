package com.otoil.dbcomparator.shared.beans.containers;

import com.otoil.dbcomparator.client.resources.internationalization.DBComparatorMessages;
import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.TableNode;

public class TablesContainerNode extends ContainerNode
{
    public TablesContainerNode()
    {
        super("tables", "table");
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

    @Override
    public String getLocalizedName(DBComparatorMessages localizer)
    {
        return localizer.tables();
    }
}
