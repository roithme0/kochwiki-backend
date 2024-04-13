package org.acme.Foodstuff;

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

@Path("/foodstuffs")
public class FoodstuffService {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = Logger.getLogger(FoodstuffService.class);

    /**
     * Resource to access foodstuffs.
     */
    @Inject
    private FoodstuffResource foodstuffResource;

    /**
     * @return list of all foodstuffs.
     */
    @GET
    public List<Foodstuff> listAll() {
        LOG.info("GET: listing all foodstuffs ...");
        return foodstuffResource.listAll();
    }

    /**
     * @param id of foodstuff to find.
     * @return foodstuff with given id.
     */
    @GET
    @Path("/{id}")
    public Foodstuff findById(@PathParam("id") final Long id) {
        LOG.info("GET: find foodstuff with id '" + id + "' ...");
        return foodstuffResource.findById(id);
    }

    /**
     * @param foodstuff to create.
     * @return created foodstuff.
     */
    @POST
    @Transactional
    public Foodstuff create(final Foodstuff foodstuff) {
        LOG.info("POST: creating foodstuff '" + foodstuff.name + "' ...");
        foodstuffResource.persist(foodstuff);
        return foodstuff;
    }

    /**
     * @param id      of foodstuff to update.
     * @param updates to apply.
     * @return updated foodstuff.
     */
    @PATCH
    @Path("/{id}")
    @Transactional
    public Foodstuff patch(@PathParam("id") final Long id, final Map<String, Object> updates) {
        LOG.info("PATCH: patching foodstuff with id '" + id + "' ...");
        Foodstuff foodstuff = findById(id);
        if (foodstuff == null) {
            throw new IllegalArgumentException("Foodstuff with id " + id + " does not exist");
        }
        return foodstuffResource.patch(foodstuff, updates);
    }

    /**
     * Delete foodstuff with given id.
     * 
     * @param id of foodstuff to delete.
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") final Long id) {
        LOG.info("DELETE: deleting foodstuff with id '" + id + "' ...");
        Foodstuff foodstuff = Foodstuff.findById(id);
        foodstuff.delete();
    }
}
