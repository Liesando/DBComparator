package com.otoil.dbcomparator.server.comparison;


import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.AbstractNode.NodeState;


/**
 * Сраниватель контейнеров (таких как "columns", "constraints" и т. д.)
 * 
 * @author Sergey Medelyan
 * @param <T> тип принимаемого объекта
 * @param <P> тип родителя, в котором содержится принимаемый объект
 */
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

        // контейнер не существует
        return node.isOfSourceSnapshot() ? NodeState.DELETED : NodeState.ADDED;
    }
}
