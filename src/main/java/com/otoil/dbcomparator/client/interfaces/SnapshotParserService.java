package com.otoil.dbcomparator.client.interfaces;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.otoil.dbcomparator.shared.DatabaseNode;

public interface SnapshotParserService extends RestService
{
    @POST
    @Path("/rest/snapshot-parser/{id}")
    void parseSnapshot(@PathParam("id") String snapshotId,
        MethodCallback<DatabaseNode> callback);
}
