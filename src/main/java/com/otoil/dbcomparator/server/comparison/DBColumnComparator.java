package com.otoil.dbcomparator.server.comparison;


import com.otoil.dbcomparator.shared.beans.ColumnNode;
import com.otoil.dbcomparator.shared.beans.ColumnsContainerNode;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;
import com.otoil.dbcomparator.shared.beans.TableNode;
import com.otoil.dbcomparator.shared.beans.AbstractNode.NodeState;


public class DBColumnComparator
        extends DBObjectComparator<ColumnNode, ColumnsContainerNode>
{
    public DBColumnComparator()
    {
        super(ColumnNode.class, ColumnsContainerNode.class);
    }

    @Override
    protected NodeState compare(ColumnNode node, ColumnsContainerNode reflectedContainer)
    {
        ColumnNode sameColumn = getSameNodeFromReflectedContainer(node,
            reflectedContainer);
        if (sameColumn == null)
        {
            return reflectedContainer.isOfSourceSnapshot() ? NodeState.ADDED
                : NodeState.DELETED;
        }
        return node.getName().equals(sameColumn.getName())
                && node.getType().equals(sameColumn.getType())
                && node.isNullable() == sameColumn.isNullable()
                && node.isVirtual() == sameColumn.isVirtual()
            ? NodeState.NON_CHANGED
            : NodeState.CHANGED;
    }
}
