package org.acme.IngredientMetaData;

import java.util.Map;
import java.util.HashMap;

public class IngredientMetaData {

    /**
     * @return Key value pairs of the verbose names and names of the ingredient fields.
     */
    public static Map<String, String> getVerboseNames() {
        Map<String, String> verboseNames = new HashMap<>();
        verboseNames.put("name", "Name");
        verboseNames.put("brand", "Marke");
        verboseNames.put("unit", "Einheit");
        verboseNames.put("unitVerbose", "Einheit");
        verboseNames.put("kcal", "Kalorien");
        verboseNames.put("carbs", "Kohlenhydrate");
        verboseNames.put("protein", "Proteine");
        verboseNames.put("fat", "Fett");
        return verboseNames;
    }

    /**
     * @return Key value pairs of the unit choices verbose names and names.
     */
    public static Map<String, String> getUnitChoices() {
        Map<String, String> unitChoices = new HashMap<>();
        unitChoices.put("G", "g");
        unitChoices.put("ML", "ml");
        unitChoices.put("PIECE", "Stk.");
        return unitChoices;
    }
}
