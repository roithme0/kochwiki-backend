package org.acme.Ingredient;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.ws.rs.Path;
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
    public List<Ingredient> listAll() {
        LOG.info("GET: list all ingredients ...");
        return ingredientResource.listAll();
    }
}
