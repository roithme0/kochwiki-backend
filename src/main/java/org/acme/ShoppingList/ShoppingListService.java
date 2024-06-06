package org.acme.ShoppingList;

import org.acme.CustomUser.CustomUser;
import org.acme.ErrorResponse.ErrorResponse;
import org.acme.Ingredient.Ingredient;
import org.acme.Ingredient.IngredientResource;
import org.acme.ShoppingListItem.ShoppingListItemIngredient;
import org.acme.ShoppingListItem.ShoppingListItemIngredientResource;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/shoppingLists")
public class ShoppingListService {
    private static final Logger LOG = Logger.getLogger(ShoppingListService.class);

    @Inject
    private ShoppingListResource shoppingListResource;

    @Inject
    private ShoppingListItemIngredientResource shoppingListItemIngredientResource;

    @Inject
    IngredientResource ingredientResource;

    @Inject
    EntityManager entityManager;

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
    public Response findByCustomUserId(@PathParam("customUserId") final Long customUserId) {
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

    @PATCH
    @Transactional
    @Path("/{customUserId}/addIngredient/{ingredientId}")
    public Response addIngredient(@PathParam("customUserId") final Long customUserId,
            @PathParam("ingredientId") final Long ingredientId,
            final Float amount) {
        LOG.info("PATCH: adding ingredient with id '" + ingredientId + "' to shoppingList ...");
        try {
            ShoppingList shoppingList = shoppingListResource.findByCustomUserId(customUserId);
            if (shoppingList == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(),
                                "ShoppingList for customUser with id '" + customUserId + "' not found"))
                        .build();
            }
            Ingredient ingredient = ingredientResource.findById(ingredientId);
            if (ingredient == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(),
                                "Ingredient with id '" + ingredientId + "' not found"))
                        .build();
            }
            ShoppingListItemIngredient shoppingListItemIngredient = null;
            try {
                shoppingListItemIngredient = entityManager.createQuery(
                        "SELECT shoppingListItemIngredient FROM ShoppingListItemIngredient shoppingListItemIngredient WHERE shoppingListItemIngredient.ingredient = :ingredient",
                        ShoppingListItemIngredient.class)
                        .setParameter("ingredient", ingredient)
                        .getSingleResult();
            } catch (Exception e) {
                // ingredient not used in any shopping list
                shoppingListItemIngredient = new ShoppingListItemIngredient(ingredient,
                        amount);
            }
            shoppingList.addIngredient(shoppingListItemIngredient);
            shoppingListItemIngredientResource.persist(shoppingListItemIngredient);
            shoppingListResource.persist(shoppingList);
            return Response.ok(shoppingList).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }

    @PATCH
    @Transactional
    @Path("/{customUserId}/removeIngredient/{ingredientId}")
    public Response removeIngredient(@PathParam("customUserId") final Long customUserId,
            @PathParam("ingredientId") final Long ingredientId,
            final Float amount) {
        LOG.info("PATCH: removing ingredient with id '" + ingredientId + "' from shoppingList ...");
        try {
            ShoppingList shoppingList = shoppingListResource.findByCustomUserId(customUserId);
            if (shoppingList == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(),
                                "ShoppingList for customUser with id '" + customUserId + "' not found"))
                        .build();
            }
            Ingredient ingredient = ingredientResource.findById(ingredientId);
            if (ingredient == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(),
                                "Ingredient with id '" + ingredientId + "' not found"))
                        .build();
            }
            ShoppingListItemIngredient shoppingListItemIngredient = null;
            try {
                shoppingListItemIngredient = entityManager.createQuery(
                        "SELECT shoppingListItemIngredient FROM ShoppingListItemIngredient shoppingListItemIngredient WHERE shoppingListItemIngredient.ingredient = :ingredient",
                        ShoppingListItemIngredient.class)
                        .setParameter("ingredient", ingredient)
                        .getSingleResult();

                shoppingList.removeIngredient(shoppingListItemIngredient);
                shoppingListItemIngredientResource.persist(shoppingListItemIngredient);
                shoppingListResource.persist(shoppingList);
                return Response.ok(shoppingList).build();
            } catch (Exception e) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(),
                                "Ingredient with id '" + ingredientId + "' not found in shoppingList"))
                        .build();
            }
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }
}
