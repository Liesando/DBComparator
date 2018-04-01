package com.otoil.dbcomparator.shared;


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

    @Override
    protected boolean canContainChild(AbstractNode child)
    {
        // в будущем добавятся и другие типы
        return child instanceof ContainerNode;
    }
}
