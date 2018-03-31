package com.otoil.dbcomparator.server.comparing;


import com.otoil.dbcomparator.shared.AbstractNode.NodeState;
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.TableNode;


public class DBTableComparator
        extends DBObjectComparator<TableNode, DatabaseNode>
{
    public DBTableComparator()
    {
        super(TableNode.class, DatabaseNode.class);
    }

    @Override
    protected NodeState compare(TableNode node, DatabaseNode reflectedContainer)
    {
        TableNode sameTable = getSameNodeFromReflectedContainer(node,
            reflectedContainer);
        if (sameTable == null)
        {
            return reflectedContainer.isOfSourceSnapshot() ? NodeState.ADDED
                : NodeState.DELETED;
        }

        return node.getName().equals(sameTable.getName())
            ? NodeState.NON_CHANGED
            : NodeState.CHANGED;
    }
}
