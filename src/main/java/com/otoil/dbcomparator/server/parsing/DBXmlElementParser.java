package com.otoil.dbcomparator.server.parsing;


import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import com.otoil.dbcomparator.shared.AbstractNode;


/**
 * Класс, осуществляющий парсинг xml-элементов в указанный тип R.
 * 
 * @author kakeru
 * @param <R>
 */
public abstract class DBXmlElementParser<R extends AbstractNode>
        extends DBObjectParser<Node, R>
{
    protected String getXmlNodeName(Node n)
    {
        String nodeName = getXmlAttributeValue(n, "name");
        return nodeName == "" ? "(not specified)": nodeName;
    }
    
    protected boolean getXmlBooleanAttrValue(Node n, String attributeName)
    {
        return getXmlAttributeValue(n, attributeName).equalsIgnoreCase("Y");
    }

    protected String getXmlAttributeValue(Node n, String attributeName)
    {
        NamedNodeMap attrs = n.getAttributes();
        Node node = attrs.getNamedItem(attributeName);
        String attributeValue = node == null ? "" : node.getNodeValue();
        return attributeValue;
    }
}
