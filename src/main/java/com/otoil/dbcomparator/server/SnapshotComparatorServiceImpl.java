package com.otoil.dbcomparator.server;


import javax.ws.rs.Path;

import com.otoil.dbcomparator.client.interfaces.SnapshotComparatorService;
import com.otoil.dbcomparator.server.comparing.DBColumnComparator;
import com.otoil.dbcomparator.server.comparing.DBContainerComparator;
import com.otoil.dbcomparator.server.comparing.DBRootComparator;
import com.otoil.dbcomparator.server.comparing.DBTableComparator;
import com.otoil.dbcomparator.shared.ColumnsContainerNode;
import com.otoil.dbcomparator.shared.ComparisonRequest;
import com.otoil.dbcomparator.shared.ComparisonResult;
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.TableNode;
import com.otoil.dbcomparator.shared.TablesContainerNode;


@Path("snapshot-comparator")
public class SnapshotComparatorServiceImpl implements SnapshotComparatorService
{

    @Override
    public ComparisonResult compareSnapshot(ComparisonRequest request)
    {
        DBRootComparator rootComparator = createRootComparator();

        rootComparator.performComparison(request.getSource(),
            request.getDest());
        rootComparator.performComparison(request.getDest(),
            request.getSource());

        return new ComparisonResult(request.getSource(), request.getDest());
    }

    private DBRootComparator createRootComparator()
    {
        DBRootComparator root = new DBRootComparator();
        DBContainerComparator<TablesContainerNode, DatabaseNode> tablesContainerComparator = new DBContainerComparator<TablesContainerNode, DatabaseNode>(
            TablesContainerNode.class, DatabaseNode.class);

        DBTableComparator tableComparator = new DBTableComparator();
        DBContainerComparator<ColumnsContainerNode, TableNode> columnsContainerComparator = new DBContainerComparator<ColumnsContainerNode, TableNode>(
            ColumnsContainerNode.class, TableNode.class);

        DBColumnComparator columnComparator = new DBColumnComparator();

        root.registerSubcomparator(tablesContainerComparator);
        tablesContainerComparator.registerSubcomparator(tableComparator);
        tableComparator.registerSubcomparator(columnsContainerComparator);
        columnsContainerComparator.registerSubcomparator(columnComparator);

        return root;
    }
}
