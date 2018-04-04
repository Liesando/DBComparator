package com.otoil.dbcomparator.shared.beans;


/**
 * Контейнер для других узлов (например, columns для столбцов, constraints для
 * ограничений и так далее).
 * 
 * @author kakeru
 */
public abstract class ContainerNode extends AbstractNode implements Cloneable
{
    private String subelementsType = "";
    public ContainerNode()
    {
        this(null, null);
    }

    public ContainerNode(String name, String subelementsType)
    {
        super(name);
        this.subelementsType = subelementsType;
    }
    
    public String getSubelementsType()
    {
        return subelementsType;
    }

    public void setSubelementsType(String containerFor)
    {
        this.subelementsType = containerFor;
    }

    @Override
    public abstract void setName(String name);

    public abstract ContainerNode clone();
}
