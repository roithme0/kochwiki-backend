package org.acme.Ingredient;

import java.util.List;

import org.acme.ErrorResponse.ErrorResponse;
import org.acme.FoodstuffMetaData.FoodstuffMetaData;
import org.jboss.logging.Logger;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;

@Path("/ingredients")
public class IngredientService {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = Logger.getLogger(IngredientService.class);

    /**
     * Resource to access ingredients.
     */
    @Inject
    private IngredientResource ingredientResource;

    /**
     * @return list of all ingredients.
     */
    @GET
    public Response listAll() {
        LOG.info("GET: list all ingredients ...");
        try {
            return Response.ok(ingredientResource.listAll()).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }
}
