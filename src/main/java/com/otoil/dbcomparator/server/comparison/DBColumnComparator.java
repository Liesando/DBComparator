package com.otoil.dbcomparator.server.comparison;


import com.otoil.dbcomparator.shared.beans.ColumnNode;
import com.otoil.dbcomparator.shared.beans.ColumnsContainerNode;

import java.util.function.BiConsumer;
import java.util.function.Function;

import com.otoil.dbcomparator.shared.beans.AbstractNode.NodeState;


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

        boolean nameChanged = !node.getName().equals(sameColumn.getName());
        formatIf(nameChanged && node.isOfSourceSnapshot(), node, sameColumn,
            ColumnNode::getName, ColumnNode::setName);

        boolean typeChanged = !node.getType().equals(sameColumn.getType());
        formatIf(typeChanged && node.isOfSourceSnapshot(), node, sameColumn,
            ColumnNode::getType, ColumnNode::setType);

        return !nameChanged && !typeChanged
            && node.isNullable() == sameColumn.isNullable()
            && node.isVirtual() == sameColumn.isVirtual()
                ? NodeState.NON_CHANGED
                : NodeState.CHANGED;
    }

    private void formatIf(boolean condition, ColumnNode source, ColumnNode dest,
        Function<ColumnNode, String> valueGetter,
        BiConsumer<ColumnNode, String> newValueSetter)
    {
        if (condition)
        {
            String formattedSourceValue = formatPropertyChanged(
                changed(valueGetter.apply(source)), "--->", valueGetter.apply(dest));
            String formattedDestValue = formatPropertyChanged(
                valueGetter.apply(source), "--->", changed(valueGetter.apply(dest)));
            newValueSetter.accept(source, formattedSourceValue);
            newValueSetter.accept(dest, formattedDestValue);
        }
    }

    private String formatPropertyChanged(String sourceValue, String separator,
        String destValue)
    {
        return String.format(
            "<span><span style=\"color: gray;\"><i>%s</i></span> <b>%s</b> <span style=\"color: gray;\"><i>%s</i></span></span>",
            sourceValue, separator, destValue);
    }

    private String changed(String str)
    {
        return String.format("<span style=\"color: blue;\"><u>%s</u></span>",
            str);
    }
}
