package com.otoil.dbcomparator.server.comparison;


import com.otoil.dbcomparator.shared.beans.AbstractNode.NodeState;
import com.otoil.dbcomparator.shared.beans.constraints.PrimaryConstraintNode;
import com.otoil.dbcomparator.shared.beans.containers.ConstraintsContainerNode;


/**
 * Сравниватель ограничений
 * 
 * @author Sergey Medelyan
 */
public class DBPrimaryConstraintComparator extends
        DBObjectComparator<PrimaryConstraintNode, ConstraintsContainerNode>
{
    public DBPrimaryConstraintComparator()
    {
        super(PrimaryConstraintNode.class, ConstraintsContainerNode.class);
    }

    @Override
    protected NodeState compare(PrimaryConstraintNode node,
        ConstraintsContainerNode reflectedContainer)
    {
        PrimaryConstraintNode sameConstraint = getSameNodeFromReflectedContainer(
            node, reflectedContainer);

        if (sameConstraint == null)
        {
            return reflectedContainer.isOfSourceSnapshot() ? NodeState.ADDED
                : NodeState.DELETED;
        }

        formatValueIfChanged(node, sameConstraint,
            PrimaryConstraintNode::getName, PrimaryConstraintNode::setName);
        formatValueIfChanged(node, sameConstraint,
            PrimaryConstraintNode::getStatus, PrimaryConstraintNode::setStatus);
        formatValueIfChanged(node, sameConstraint,
            PrimaryConstraintNode::getIndexName,
            PrimaryConstraintNode::setIndexName);

        return node.getName().equals(sameConstraint.getName())
            && node.getStatus().equals(sameConstraint.getStatus())
            && node.getIndexName().equals(sameConstraint.getIndexName())
                ? NodeState.NON_CHANGED
                : NodeState.CHANGED;
    }

}
