package com.otoil.dbcomparator.server.snapshotparsing;


import org.w3c.dom.Node;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.shared.TableNode;


public class DBTableParser extends DBXmlElementParser<TableNode>
{
    public static final String FOR_TYPE = "table";

    @Override
    protected TableNode parseObjectOnly(Node t) throws DBObjectParsingException
    {
        TableNode table = new TableNode(getXmlNodeName(t));
        return table;
    }
}
