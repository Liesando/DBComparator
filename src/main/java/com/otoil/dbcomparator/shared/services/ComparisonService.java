package com.otoil.dbcomparator.shared.services;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.DirectRestService;


@Path("snapshot-comparator")
public interface ComparisonService extends DirectRestService
{
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    ComparisonBean compareSnapshot(ComparisonBean request);
}
