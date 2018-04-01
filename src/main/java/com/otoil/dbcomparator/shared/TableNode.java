package com.otoil.dbcomparator.shared;


/** 
 * Узел-таблица.
 * @author kakeru
 *
 */
public class TableNode extends AbstractNode
{
    private String owner;
    private String tablespace;
    private boolean isTemporary;
    private boolean isOfIotType;
    
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

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getTablespace()
    {
        return tablespace;
    }

    public void setTablespace(String tablespace)
    {
        this.tablespace = tablespace;
    }

    public boolean isTemporary()
    {
        return isTemporary;
    }

    public void setTemporary(boolean isTemporary)
    {
        this.isTemporary = isTemporary;
    }

    public boolean isOfIotType()
    {
        return isOfIotType;
    }

    public void setOfIotType(boolean isOfIotType)
    {
        this.isOfIotType = isOfIotType;
    }
}
