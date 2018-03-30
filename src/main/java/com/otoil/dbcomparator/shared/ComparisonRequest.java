package com.otoil.dbcomparator.shared;

public class ComparisonRequest
{
    private DatabaseNode source;
    private DatabaseNode dest;
    
    public ComparisonRequest() {}

    public ComparisonRequest(DatabaseNode source, DatabaseNode dest)
    {
        super();
        this.source = source;
        this.dest = dest;
    }

    public DatabaseNode getSource()
    {
        return source;
    }

    public void setSource(DatabaseNode source)
    {
        this.source = source;
    }

    public DatabaseNode getDest()
    {
        return dest;
    }

    public void setDest(DatabaseNode dest)
    {
        this.dest = dest;
    }
}
