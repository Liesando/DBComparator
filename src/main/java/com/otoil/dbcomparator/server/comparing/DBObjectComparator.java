package com.otoil.dbcomparator.server.comparing;


import java.util.ArrayList;
import java.util.List;

import com.otoil.dbcomparator.shared.AbstractNode;
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.AbstractNode.NodeState;


/**
 * Сравниватель объектов слепка {@link AbstractNode}
 * 
 * @author kakeru
 * @param <T> тип принимаемого объекта
 * @param <P> тип родителя, в котором содержится принимаемый объект
 */
public abstract class DBObjectComparator<T extends AbstractNode, P extends AbstractNode>
{
    private final Class<T> type;
    private final Class<P> parentType;
    private ArrayList<DBObjectComparator<? extends AbstractNode, T>> subcomparators = new ArrayList<DBObjectComparator<? extends AbstractNode, T>>();

    public DBObjectComparator(Class<T> t, Class<P> p)
    {
        type = t;
        parentType = p;
    }

    /**
     * Проводит сравнение, изменяя состояние {@link NodeState} узла
     * <code>node</code>
     * 
     * @param node узел
     * @param reflectedContainer контейнер из другого слепка, в котором может
     *            находиться другая версия узла <code>node</code>
     */
    public final NodeState performComparison(AbstractNode node, P reflectedContainer)
    {
        NodeState result = compare(getType().cast(node), reflectedContainer);

        // there's no same node
        if (result == NodeState.ADDED || result == NodeState.DELETED)
        {
            node.setState(result);
            return result;
        }

        // there IS same node
        List<AbstractNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++)
        {
            AbstractNode child = children.get(i);
            DBObjectComparator<? extends AbstractNode, T> sub = subcomparators
                .stream().filter(s -> s.getType() == child.getClass())
                .findFirst().orElse(null);

            if (sub != null)
            {
                NodeState subResult = sub.performComparison(child, getSameNodeFromReflectedContainer(
                    getType().cast(node), reflectedContainer));
                
                if(result == NodeState.NON_CHANGED && subResult != NodeState.NON_CHANGED)
                {
                    result = NodeState.CHANGED;
                }
            }
        }
        
        node.setState(result);
        return result;
    }

    public final void registerSubcomparator(
        DBObjectComparator<? extends AbstractNode, T> subcomparator)
    {
        if (!subcomparators.stream()
            .anyMatch(s -> s.getType() == subcomparator.getType()))
        {
            subcomparators.add(subcomparator);
        }
    }

    protected final Class<T> getType()
    {
        return type;
    }

    protected final Class<P> getParentType()
    {
        return parentType;
    }

    protected T getSameNodeFromReflectedContainer(T node,
        P reflectedContainer)
    {
        return reflectedContainer.getChild(getType(), getType()::cast, node.getName());
    }

    protected abstract NodeState compare(T node, P reflectedContainer);
}
