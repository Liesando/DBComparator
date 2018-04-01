package com.otoil.dbcomparator.server.comparing;


import com.otoil.dbcomparator.shared.AbstractNode.NodeState;
import com.otoil.dbcomparator.shared.ColumnNode;
import com.otoil.dbcomparator.shared.ColumnsContainerNode;
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.TableNode;


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
