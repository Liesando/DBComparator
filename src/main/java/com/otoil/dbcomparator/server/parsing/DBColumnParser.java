package com.otoil.dbcomparator.server.parsing;


import org.w3c.dom.Node;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.shared.beans.ColumnNode;


/**
 * Парсер столбцов
 * 
 * @author Sergey Medelyan
 */
public class DBColumnParser extends DBXmlElementParser<ColumnNode>
{
    public static final String FOR_TYPE = "column";

    @Override
    protected ColumnNode parseObjectOnly(Node t) throws DBObjectParsingException
    {
        ColumnNode column = new ColumnNode(getXmlNodeName(t));
        column.setType(getXmlAttributeValue(t, "type"));
        column.setNullable(getXmlBooleanAttrValue(t, "nullable"));
        column.setVirtual(getXmlBooleanAttrValue(t, "virtual"));
        return column;
    }

}
