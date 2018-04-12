package com.otoil.dbcomparator.server.services;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.ws.rs.Path;

import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.server.exceptions.ZipIsEmptyException;
import com.otoil.dbcomparator.server.parsing.DBColumnParser;
import com.otoil.dbcomparator.server.parsing.DBPrimaryConstraintParser;
import com.otoil.dbcomparator.server.parsing.DBPrimaryKeyColumnParser;
import com.otoil.dbcomparator.server.parsing.DBTableParser;
import com.otoil.dbcomparator.server.parsing.DBXmlElementParserProxy;
import com.otoil.dbcomparator.server.parsing.DBZipEntryParser;
import com.otoil.dbcomparator.server.parsing.flattening.DBCommentaryParser;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;
import com.otoil.dbcomparator.shared.beans.containers.ColumnsContainerNode;
import com.otoil.dbcomparator.shared.beans.containers.ConstraintsContainerNode;
import com.otoil.dbcomparator.shared.beans.containers.TablesContainerNode;
import com.otoil.dbcomparator.shared.services.SnapshotsParserService;


@Path("snapshot-parser")
public class SnapshotsParserServiceImpl implements SnapshotsParserService
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
        entryParser.registerSubparser(DBTableParser.FOR_TYPE, "comments",
            new DBCommentaryParser());

        DBXmlElementParserProxy columnsProxy = new DBXmlElementParserProxy(
            new ColumnsContainerNode());
        columnsProxy.registerSubparser(DBColumnParser.FOR_TYPE,
            new DBColumnParser());
        columnsProxy.registerSubparser(DBColumnParser.FOR_TYPE, "comments",
            new DBCommentaryParser());

        entryParser.registerSubparser(DBTableParser.FOR_TYPE,
            columnsProxy.FOR_TYPE, columnsProxy);

        DBXmlElementParserProxy constraintsProxy = new DBXmlElementParserProxy(
            new ConstraintsContainerNode());

        DBPrimaryConstraintParser pkConstraintParser = new DBPrimaryConstraintParser();
        DBPrimaryKeyColumnParser pkColumnParser = new DBPrimaryKeyColumnParser();
        pkConstraintParser.registerSubparser(pkColumnParser.FOR_TYPE,
            pkColumnParser);

        constraintsProxy.registerSubparser(DBPrimaryConstraintParser.FOR_TYPE,
            pkConstraintParser);

        entryParser.registerSubparser(DBTableParser.FOR_TYPE,
            constraintsProxy.FOR_TYPE, constraintsProxy);

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
