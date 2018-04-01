package com.otoil.dbcomparator.server.comparing;


import com.otoil.dbcomparator.shared.AbstractNode.NodeState;
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.TableNode;
import com.otoil.dbcomparator.shared.TablesContainerNode;


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
