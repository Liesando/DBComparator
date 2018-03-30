package com.otoil.dbcomparator.server.snapshotparsing;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.otoil.dbcomparator.shared.AbstractNode;


public abstract class DBXmlElementParser<R extends AbstractNode>
        extends DBObjectParser<Node, R>
{
    protected void parseChildrenXmlElementsIfAny(AbstractNode parent,
        Node parentNode) throws DBObjectParsingException
    {
        NodeList children = parentNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++)
        {
            Node child = children.item(i);
            if (subparsers.containsKey(child.getNodeName()))
            {
                subparsers.get(child.getNodeName()).parse(parent, child,
                    child::getChildNodes);
            }
            else
            {
                // TODO: indicate that children of that type was skipped
            }
        }
    }
}
