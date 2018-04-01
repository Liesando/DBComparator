package com.otoil.dbcomparator.shared;

public class CommentaryNode extends AbstractNode
{
    public CommentaryNode()
    {
        this(null);
    }
    
    public CommentaryNode(String name)
    {
        super(name);
    }

    @Override
    protected boolean canContainChild(AbstractNode child)
    {
        return false;
    }

}
