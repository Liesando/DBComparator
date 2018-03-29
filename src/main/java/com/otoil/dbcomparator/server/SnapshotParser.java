package com.otoil.dbcomparator.server;

import java.awt.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.otoil.dbcomparator.shared.ColumnNode;
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.TableNode;

@Path("snapshot-parser")
public class SnapshotParser
{
    @Path("/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DatabaseNode parseSnapshot()
    {
        // mock
        DatabaseNode root = new DatabaseNode("DB 1");
        TableNode table2 = new TableNode("Table 2");
        TableNode table3 = new TableNode("Table 3");
        root.addChild(table2);
        root.addChild(table3);
        table2.addChild(new ColumnNode("Col 1"));
        table2.addChild(new ColumnNode("Col 2"));
        table3.addChild(new ColumnNode("Col 1"));
        
        return root;
    }
}
