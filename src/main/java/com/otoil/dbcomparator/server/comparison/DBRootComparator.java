package com.otoil.dbcomparator.server.comparison;


import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;
import com.otoil.dbcomparator.shared.beans.AbstractNode.NodeState;


/**
 * Сравниватель корневых узлов баз
 * 
 * @author Sergey Medelyan
 */
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
        // сами по себе корневые узлы сравнивать не нужно
        return NodeState.NON_CHANGED;
    }

    @Override
    protected DatabaseNode getSameNodeFromReflectedContainer(DatabaseNode node,
        AbstractNode reflectedContainer)
    {
        // у нас нет контейнера для узла БД (т. к. это корневой узел, у которого
        // нет родителя), поэтому далее,
        // когда этот метод используется в performComparison() и ему
        // нужно передать родительский контейнер для детей (таблиц, вьюх,
        // триггеров и т. д.) мы делаем вот такую вот заглушку
        return DatabaseNode.class.cast(reflectedContainer);
    }
}
