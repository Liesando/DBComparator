package com.otoil.dbcomparator.server.parsing;


import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import com.otoil.dbcomparator.shared.AbstractNode;

/**
 * Класс, осуществляющий парсинг xml-элементов в указанный
 * тип R.
 * @author kakeru
 *
 * @param <R>
 */
public abstract class DBXmlElementParser<R extends AbstractNode>
        extends DBObjectParser<Node, R>
{
    protected String getXmlNodeName(Node n)
    {
        NamedNodeMap attrs = n.getAttributes();
        Node node = attrs.getNamedItem("name");
        String nodeName = node == null ? "(not specified)"
            : node.getNodeValue();
        return nodeName;
    }
}
