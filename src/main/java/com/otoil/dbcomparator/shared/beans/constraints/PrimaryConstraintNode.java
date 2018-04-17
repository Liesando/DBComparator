package com.otoil.dbcomparator.shared.beans.constraints;


import java.util.ArrayList;
import java.util.List;


/**
 * Узел-ограничение по первичному ключу
 * 
 * @author Sergey Medelyan
 */
public class PrimaryConstraintNode extends ConstraintNode
{
    private String status;
    private String indexName;
    private List<String> columnNames;

    public PrimaryConstraintNode()
    {
        super(null);
    }

    public PrimaryConstraintNode(String name)
    {
        super(name);
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getIndexName()
    {
        return indexName;
    }

    public void setIndexName(String indexName)
    {
        this.indexName = indexName;
    }

    public List<String> getColumnNames()
    {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames)
    {
        this.columnNames = columnNames;
    }

    public void addColumnName(String columnName)
    {
        if (columnNames == null)
        {
            columnNames = new ArrayList<String>();
        }
        columnNames.add(columnName);
    }
}
