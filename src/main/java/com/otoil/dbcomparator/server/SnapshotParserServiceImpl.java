package com.otoil.dbcomparator.server;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.ws.rs.Path;

import com.otoil.dbcomparator.client.interfaces.SnapshotParserService;
import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.server.exceptions.ZipIsEmptyException;
import com.otoil.dbcomparator.server.parsing.DBColumnParser;
import com.otoil.dbcomparator.server.parsing.DBTableParser;
import com.otoil.dbcomparator.server.parsing.DBXmlElementParserProxy;
import com.otoil.dbcomparator.server.parsing.DBZipEntryParser;
import com.otoil.dbcomparator.shared.ColumnsContainerNode;
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.TablesContainerNode;


@Path("snapshot-parser")
public class SnapshotParserServiceImpl implements SnapshotParserService
{
    // TODO: errors handling
    // TODO: prevent skipping first element if there are no subfolders in zip
    public DatabaseNode parseSnapshot(String snapshotId)
    {
        try
        {
            ZipFile zf = openZipFile(snapshotId);
            Enumeration<? extends ZipEntry> entries = zf.entries();
            DatabaseNode dbRoot = null;
            DBZipEntryParser entryParser = createEntryParser();

            TablesContainerNode tablesContainer = new TablesContainerNode();
            entryParser.registerContainerFor(
                tablesContainer.getSubelementsType(), tablesContainer);

            while (entries.hasMoreElements())
            {
                ZipEntry entry = entries.nextElement();
                if (dbRoot == null)
                {
                    dbRoot = constructDatabaseNode(entry);
                }
                else if (!entry.isDirectory())
                {
                    entryParser.parse(null, zf.getInputStream(entry), null);
                }
                else
                {
                    // TODO: indicate that there are too many directories
                }
            }

            zf.close();
            dbRoot.addChild(tablesContainer);
            return dbRoot;
        }
        catch (ZipIsEmptyException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (DBObjectParsingException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private ZipFile openZipFile(String snapshotId)
        throws ZipException, IOException
    {
        File file = new File(System.getProperty("java.io.tmpdir"),
            snapshotId + ".zip");
        return new ZipFile(file);
    }

    private DBZipEntryParser createEntryParser()
    {
        DBZipEntryParser entryParser = new DBZipEntryParser();
        entryParser.registerSubparser(DBTableParser.FOR_TYPE,
            new DBTableParser());
        DBXmlElementParserProxy columnsProxy = new DBXmlElementParserProxy(
            new ColumnsContainerNode());
        columnsProxy.registerSubparser(DBColumnParser.FOR_TYPE,
            new DBColumnParser());
        entryParser.registerSubparser(DBTableParser.FOR_TYPE,
            columnsProxy.FOR_TYPE, columnsProxy);
        return entryParser;
    }

    private DatabaseNode constructDatabaseNode(ZipEntry firstEntry)
        throws ZipIsEmptyException
    {
        if (firstEntry == null)
        {
            throw new ZipIsEmptyException();
        }

        String dbName = firstEntry.getName().endsWith("/")
            ? firstEntry.getName().substring(0,
                firstEntry.getName().length() - 1)
            : "(not specified)";

        return new DatabaseNode(dbName);
    }
}
