package com.otoil.dbcomparator.server.parsing;


import com.otoil.dbcomparator.server.parsing.flattening.DBXmlElementFlattener;
import com.otoil.dbcomparator.shared.beans.constraints.PrimaryConstraintNode;


public class DBPrimaryKeyColumnParser extends DBXmlElementFlattener
{
    public DBPrimaryKeyColumnParser()
    {
        super("column", (parent, xmlNode) -> {
            ((PrimaryConstraintNode) parent).addColumnName(getXmlNodeName(xmlNode));
        });
    }
}
