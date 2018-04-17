package com.otoil.dbcomparator.server.parsing;


import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.otoil.dbcomparator.shared.beans.AbstractNode;


/**
 * Класс, осуществляющий парсинг xml-элементов в указанный тип R.
 * 
 * @author Sergey Medelyan
 * @param <R> тип узла бд
 */
public abstract class DBXmlElementParser<R extends AbstractNode>
        extends DBObjectParser<Node, R>
{
    protected static String getXmlNodeName(Node n)
    {
        String nodeName = getXmlAttributeValue(n, "name");
        return nodeName == "" ? "(not specified)" : nodeName;
    }

    protected static boolean getXmlBooleanAttrValue(Node n,
        String attributeName)
    {
        return getXmlAttributeValue(n, attributeName).equalsIgnoreCase("Y");
    }

    protected static String getXmlAttributeValue(Node n, String attributeName)
    {
        NamedNodeMap attrs = n.getAttributes();
        Node node = attrs.getNamedItem(attributeName);
        String attributeValue = node == null ? "" : node.getNodeValue();
        return attributeValue;
    }
}
