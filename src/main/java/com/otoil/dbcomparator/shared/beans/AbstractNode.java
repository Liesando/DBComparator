package com.otoil.dbcomparator.shared.beans;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.otoil.dbcomparator.client.comparison.ComparisonModel;
import com.otoil.dbcomparator.client.resources.DBComparatorTemplates;
import com.otoil.dbcomparator.client.resources.internationalization.DBComparatorMessages;
import com.otoil.dbcomparator.shared.beans.constraints.PrimaryConstraintNode;
import com.otoil.dbcomparator.shared.beans.containers.ColumnsContainerNode;
import com.otoil.dbcomparator.shared.beans.containers.ConstraintsContainerNode;
import com.otoil.dbcomparator.shared.beans.containers.TablesContainerNode;


/**
 * Узел, представляющий собой некоторый элемент базы в слепке (например, саму
 * базу, таблицу, столбец, вьюху и т. д.). Узел может содержать в себе другие
 * узлы.
 * 
 * @author kakeru
 */
@JsonSubTypes({@Type(value = DatabaseNode.class, name = "database"),
    @Type(value = TableNode.class, name = "table"),
    @Type(value = ColumnNode.class, name = "column"),
    @Type(value = PrimaryConstraintNode.class, name = "primary-constraint"),
    @Type(value = TablesContainerNode.class, name = "tables-container"),
    @Type(value = ConstraintsContainerNode.class, name = "constraints-container"),
    @Type(value = ColumnsContainerNode.class, name = "columns-container")})
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "@class")
public abstract class AbstractNode
{
    protected static final String TABULATION = "&nbsp;&nbsp;&nbsp;&nbsp;";

    /**
     * Состояние узла (изменяется {@link ComparisonModel моделью}, проводящей
     * сравнение слепков)
     * 
     * @author kakeru
     */
    public enum NodeState
    {
        NON_CHANGED, CHANGED, DELETED, ADDED
    }

    // принадлежит ли узел исходному (старому) слепку?
    private boolean isOfSourceSnapshot = false;
    private NodeState state;
    private String name;
    private String commentary;

    /**
     * содержит форматированную строку со сводкой об изменениях внутри этого
     * узла - его детях
     */
    private String changesSummary;
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

    public final List<AbstractNode> getChildren()
    {
        return children;
    }

    /**
     * Возвращает детей данного узла определённого типа.
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

        // return children.stream().filter(c -> instanceChecker.apply(c))
        // .map(c -> caster.apply(c)).collect(Collectors.toList());

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

    public final String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCommentary()
    {
        return commentary;
    }

    public void setCommentary(String commentary)
    {
        this.commentary = commentary;
    }

    public boolean hasCommentary()
    {
        return commentary != null && commentary.trim().length() > 0;
    }

    public final <T extends AbstractNode> T getChild(Class<T> childType,
        Function<AbstractNode, T> caster, String childName)
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
        for (int i = 0; i < children.size(); i++)
        {
            children.get(i).setOfSourceSnapshot(isOfSourceSnapshot);
        }
    }

    public boolean hasSummary()
    {
        return changesSummary != null && changesSummary.trim().length() > 0;
    }

    @JsonIgnore
    public String getChangesSummary(String prefix,
        DBComparatorMessages localizer, DBComparatorTemplates templates)
    {
        if (changesSummary == null)
        {
            changesSummary = generateChangesSummary(prefix, localizer,
                templates);
        }
        return changesSummary;
    }

    @JsonIgnore
    public String getChangesSummary(DBComparatorMessages localizer,
        DBComparatorTemplates templates)
    {
        return getChangesSummary("", localizer, templates);
    }

    /**
     * Генерирует html-форматированную строку с краткой информацией об
     * изменениях в детях этого узла. Переопределять в подклассах по надобности.
     * Классы-листья должны возвращать null
     * 
     * @return краткая информация или null
     */
    protected String generateChangesSummary(String prefix,
        DBComparatorMessages localizer, DBComparatorTemplates templates)
    {
        StringBuilder summaryBuilder = new StringBuilder();
        Iterator<AbstractNode> it = getChildren().iterator();
        while (it.hasNext())
        {
            if (summaryBuilder.length() > 0)
            {
                summaryBuilder.append("<br>");
            }
            AbstractNode node = it.next();
            summaryBuilder.append(prefix != "" ? prefix : TABULATION);
            summaryBuilder.append(node.getLocalizedName(localizer));
            summaryBuilder.append(": ");
            summaryBuilder.append(node.getChangesSummary(localizer, templates));
        }

        return summaryBuilder.toString();
    }

    protected String generateChangesSummary(DBComparatorMessages localizer,
        DBComparatorTemplates templates)
    {
        return generateChangesSummary("", localizer, templates);
    }

    public static String localizedNameFor(AbstractNode node,
        DBComparatorMessages localizer)
    {
        if (node instanceof TablesContainerNode)
        {
            return localizer.tables();
        }
        else if (node instanceof ColumnsContainerNode)
        {
            return localizer.columns();
        }
        return node.getName();
    }

    public String getLocalizedName(DBComparatorMessages localizer)
    {
        return getName();
    }

    /**
     * Может ли данный узел содержать указанного ребёнка?
     * 
     * @param child
     * @return
     */
    protected abstract boolean canContainChild(AbstractNode child);
}
