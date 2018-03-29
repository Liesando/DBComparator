package com.otoil.dbcomparator.server;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/")
public class HelloResource
{
    @GET
    @Path("sayhello")
    @Produces("text/plain")
    public String getHello()
    {
        return "hello";
    }
}