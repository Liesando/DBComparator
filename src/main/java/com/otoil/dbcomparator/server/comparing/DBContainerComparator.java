package com.otoil.dbcomparator.server.comparing;


import com.otoil.dbcomparator.shared.AbstractNode;
import com.otoil.dbcomparator.shared.AbstractNode.NodeState;


public class DBContainerComparator<T extends AbstractNode, P extends AbstractNode>
        extends DBObjectComparator<T, P>
{
    public DBContainerComparator(Class<T> type, Class<P> parentType)
    {
        super(type, parentType);
    }

    @Override
    protected NodeState compare(T node, P reflectedContainer)
    {
        T sameNode = getSameNodeFromReflectedContainer(node,
            reflectedContainer);
        if (sameNode != null)
        {
            // нас не интересует имя контейнера, главное, что он есть
            return NodeState.NON_CHANGED;
        }

        return node.isOfSourceSnapshot() ? NodeState.DELETED : NodeState.ADDED;
    }
}
