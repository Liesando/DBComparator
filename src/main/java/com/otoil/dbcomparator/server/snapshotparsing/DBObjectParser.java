package com.otoil.dbcomparator.server.snapshotparsing;


import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.otoil.dbcomparator.shared.AbstractNode;


/**
 * Класс, осуществляющий парсинг .xml-файлов и элементов xml-разметки. П. С.
 * какие-то страшные методы получились :(
 * 
 * @author kakeru
 * @param <T> Тип обрабатываемого объекта (во всех случаях, кроме самого
 *            верхнего уровня это будет {@link Node}; на верхнем уровне -
 *            {@link ZipEntry})
 * @param <R> Тип результата обработки
 */
public abstract class DBObjectParser<T, R extends AbstractNode>
{
    protected HashMap<String, DBObjectParser<Node, ? extends AbstractNode>> subparsers = new HashMap<String, DBObjectParser<Node, ? extends AbstractNode>>();

    public final void parse(AbstractNode parent, T t,
        Supplier<NodeList> childrenExtractor) throws DBObjectParsingException
    {
        R result = parseObject(parent, t);
        if (childrenExtractor != null)
        {
            NodeList children = childrenExtractor.get();
            if (children != null)
            {
                for (int i = 0; i < children.getLength(); i++)
                {
                    Node temp = children.item(i);
                    if (temp.getNodeType() == Node.ELEMENT_NODE
                        && subparsers.containsKey(temp.getNodeName()))
                    {
                        subparsers.get(temp.getNodeName()).parse(result, temp,
                            temp::getChildNodes);
                    }
                }
            }
        }
        parent.addChild(result);
    }

    protected abstract R parseObject(AbstractNode parent, T t)
        throws DBObjectParsingException;

    public final <S extends DBObjectParser<Node, ? extends AbstractNode>> void registerSubparser(
        String forType, S subparser)
    {
        subparsers.putIfAbsent(forType, subparser);
    }

    public final <S extends DBObjectParser<Node, ? extends AbstractNode>> void registerSubparser(
        String parentParserFor, String forType, S subparser)
    {
        if (subparsers.containsKey(parentParserFor))
        {
            subparsers.get(parentParserFor).registerSubparser(forType,
                subparser);
        }
    }
}