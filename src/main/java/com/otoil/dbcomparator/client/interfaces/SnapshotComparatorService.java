package com.otoil.dbcomparator.client.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.DirectRestService;

import com.otoil.dbcomparator.shared.ComparisonRequest;
import com.otoil.dbcomparator.shared.ComparisonResult;
import com.otoil.dbcomparator.shared.DatabaseNode;

@Path("snapshot-comparator")
public interface SnapshotComparatorService extends DirectRestService
{
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    ComparisonResult compareSnapshot(ComparisonRequest request);
}
