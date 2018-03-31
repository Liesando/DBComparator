package com.otoil.dbcomparator.server.snapshotparsing;


import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.shared.AbstractNode;


/**
 * Класс, осуществляющий парсинг .xml-файлов или элементов xml-разметки. П. С.
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
    private AbstractNode parentDBNode = null;

    /**
     * Парсит переданный источник <code>t</code> и его детей, если
     * <code>childrenExtractor != null</code>. Результат парсинга (то есть
     * {@link AbstractNode} или его наследника с детьми) добавляет к детям
     * <code>parent</code>
     * 
     * @param parent Родительский узел
     * @param t Элемент, подлежащий парсингу
     * @param childrenExtractor экстрактор детей из <code>t</code>
     * @throws DBObjectParsingException
     */
    public final void parse(AbstractNode parent, T t,
        Supplier<NodeList> childrenExtractor) throws DBObjectParsingException
    {
        parentDBNode = parent;
        R result = parseObjectOnly(t);

        // не парсим детей, если нет экстрактора или результат парсинга
        // объекта нулевой
        if (childrenExtractor != null && result != null)
        {
            NodeList children = childrenExtractor.get();
            if (children != null)
            {
                parseChildren(result, children);
            }
        }

        // не забываем приаттачить результат парсинга
        if (result != null)
        {
            parent.addChild(result);
        }
    }

    protected void parseChildren(R parentDBNode, NodeList children)
        throws DBObjectParsingException
    {
        for (int i = 0; i < children.getLength(); i++)
        {
            Node temp = children.item(i);
            if (temp.getNodeType() == Node.ELEMENT_NODE
                && subparsers.containsKey(temp.getNodeName()))
            {
                subparsers.get(temp.getNodeName()).parse(parentDBNode, temp,
                    temp::getChildNodes);
            }
        }
    }

    /**
     * Парсит конкретный объект
     * 
     * @param t Элемент для парсинга
     * @return Узел-результат парсинга
     * @throws DBObjectParsingException
     */
    protected abstract R parseObjectOnly(T t) throws DBObjectParsingException;

    public final <S extends DBObjectParser<Node, ? extends AbstractNode>> void registerSubparser(
        String forType, S subparser)
    {
        subparsers.putIfAbsent(forType, subparser);
    }

    public final <S extends DBObjectParser<Node, ? extends AbstractNode>> void registerSubparser(
        String parentParserType, String forType, S subparser)
    {
        DBObjectParser<Node, ? extends AbstractNode> parser = getSubparserForType(
            parentParserType);
        if (parser != null)
        {
            parser.registerSubparser(forType, subparser);
        }
    }

    private DBObjectParser<Node, ? extends AbstractNode> getSubparserForType(
        String forType)
    {
        if (subparsers.containsKey(forType))
        {
            return subparsers.get(forType);
        }
        else
        {
            for (String key : subparsers.keySet())
            {
                DBObjectParser<Node, ? extends AbstractNode> parser = subparsers
                    .get(key).getSubparserForType(forType);
                if (parser != null)
                {
                    return parser;
                }
            }
        }

        return null;
    }

    protected final AbstractNode getParentDBNode()
    {
        return parentDBNode;
    }
}