package org.acme.Recipe;

import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.transaction.Transactional;

@Path("/recipes")
public class RecipeService {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = Logger.getLogger(RecipeService.class);

    /**
     * Resource to access recipes.
     */
    @Inject
    private RecipeResource recipeResource;

    /**
     * @return list of all recipes.
     */
    @GET
    public List<Recipe> findAll() {
        LOG.info("GET: finding all recipes ...");
        return recipeResource.listAll();
    }

    /**
     * @param id of recipe to find.
     * @return recipe with given id.
     */
    @GET
    @Path("/{id}")
    public Recipe findById(@PathParam("id") final Long id) {
        LOG.info("GET: finding recipe by id '" + id + "' ...");
        return recipeResource.findById(id);
    }

    /**
     * @param recipe to create.
     * @return created recipe.
     */
    @POST
    @Transactional
    public Recipe create(final Recipe recipe) {
        LOG.info("POST: creating recipe '" + recipe.name + "' ...");
        recipeResource.persist(recipe);
        return recipe;
    }

    /**
     * @param id of recipe to patch.
     * @param updates to apply.
     * @return patched recipe.
     */
    @PATCH
    @Transactional
    @Path("/{id}")
    public Recipe patch(@PathParam("id") final Long id, final Map<String, Object> updates) {
        Recipe recipe = findById(id);
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe with id " + id + " does not exist");
        }
        LOG.info("PATCH: patching recipe with id '" + id + "' ...");
        return recipeResource.patch(recipe, updates);
    }

    /**
     * Delete recipe with given id.
     * @param id of recipe to delete.
     */
    @DELETE
    @Transactional
    @Path("/{id}")
    public void delete(@PathParam("id") final Long id) {
        LOG.info("DELETE: deleting recipe with id '" + id + "' ...");
        Recipe entity = Recipe.findById(id);
        recipeResource.delete(entity);
    }
}
