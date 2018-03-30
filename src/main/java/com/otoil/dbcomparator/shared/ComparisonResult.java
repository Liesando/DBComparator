package com.otoil.dbcomparator.shared;

public final class ComparisonResult
{
    private DatabaseNode sourceRoot;
    private DatabaseNode destRoot;
    
    public ComparisonResult() {}
    
    public ComparisonResult(DatabaseNode sourceRoot, DatabaseNode destRoot)
    {
        super();
        this.sourceRoot = sourceRoot;
        this.destRoot = destRoot;
    }

    public DatabaseNode getSourceRoot()
    {
        return sourceRoot;
    }

    public DatabaseNode getDestRoot()
    {
        return destRoot;
    }

    public void setSourceRoot(DatabaseNode sourceRoot)
    {
        this.sourceRoot = sourceRoot;
    }

    public void setDestRoot(DatabaseNode destRoot)
    {
        this.destRoot = destRoot;
    }
}
