package org.acme.FoodstuffMetaData;

import java.util.Map;

import org.acme.ErrorResponse.ErrorResponse;
import org.jboss.logging.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/foodstuffs-meta-data")
public class FoodstuffMetaDataService {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = Logger.getLogger(FoodstuffMetaDataService.class);

    /**
     * @return verbose names of the foodstuffs
     */
    @GET
    @Path("/verbose-names")
    public Response getVerboseNames() {
        LOG.info("GET: getting foodstuff verbose names ...");
        try {
            return Response.ok(FoodstuffMetaData.getVerboseNames()).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * @return unit choices for the foodstuffs
     */
    @GET
    @Path("/unit-choices")
    public Response getUnitChoices() {
        LOG.info("GET: getting foodstuff unit choices ...");
        try {
            return Response.ok(FoodstuffMetaData.getUnitChoices()).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }
}
