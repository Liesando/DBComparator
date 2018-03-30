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
        request.getSource().getChildren().get(0).setState(NodeState.CHANGED);
        request.getSource().getChildren().get(1).setState(NodeState.DELETED);
        request.getDest().getChildren().get(0).setState(NodeState.ADDED);
        request.getDest().getChildren().get(1).setState(NodeState.CHANGED);
        request.getDest().getChildren().get(1).getChildren().get(0)
            .setState(NodeState.CHANGED);
        request.getDest().getChildren().get(1).getChildren().get(1)
            .setState(NodeState.CHANGED);
        
        return new ComparisonResult(request.getSource(), request.getDest());
    }

}
