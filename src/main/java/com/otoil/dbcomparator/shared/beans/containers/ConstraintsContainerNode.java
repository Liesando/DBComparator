package com.otoil.dbcomparator.shared.beans.containers;


import com.otoil.dbcomparator.client.resources.internationalization.DBComparatorMessages;
import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.constraints.ConstraintNode;


/**
 * Контейнер ограничений
 * 
 * @author Sergey Medelyan
 */
public class ConstraintsContainerNode extends ContainerNode
{
    public ConstraintsContainerNode()
    {
        // subelementsType тут может быть разный, да и нужен
        // он лишь в одном месте.
        super("constraints", "");
    }

    @Override
    public ContainerNode clone()
    {
        return new ConstraintsContainerNode();
    }

    @Override
    protected boolean canContainChild(AbstractNode child)
    {
        return child instanceof ConstraintNode;
    }

    @Override
    public String getLocalizedName(DBComparatorMessages localizer)
    {
        return localizer.constraints();
    }
}
