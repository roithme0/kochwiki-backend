package org.acme.FoodstuffMetaData;

import java.util.Map;

import org.jboss.logging.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

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
    public Map<String, String> getVerboseNames() {
        LOG.info("GET: getting foodstuff verbose names ...");
        return FoodstuffMetaData.getVerboseNames();
    }

    /**
     * @return unit choices for the foodstuffs
     */
    @GET
    @Path("/unit-choices")
    public Map<String, String> getUnitChoices() {
        LOG.info("GET: getting foodstuff unit choices ...");
        return FoodstuffMetaData.getUnitChoices();
    }
}
