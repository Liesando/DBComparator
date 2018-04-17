package com.otoil.dbcomparator.server.parsing;


import org.w3c.dom.Node;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.containers.ContainerNode;


/**
 * Прокси-класс для парсинга. Нужен, чтобы адекватно парсить контейнеры:
 * например, столбцы все хранятся в элементе &lt;columns&gt;, а каждый отдельный
 * столбец - в своём элементе &lt;column&gt;. Таким образом нам нужен некий
 * "прокси", который при подаче в него &lt;columns&gt; вернёт контейнер
 * {@link ContainerNode} (т. к. этот элемент сам по себе никак не
 * распарсивается), а для каждого элемента внутри контейнера проведёт парсинг с
 * помощью зарегистрированных подпарсеров.
 * 
 * @author Sergey Medelyan
 */
public class DBXmlElementParserProxy extends DBXmlElementParser<AbstractNode>
{
    /** Тип контейнера (например, columns, indexes) */
    public final String FOR_TYPE;
    private ContainerNode container;

    /**
     * Конструктор задаст значение {@link DBXmlElementParserProxy#FOR_TYPE}
     * равным имени переданного контейнера
     * 
     * @param container контейнер, в который будут складываться распарсенные
     *            элементы
     */
    public DBXmlElementParserProxy(ContainerNode container)
    {
        FOR_TYPE = container.getName();
        this.container = container;
    }

    @Override
    protected AbstractNode parseObjectOnly(Node t)
        throws DBObjectParsingException
    {
        return container.clone();
    }

}
