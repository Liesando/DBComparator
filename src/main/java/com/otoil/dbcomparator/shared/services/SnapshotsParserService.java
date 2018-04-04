package com.otoil.dbcomparator.shared.services;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.DirectRestService;

import com.otoil.dbcomparator.shared.beans.DatabaseNode;

@Path("snapshot-parser")
public interface SnapshotsParserService extends DirectRestService
{
    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    DatabaseNode parseSnapshot(@PathParam("id") String snapshotId);
}
