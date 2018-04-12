package com.otoil.dbcomparator.server.comparison;


import com.otoil.dbcomparator.shared.beans.ColumnNode;

import com.otoil.dbcomparator.shared.beans.AbstractNode.NodeState;
import com.otoil.dbcomparator.shared.beans.containers.ColumnsContainerNode;


public class DBColumnComparator
        extends DBObjectComparator<ColumnNode, ColumnsContainerNode>
{
    public DBColumnComparator()
    {
        super(ColumnNode.class, ColumnsContainerNode.class);
    }

    @Override
    protected NodeState compare(ColumnNode node,
        ColumnsContainerNode reflectedContainer)
    {
        ColumnNode sameColumn = getSameNodeFromReflectedContainer(node,
            reflectedContainer);
        if (sameColumn == null)
        {
            return reflectedContainer.isOfSourceSnapshot() ? NodeState.ADDED
                : NodeState.DELETED;
        }

        formatValueIfChanged(node, sameColumn, ColumnNode::getName,
            ColumnNode::setName);

        formatValueIfChanged(node, sameColumn, ColumnNode::getType,
            ColumnNode::setType);

        return node.getName().equals(sameColumn.getName())
            && node.getType().equals(sameColumn.getType())
            && node.isNullable() == sameColumn.isNullable()
            && node.isVirtual() == sameColumn.isVirtual()
                ? NodeState.NON_CHANGED
                : NodeState.CHANGED;
    }

}
