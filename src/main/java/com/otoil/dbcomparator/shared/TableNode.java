package com.otoil.dbcomparator.shared;


/** 
 * Узел-таблица.
 * @author kakeru
 *
 */
public class TableNode extends AbstractNode
{
    public TableNode()
    {
        this(null);
    }
    
    public TableNode(String name)
    {
        super(name);
    }

    @Override
    protected boolean canContainChild(AbstractNode child)
    {
        // все данные в таблицу можно класть только внутри
        // специальных контейнеров
        return child instanceof ContainerNode;
    }
}
