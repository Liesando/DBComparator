package com.otoil.dbcomparator.shared.beans.constraints;

import com.otoil.dbcomparator.shared.beans.AbstractNode;

public abstract class ConstraintNode extends AbstractNode
{
    public ConstraintNode()
    {
        super(null);
    }

    public ConstraintNode(String name)
    {
        super(name);
    }

    @Override
    protected boolean canContainChild(AbstractNode child)
    {
        return false;
    }

}
