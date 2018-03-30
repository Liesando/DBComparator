package com.otoil.dbcomparator.server.snapshotparsing;

import org.w3c.dom.Node;

import com.otoil.dbcomparator.shared.AbstractNode;
import com.otoil.dbcomparator.shared.TableNode;

public class DBTableParser extends DBXmlElementParser<TableNode>
{

    @Override
    protected TableNode parseObject(AbstractNode parent, Node t)
        throws DBObjectParsingException
    {
        TableNode table = new TableNode(t.getNodeName());
        parseChildrenXmlElementsIfAny(table, t);
        return table;
    }
}
