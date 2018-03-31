package com.otoil.dbcomparator.server;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.ws.rs.Path;

import com.otoil.dbcomparator.client.interfaces.SnapshotParserService;
import com.otoil.dbcomparator.server.exceptions.DBObjectParsingException;
import com.otoil.dbcomparator.server.exceptions.ZipIsEmptyException;
import com.otoil.dbcomparator.server.snapshotparsing.DBColumnParser;
import com.otoil.dbcomparator.server.snapshotparsing.DBTableParser;
import com.otoil.dbcomparator.server.snapshotparsing.DBZipEntryParser;
import com.otoil.dbcomparator.shared.ColumnNode;
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.TableNode;


@Path("snapshot-parser")
public class SnapshotParserServiceImpl implements SnapshotParserService
{
    public DatabaseNode parseSnapshot(String snapshotId)
    {

        System.out.println("started with: " + snapshotId);
        // some tests with zip
        try
        {
            ZipFile zf = openZipFile(snapshotId);
            Enumeration<? extends ZipEntry> entries = zf.entries();
            DatabaseNode dbRoot = null;
            DBZipEntryParser entryParser = createEntryParser();
            
            while (entries.hasMoreElements())
            {
                ZipEntry entry = entries.nextElement();
                if (dbRoot == null)
                {
                    dbRoot = constructDatabaseNode(entry);
                }
                else if(!entry.isDirectory())
                {
                    entryParser.parse(dbRoot, zf.getInputStream(entry), null);
                }
                else
                {
                    // TODO: indicate that there are too many dirs
                }
            }

            System.out.println("!!!!ok!!!!\n");
            zf.close();
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

        // mock
        DatabaseNode root = null;
        if (snapshotId.equals("1"))
        {
            root = new DatabaseNode("DB 1");
            TableNode table2 = new TableNode("Table 2");
            TableNode table3 = new TableNode("Table 3");
            root.addChild(table2);
            root.addChild(table3);
            table2.addChild(new ColumnNode("Col 1"));
            table2.addChild(new ColumnNode("Col 2"));
            table3.addChild(new ColumnNode("Col 1"));
        }
        else
        {
            root = new DatabaseNode("DB 2");
            TableNode table1 = new TableNode("Table 1");
            TableNode table2 = new TableNode("Table 2");
            root.addChild(table1);
            root.addChild(table2);
            table1.addChild(new ColumnNode("Col 1"));
            table1.addChild(new ColumnNode("Col 2"));
            table2.addChild(new ColumnNode("##Col 1"));
            table2.addChild(new ColumnNode("##Col 2"));
        }

        return root;
    }
    
    private ZipFile openZipFile(String snapshotId) throws ZipException, IOException
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
        entryParser.registerSubparser(DBTableParser.FOR_TYPE,
            DBColumnParser.FOR_TYPE, new DBColumnParser());
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
