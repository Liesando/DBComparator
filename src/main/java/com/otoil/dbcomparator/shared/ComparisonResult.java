package com.otoil.dbcomparator.shared;

public class ComparisonResult
{
    private final DatabaseNode sourceRoot;
    private final DatabaseNode destRoot;
    
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
    
    
}
