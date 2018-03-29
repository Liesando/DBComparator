package com.otoil.dbcomparator.client;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.otoil.dbcomparator.client.interfaces.SnapshotsModel;
import com.otoil.dbcomparator.shared.AbstractNode;
import com.otoil.dbcomparator.shared.ColumnNode;
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.TableNode;


/**
 * Модель для парсинга слепков в {@link AbstractNode}
 * @author kakeru
 *
 */
public class SnapshotsModelZipRestImpl implements SnapshotsModel
{

    @Override
    public void parseSnapshot(String id, AsyncCallback<DatabaseNode> callback)
    {
        // mocking
        DatabaseNode root;
        if (id.equals("1"))
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

        callback.onSuccess(root);
    }

}
