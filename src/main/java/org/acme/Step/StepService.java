package org.acme.Step;

import java.util.List;

import org.acme.ErrorResponse.ErrorResponse;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/steps")
public class StepService {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = Logger.getLogger(StepService.class);

    /**
     * Resource to access steps.
     */
    @Inject
    private StepResource stepResource;

    /**
     * @return all steps
     */
    @GET
    public Response findAll() {
        LOG.info("GET: finding all steps ...");
        try {
            return Response.ok(stepResource.listAll()).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }
}
