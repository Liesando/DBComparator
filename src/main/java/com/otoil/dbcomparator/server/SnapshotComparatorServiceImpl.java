package com.otoil.dbcomparator.server;


import javax.ws.rs.Path;

import com.otoil.dbcomparator.client.interfaces.SnapshotComparatorService;
import com.otoil.dbcomparator.shared.ComparisonRequest;
import com.otoil.dbcomparator.shared.ComparisonResult;
import com.otoil.dbcomparator.shared.AbstractNode.NodeState;

@Path("snapshot-comparator")
public class SnapshotComparatorServiceImpl implements SnapshotComparatorService
{

    @Override
    public ComparisonResult compareSnapshot(ComparisonRequest request)
    {   
        return new ComparisonResult(request.getSource(), request.getDest());
    }

}
