package com.otoil.dbcomparator.server.parsing;


import org.w3c.dom.Node;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.shared.beans.constraints.PrimaryConstraintNode;


public class DBPrimaryConstraintParser
        extends DBXmlElementParser<PrimaryConstraintNode>
{
    public static final String FOR_TYPE = "primaryConstraint";

    @Override
    protected PrimaryConstraintNode parseObjectOnly(Node t)
        throws DBObjectParsingException
    {
        PrimaryConstraintNode pkConstraint = new PrimaryConstraintNode(
            getXmlNodeName(t));
        pkConstraint.setStatus(getXmlAttributeValue(t, "status"));
        pkConstraint.setIndexName(getXmlAttributeValue(t, "indexName"));
        return pkConstraint;
    }
}
