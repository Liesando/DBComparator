package com.otoil.dbcomparator.server.comparison;


import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.AbstractNode.NodeState;


/**
 * Сравниватель объектов слепка {@link AbstractNode}
 * 
 * @author Sergey Medelyan
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
    public final NodeState performComparison(AbstractNode node,
        P reflectedContainer)
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
                NodeState subResult = sub.performComparison(child,
                    getSameNodeFromReflectedContainer(getType().cast(node),
                        reflectedContainer));

                if (result == NodeState.NON_CHANGED
                    && subResult != NodeState.NON_CHANGED)
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

    protected T getSameNodeFromReflectedContainer(T node, P reflectedContainer)
    {
        return reflectedContainer.getChild(getType(), getType()::cast,
            node.getName());
    }

    protected <T extends AbstractNode> void formatValueIfChanged(T source,
        T dest, Function<T, String> valueGetter,
        BiConsumer<T, String> newValueSetter)
    {
        if (!valueGetter.apply(source).equals(valueGetter.apply(dest))
            && source.isOfSourceSnapshot())
        {
            String formattedSourceValue = formatPropertyChanged(
                changed(valueGetter.apply(source)), "--->",
                valueGetter.apply(dest));
            String formattedDestValue = formatPropertyChanged(
                valueGetter.apply(source), "--->",
                changed(valueGetter.apply(dest)));
            newValueSetter.accept(source, formattedSourceValue);
            newValueSetter.accept(dest, formattedDestValue);
        }
    }

    protected String formatPropertyChanged(String sourceValue, String separator,
        String destValue)
    {
        return String.format(
            "<span><span style=\"color: gray;\"><i>%s</i></span> <b>%s</b> <span style=\"color: gray;\"><i>%s</i></span></span>",
            sourceValue, separator, destValue);
    }

    protected String changed(String str)
    {
        return String.format("<span style=\"color: blue;\"><u>%s</u></span>",
            str);
    }

    protected abstract NodeState compare(T node, P reflectedContainer);
}
