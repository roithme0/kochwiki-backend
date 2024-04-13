package org.acme.FoodstuffMetaData;

public enum UnitEnum {
    /**
     * Grams.
     */
    G("g"),
    /**
     * Milliliters.
     */
    ML("ml"),
    /**
     * Pieces.
     */
    PIECE("Stk.");

    /**
     * Unit.
     */
    private final String unit;

    /**
     * Constructor.
     * 
     * @param newUnit unit to set.
     */
    private UnitEnum(final String newUnit) { // needs to be private for some reason
        unit = newUnit;
    }

    /**
     * @return Verbose name of unit.
     */
    public String getUnitVerbose() {
        return unit;
    }
}
