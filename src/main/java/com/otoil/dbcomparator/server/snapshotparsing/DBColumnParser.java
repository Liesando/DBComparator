package com.otoil.dbcomparator.server.snapshotparsing;


import org.w3c.dom.Node;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.shared.ColumnNode;


public class DBColumnParser extends DBXmlElementParser<ColumnNode>
{
    public static final String FOR_TYPE = "column";

    @Override
    protected ColumnNode parseObjectOnly(Node t) throws DBObjectParsingException
    {
        ColumnNode column = new ColumnNode(getXmlNodeName(t));
        return column;
    }

}
