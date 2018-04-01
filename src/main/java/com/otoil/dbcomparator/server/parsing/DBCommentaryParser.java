package com.otoil.dbcomparator.server.parsing;


import org.w3c.dom.Node;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.shared.CommentaryNode;


public class DBCommentaryParser extends DBXmlElementParser<CommentaryNode>
{
    public static final String FOR_TYPE = "comments";
    
    @Override
    protected CommentaryNode parseObjectOnly(Node t)
        throws DBObjectParsingException
    {
        CommentaryNode commentaryNode = new CommentaryNode(t.getTextContent());
        return commentaryNode;
    }
}
