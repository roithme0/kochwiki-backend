package org.acme.Ingredient;

import java.util.Map;

import org.jboss.logging.Logger;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IngredientResource implements PanacheRepository<Ingredient> {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = Logger.getLogger(IngredientService.class);

    /**
     * Patch an ingredient with the given updates.
     * Update all fields except id.
     * @param ingredient ingredient to patch
     * @param updates updates to apply
     * @return patched ingredient
     */
    public Ingredient patch(final Ingredient ingredient, final Map<String, Object> updates) {
        LOG.debug("updates: " + updates);
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            switch (key) {
                case "name":
                    ingredient.name = (String) value;
                    break;
                case "brand":
                    ingredient.brand = (String) value;
                    break;
                case "unit":
                    ingredient.setUnit((String) value);
                    break;
                case "kcal":
                    ingredient.kcal = (Integer) value;
                    break;
                case "carbs":
                    ingredient.carbs = (Integer) value;
                    break;
                case "protein":
                    ingredient.protein = (Integer) value;
                    break;
                case "fat":
                    ingredient.fat = (Integer) value;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field '" + key + "'");
            }
        }
        return ingredient;
    }
}
