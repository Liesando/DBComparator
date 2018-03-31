package com.otoil.dbcomparator.server;


import javax.ws.rs.Path;

import com.otoil.dbcomparator.client.interfaces.SnapshotComparatorService;
import com.otoil.dbcomparator.server.snapshotcomparing.DBColumnComparator;
import com.otoil.dbcomparator.server.snapshotcomparing.DBRootComparator;
import com.otoil.dbcomparator.server.snapshotcomparing.DBTableComparator;
import com.otoil.dbcomparator.shared.ComparisonRequest;
import com.otoil.dbcomparator.shared.ComparisonResult;


@Path("snapshot-comparator")
public class SnapshotComparatorServiceImpl implements SnapshotComparatorService
{

    @Override
    public ComparisonResult compareSnapshot(ComparisonRequest request)
    {
        DBRootComparator rootComparator = createRootComparator();
        
        rootComparator.performComparison(request.getSource(), request.getDest());
        rootComparator.performComparison(request.getDest(), request.getSource());

        return new ComparisonResult(request.getSource(), request.getDest());
    }

    private DBRootComparator createRootComparator()
    {
        DBRootComparator root = new DBRootComparator();
        DBTableComparator tableComparator = new DBTableComparator();
        DBColumnComparator columnComparator = new DBColumnComparator();

        tableComparator.registerSubcomparator(columnComparator);
        root.registerSubcomparator(tableComparator);

        return root;
    }
}
