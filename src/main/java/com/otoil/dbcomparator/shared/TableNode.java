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
        // в будущем добавятся и другие типы
        return child instanceof ColumnNode;
    }
}
