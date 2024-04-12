package org.acme.Amount;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.ws.rs.Path;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;

@Path("/amounts")
public class AmountService {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = Logger.getLogger(AmountService.class);

    /**
     * Resource to access amounts.
     */
    @Inject
    private AmountResource amountResource;

    /**
     * @return list of all amounts.
     */
    @GET
    public List<Amount> listAll() {
        LOG.info("GET: list all amounts ...");
        return amountResource.listAll();
    }
}
