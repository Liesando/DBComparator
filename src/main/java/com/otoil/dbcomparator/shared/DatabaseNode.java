package com.otoil.dbcomparator.shared;


import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Узел базы данных (самый верхний уровень в иерархии)
 * 
 * @author kakeru
 */
public class DatabaseNode extends AbstractNode
{

    public DatabaseNode()
    {
        this(null);
    }

    public DatabaseNode(String name)
    {
        super(name);
    }

    @JsonCreator
    public DatabaseNode(@JsonProperty("state") NodeState state,
        @JsonProperty("name") String name,
        @JsonProperty("children") ArrayList<AbstractNode> children)
    {
        super(state, name, children);
    }
    
//    public DatabaseNode(NodeState state,
//        String name,
//        ArrayList<AbstractNode> children)
//    {
//        super(state, name, children);
//    }

    @Override
    protected boolean canContainChild(AbstractNode child)
    {
        // в будущем добавятся и другие типы
        return child instanceof TableNode;
    }
}
