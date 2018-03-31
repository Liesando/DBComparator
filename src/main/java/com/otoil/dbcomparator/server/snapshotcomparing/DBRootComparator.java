package com.otoil.dbcomparator.server.snapshotcomparing;


import com.otoil.dbcomparator.shared.AbstractNode;
import com.otoil.dbcomparator.shared.AbstractNode.NodeState;
import com.otoil.dbcomparator.shared.DatabaseNode;


public class DBRootComparator
        extends DBObjectComparator<DatabaseNode, AbstractNode>
{

    public DBRootComparator()
    {
        super(DatabaseNode.class, AbstractNode.class);
    }

    @Override
    protected NodeState compare(DatabaseNode node,
        AbstractNode reflectedContainer)
    {
        // TODO: нужно ли сравнивать имена, если они не задаются в явном виде в
        // xml?
        return NodeState.NON_CHANGED;
    }

    @Override
    protected DatabaseNode getSameNodeFromReflectedContainer(DatabaseNode node,
        AbstractNode reflectedContainer)
    {
        // в руте у нас нет контейнера для узла БД, поэтому далее,
        // когда этот метод используется в performComparison() и ему
        // нужно передать родительский контейнер для детей (таблиц, вьюх, 
        // триггеров и т. д.) мы делаем вот такую вот заглушку
        return DatabaseNode.class.cast(reflectedContainer);
    }
}
