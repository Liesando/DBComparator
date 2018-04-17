package com.otoil.dbcomparator.server.parsing;


import com.otoil.dbcomparator.server.parsing.flattening.DBXmlElementFlattener;
import com.otoil.dbcomparator.shared.beans.constraints.PrimaryConstraintNode;


/**
 * Парсер-уплотнитель (см. {@link DBXmlElementFlattener}), кладующий в
 * PK-ограничение стобцы, входящие в ключ
 * 
 * @author Sergey Medelyan
 */
public class DBPrimaryKeyColumnParser extends DBXmlElementFlattener
{
    public DBPrimaryKeyColumnParser()
    {
        super("column", (parent, xmlNode) -> {
            ((PrimaryConstraintNode) parent)
                .addColumnName(getXmlNodeName(xmlNode));
        });
    }
}
