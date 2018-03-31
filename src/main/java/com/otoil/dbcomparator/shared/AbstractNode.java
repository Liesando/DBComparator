package com.otoil.dbcomparator.shared;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.otoil.dbcomparator.shared.AbstractNode.NodeState;


/**
 * Узел, представляющий собой некоторый элемент базы в слепке (например, саму
 * базу, таблицу, столбец, вьюху и т. д.). Может содержать в себе другие узлы.
 * TODO: добавить updateParentState() и, соответственно parent, если потребуется
 * (упростит парсинг, т. к. не нужно будет следить, изменилось ли что-нибудь в
 * таблице, чтобы изменить её статус; в случае с этими методом и полем - они
 * сами позаботятся об обновлении статуса своего родителя)
 * 
 * @author kakeru
 */
@JsonSubTypes({@Type(value = DatabaseNode.class, name = "database"),
    @Type(value = TableNode.class, name = "table"),
    @Type(value = ColumnNode.class, name = "column")})
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "@class")
public abstract class AbstractNode
{
    /**
     * Состояние узла (изменяется моделью, проводящей сравнение слепков)
     * 
     * @author kakeru
     */
    public enum NodeState
    {
        NON_CHANGED, CHANGED, DELETED, ADDED
    }

    private boolean isOfSourceSnapshot = false;
    private NodeState state;
    private String name;
    private List<AbstractNode> children;

    protected AbstractNode(String name)
    {
        this.name = name;
        state = NodeState.NON_CHANGED;
        children = new ArrayList<AbstractNode>();
    }

    protected AbstractNode(NodeState state, String name,
        List<AbstractNode> children)
    {
        this.state = state;
        this.name = name;
        this.children = children;
    }

    public final void addChild(AbstractNode child)
    {
        if (canContainChild(child))
        {
            children.add(child);

            // если у нас состояние added/deleted, сразу меняем состояние
            // ребёнка
            if (state == NodeState.ADDED || state == NodeState.DELETED)
            {
                child.setState(state);
            }
        }
    }

    public final String getName()
    {
        return name;
    }

    public final List<AbstractNode> getChildren()
    {
        return children;
    }

    /**
     * Возвращает детей данного узла определённого типа. (см. закомментированную
     * версию функции ниже, если возникают вопросы, зачем так сложно).
     * 
     * @param instanceChecker функция, проверяющая, нужный ли тип у узла
     * @param caster функция, преобразующая узел к нужному типу
     * @return списочный массив из узлов нужного типа
     */
    public final <T extends AbstractNode> List<T> getChildrenOfType(
        Function<AbstractNode, Boolean> instanceChecker,
        Function<AbstractNode, T> caster)
    {

        /*
         * В потенциале некоторые узлы (например, узел БД) могут содержать детей
         * разного типа (таблицы, вьюхи, триггеры, хранимые процедуры и т. д.)
         * Но если потребуется (а пока приложуха не будет сделана полностью -
         * потребуется:) ) получить детей только заданного типа - функция будет
         * полезной. Будет ли она полезной после завершения разработки - вопрос.
         */

        ArrayList<T> result = new ArrayList<T>();
        children.forEach(abstractNode -> {
            if (instanceChecker.apply(abstractNode))
                result.add(caster.apply(abstractNode));
        });

        return result;
    }

    public final NodeState getState()
    {
        return state;
    }

    public final void setState(NodeState state)
    {
        this.state = state;

        // элементы со статусом "удалён" или "добавлен"
        // всем своим детям задают такой же статус
        if (children != null
            && (state == NodeState.DELETED || state == NodeState.ADDED))
        {
            for (int i = 0; i < children.size(); i++)
            {
                children.get(i).setState(state);
            }
        }
    }

    public void setChildren(List<AbstractNode> children)
    {
        this.children = children;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public final <T extends AbstractNode> T getChild(Class<T> childType, Function<AbstractNode, T> caster,
        String childName)
    {
        T node = caster.apply(children.stream()
            .filter(
                c -> c.getClass() == childType && c.getName().equals(childName))
            .findFirst().orElse(null));
        
        return node;
    }

    public boolean isOfSourceSnapshot()
    {
        return isOfSourceSnapshot;
    }

    public void setOfSourceSnapshot(boolean isOfSourceSnapshot)
    {
        this.isOfSourceSnapshot = isOfSourceSnapshot;
    }

    /**
     * Может ли данный узел содержать указанного ребёнка?
     * 
     * @param child
     * @return
     */
    protected abstract boolean canContainChild(AbstractNode child);
}
