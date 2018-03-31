package com.otoil.dbcomparator.server.snapshotparsing;


import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.shared.AbstractNode;

/**
 * Класс для парсинга ZipEntry (точнее, InputStream из этой ZipEntry)
 * Де-факто служит прокси для фактических подпарсерсов.
 * @author kakeru
 *
 */
public class DBZipEntryParser extends DBObjectParser<InputStream, AbstractNode>
{

    @Override
    protected AbstractNode parseObjectOnly(InputStream t)
        throws DBObjectParsingException
    {
        try
        {
            Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(t);
            Element rootNode = doc.getDocumentElement();
            if (subparsers.containsKey(rootNode.getNodeName()))
            {
                subparsers.get(rootNode.getNodeName()).parse(getParentDBNode(),
                    rootNode, rootNode::getChildNodes);
            }
            else
            {
                // TODO: indicate that this type of node was skipped
            }
        }
        catch (SAXException | IOException | ParserConfigurationException e)
        {
            // TODO Auto-generated catch block
            throw new DBObjectParsingException(e);
        }
        
        // если подпарсер был найден, то он сам разберётся
        // с добавлением к родительскому узлу
        // этот класс - прокси, так что сам по себе он ничего не
        // возвращает в качестве результата парсинга
        return null;
    }

//    public static NodeList defaultZipEntryChildrenExtractor(InputStream in)
//        throws DBObjectParsingException
//    {
//        return null;
////        Document doc;
////        try
////        {
////            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
////                .parse(in);
////            Element rootNode = doc.getDocumentElement();
////            return rootNode.getChildNodes();
////        }
////        catch (SAXException | IOException | ParserConfigurationException e)
////        {
////            // TODO Auto-generated catch block
////            throw new DBObjectParsingException(e);
////        }
//    }
}
