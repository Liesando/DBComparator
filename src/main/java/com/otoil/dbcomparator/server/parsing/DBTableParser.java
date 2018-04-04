package com.otoil.dbcomparator.server.parsing;


import org.w3c.dom.Node;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.shared.beans.TableNode;


public class DBTableParser extends DBXmlElementParser<TableNode>
{
    public static final String FOR_TYPE = "table";

    @Override
    protected TableNode parseObjectOnly(Node t) throws DBObjectParsingException
    {
        TableNode table = new TableNode(getXmlNodeName(t));
        table.setOwner(getXmlAttributeValue(t, "owner"));
        table.setTablespace(getXmlAttributeValue(t, "tablespace"));
        table.setTemporary(getXmlBooleanAttrValue(t, "temporary"));
        table.setOfIotType(getXmlBooleanAttrValue(t, "iotType"));
        return table;
    }
}
