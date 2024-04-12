package org.acme.IngredientMetaData;

import java.util.Map;

import org.jboss.logging.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/ingredients-meta-data")
public class IngredientMetaDataService {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = Logger.getLogger(IngredientMetaDataService.class);

    /**
     * @return verbose names of the ingredients
     */
    @GET
    @Path("/verbose-names")
    public Map<String, String> getVerboseNames() {
        LOG.info("GET: getting ingredient verbose names ...");
        return IngredientMetaData.getVerboseNames();
    }

    /**
     * @return unit choices for the ingredients
     */
    @GET
    @Path("/unit-choices")
    public Map<String, String> getUnitChoices() {
        LOG.info("GET: getting ingredient unit choices ...");
        return IngredientMetaData.getUnitChoices();
    }
}
