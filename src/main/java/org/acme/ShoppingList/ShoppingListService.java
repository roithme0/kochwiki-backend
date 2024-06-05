package org.acme.ShoppingList;

import org.acme.ErrorResponse.ErrorResponse;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/shoppingLists")
public class ShoppingListService {
    private static final Logger LOG = Logger.getLogger(ShoppingListService.class);

    @Inject
    private ShoppingListResource shoppingListResource;

    @GET
    public Response listAll() {
        LOG.info("GET: list all shoppingLists ...");
        try {
            return Response.ok(shoppingListResource.listAll()).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{customUserId}")
    public Response findByCustomUserId(@PathParam("id") final Long customUserId) {
        LOG.info("GET: find shoppingList for customUser with id '" + customUserId + "' ...");
        try {
            ShoppingList shoppingList = shoppingListResource.findByCustomUserId(customUserId);
            if (shoppingList == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(),
                                "ShoppingList for customUser with id '" + customUserId + "' not found"))
                        .build();
            }
            return Response.ok(shoppingList).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }
}
