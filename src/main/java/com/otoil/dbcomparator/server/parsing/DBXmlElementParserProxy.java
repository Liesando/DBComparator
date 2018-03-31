package com.otoil.dbcomparator.server.parsing;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.shared.AbstractNode;


/**
 * Прокси-класс для парсинга. Нужен, чтобы адекватно парсить контейнеры,
 * например, столбцы все хранятся в элементе &lt;columns&gt;, а каждый отдельный
 * столбец - в своём элементе &lt;column&gt;. Таким образом нам нужен некий
 * "прокси", который при подаче в него &lt;columns&gt; вернёт null (т. к. этот
 * элемент сам по себе никак не распарсивается), но для каждого элемента внутри
 * контейнера проведёт парсинг с помощью зарегистрированных подпарсеров.
 * 
 * @author kakeru
 */
public class DBXmlElementParserProxy extends DBXmlElementParser<AbstractNode>
{
    /** Тип контейнера (например, columns, indexes) */
    public final String FOR_TYPE;

    public DBXmlElementParserProxy(String forType)
    {
        FOR_TYPE = forType;
    }

    @Override
    protected AbstractNode parseObjectOnly(Node t)
        throws DBObjectParsingException
    {
        NodeList children = t.getChildNodes();

        // то есть в отличие от стандартного парсинга,
        // где каждый узел - родитель всех вложенных,
        // здесь мы "перескакиваем", пропуская самих себя - контейнер,
        // и указываем родителем фактических элементов - нашего родителя
        parseChildren(getParentDBNode(), children);
        return null;
    }

}
