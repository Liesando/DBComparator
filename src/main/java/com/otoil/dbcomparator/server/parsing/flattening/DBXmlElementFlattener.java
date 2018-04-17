package com.otoil.dbcomparator.server.parsing.flattening;


import java.util.function.BiConsumer;

import org.w3c.dom.Node;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.server.parsing.DBXmlElementParser;
import com.otoil.dbcomparator.shared.beans.AbstractNode;


/**
 * Парсер, который обрабатывает значение(-я) элемента и определённым образом
 * кладёт это значение(-я) в родителя. Как именно это делается определяется в
 * {@link DBXmlElementFlattener#flattener}. Иными словами этот парсер делает
 * "уплощение" (flatteging) на 1 уровень вложенности:)
 * 
 * @author Sergey Medelyan
 */
public class DBXmlElementFlattener extends DBXmlElementParser<AbstractNode>
{
    public final String FOR_TYPE;
    private final BiConsumer<AbstractNode, Node> flattener;

    public DBXmlElementFlattener(String tagToFlatten,
        BiConsumer<AbstractNode, Node> flattener)
    {
        super();
        FOR_TYPE = tagToFlatten;
        this.flattener = flattener;
    }

    @Override
    protected AbstractNode parseObjectOnly(Node t)
        throws DBObjectParsingException
    {
        flattener.accept(getParentDBNode(), t);
        return null;
    }

}
