package io.github.nicolasritouet.rest.boundary;


import io.github.nicolasritouet.domain.boundary.MonitorJobExecutor;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/jobs")
public class MonitorJobRestApi implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject @Default
    private MonitorJobExecutor monitorJobExecutor;

    @GET
    @Path("/start")
    public Response startAllJobs() {
        monitorJobExecutor.startCheckJobs();
        return Response.accepted().entity("Jobs check started").build();
    }
}
