package org.acme.Ingredient;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import org.jboss.logging.Logger;

import java.util.List;
import java.util.Map;

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
    public List<Ingredient> listAll() {
        LOG.info("GET: listing all ingredients ...");
        return ingredientResource.listAll();
    }

    /**
     * @param id of ingredient to find.
     * @return ingredient with given id.
     */
    @GET
    @Path("/{id}")
    public Ingredient findById(@PathParam("id") final Long id) {
        LOG.info("GET: find ingredient with id '" + id + "' ...");
        return ingredientResource.findById(id);
    }

    /**
     * @param ingredient to create.
     * @return created ingredient.
     */
    @POST
    @Transactional
    public Ingredient create(final Ingredient ingredient) {
        LOG.info("POST: creating ingredient '" + ingredient.name + "' ...");
        ingredientResource.persist(ingredient);
        return ingredient;
    }

    /**
     * @param id of ingredient to update.
     * @param updates to apply.
     * @return updated ingredient.
     */
    @PATCH
    @Path("/{id}")
    @Transactional
    public Ingredient patch(@PathParam("id") final Long id, final Map<String, Object> updates) {
        LOG.info("PATCH: patching ingredient with id '" + id + "' ...");
        Ingredient ingredient = findById(id);
        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient with id " + id + " does not exist");
        }
        return ingredientResource.patch(ingredient, updates);
    }

    /**
     * Delete ingredient with given id.
     * @param id of ingredient to delete.
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") final Long id) {
        LOG.info("DELETE: deleting ingredient with id '" + id + "' ...");
        Ingredient ingredient = Ingredient.findById(id);
        ingredient.delete();
    }
}
