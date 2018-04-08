package com.otoil.dbcomparator.server.parsing;


import org.w3c.dom.Node;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.shared.beans.AbstractNode;


public class DBCommentaryParser extends DBXmlElementParser<AbstractNode>
{
    public static final String FOR_TYPE = "comments";

    @Override
    protected AbstractNode parseObjectOnly(Node t)
        throws DBObjectParsingException
    {
        getParentDBNode().setCommentary(t.getTextContent());
        return null;
    }
}
