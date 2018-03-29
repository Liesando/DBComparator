package com.otoil.dbcomparator.server;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.otoil.dbcomparator.shared.ColumnNode;
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.TableNode;


@Path("snapshot-parser")
public class SnapshotParser
{
    @Path("/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public DatabaseNode parseSnapshot(@PathParam("id") String snapshotId)
    {
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
}
