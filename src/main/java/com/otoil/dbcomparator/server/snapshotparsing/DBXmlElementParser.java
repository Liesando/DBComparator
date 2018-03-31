package com.otoil.dbcomparator.server.snapshotparsing;


import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import com.otoil.dbcomparator.shared.AbstractNode;


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
