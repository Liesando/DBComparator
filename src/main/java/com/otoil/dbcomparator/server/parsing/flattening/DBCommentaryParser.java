package com.otoil.dbcomparator.server.parsing.flattening;


public class DBCommentaryParser extends DBXmlElementFlattener
{
    public DBCommentaryParser()
    {
        super("comments", (parent, xmlNode) -> {
            parent.setCommentary(xmlNode.getTextContent());
        });
    }
}
