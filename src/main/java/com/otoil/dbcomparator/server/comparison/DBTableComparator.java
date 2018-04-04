package com.otoil.dbcomparator.server.comparison;


import com.otoil.dbcomparator.shared.beans.DatabaseNode;
import com.otoil.dbcomparator.shared.beans.TableNode;
import com.otoil.dbcomparator.shared.beans.TablesContainerNode;
import com.otoil.dbcomparator.shared.beans.AbstractNode.NodeState;


public class DBTableComparator
        extends DBObjectComparator<TableNode, TablesContainerNode>
{
    public DBTableComparator()
    {
        super(TableNode.class, TablesContainerNode.class);
    }

    @Override
    protected NodeState compare(TableNode node, TablesContainerNode reflectedContainer)
    {
        TableNode sameTable = getSameNodeFromReflectedContainer(node,
            reflectedContainer);
        if (sameTable == null)
        {
            return reflectedContainer.isOfSourceSnapshot() ? NodeState.ADDED
                : NodeState.DELETED;
        }

        return node.getName().equals(sameTable.getName())
                && node.getOwner().equals(sameTable.getOwner())
                && node.getTablespace().equals(sameTable.getTablespace())
                && node.isTemporary() == sameTable.isTemporary()
                && node.isOfIotType() == sameTable.isOfIotType()
            ? NodeState.NON_CHANGED
            : NodeState.CHANGED;
    }
}
