package org.acme.Foodstuff;

import java.util.Map;

import org.jboss.logging.Logger;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FoodstuffResource implements PanacheRepository<Foodstuff> {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = Logger.getLogger(FoodstuffService.class);

    /**
     * Patch an foodstuff with the given updates.
     * Update all fields except id.
     * 
     * @param foodstuff foodstuff to patch
     * @param updates   updates to apply
     * @return patched foodstuff
     */
    public Foodstuff patch(final Foodstuff foodstuff, final Map<String, Object> updates) {
        LOG.debug("updates: " + updates);
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            switch (key) {
                case "name":
                    foodstuff.name = (String) value;
                    break;
                case "brand":
                    foodstuff.brand = (String) value;
                    break;
                case "unit":
                    foodstuff.setUnit((String) value);
                    break;
                case "kcal":
                    foodstuff.kcal = (Integer) value;
                    break;
                case "carbs":
                    foodstuff.carbs = (Double) value;
                    break;
                case "protein":
                    foodstuff.protein = (Double) value;
                    break;
                case "fat":
                    foodstuff.fat = (Double) value;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field '" + key + "'");
            }
        }
        return foodstuff;
    }
}
