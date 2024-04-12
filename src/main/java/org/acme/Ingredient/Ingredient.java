package org.acme.Ingredient;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.UniqueConstraint;

import org.acme.Amount.Amount;
import org.acme.IngredientMetaData.UnitEnum;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name", "brand" })
})
public class Ingredient extends PanacheEntity {

    /**
     * Maximum length of name attribute.
     */
    private static final int MAX_LENGTH_NAME = 50;
    /**
     * Maximum length of brand attribute.
     */
    private static final int MAX_LENGTH_BRAND = 100;
    /**
     * Maximum length of unit attribute.
     */
    private static final int MAX_LENGTH_UNIT = 5;
    /**
     * Maximum length of kcal attribute.
     */
    private static final int MAX_LENGTH_KCAL = 3;
    /**
     * Maximum length of carbs attribute.
     */
    private static final int MAX_LENGTH_CARBS = 3;
    /**
     * Maximum length of protein attribute.
     */
    private static final int MAX_LENGTH_PROTEIN = 3;
    /**
     * Maximum length of fat attribute.
     */
    private static final int MAX_LENGTH_FAT = 3;

    /**
     * Name of the ingredient.
     */
    @Column(nullable = false, length = MAX_LENGTH_NAME)
    public String name;

    /**
     * Brand of the ingredient.
     */
    @Column(nullable = true, length = MAX_LENGTH_BRAND)
    public String brand;

    /**
     * Unit of the ingredient.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = MAX_LENGTH_UNIT)
    public UnitEnum unit;

    /**
     * Nutritional value of the ingredient.
     */
    @Column(nullable = true, length = MAX_LENGTH_KCAL)
    public Integer kcal;

    /**
     * Nutritional value of the ingredient.
     */
    @Column(nullable = true, length = MAX_LENGTH_CARBS)
    public Integer carbs;

    /**
     * Nutritional value of the ingredient.
     */
    @Column(nullable = true, length = MAX_LENGTH_PROTEIN)
    public Integer protein;

    /**
     * Nutritional value of the ingredient.
     */
    @Column(nullable = true, length = MAX_LENGTH_FAT)
    public Integer fat;

    /**
     * List of amounts the ingredient is used in.
     */
    @OneToMany(mappedBy = "ingredient", fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference("amount-ingredient")
    public List<Amount> amounts = new ArrayList<>();

    /**
     * @return name of unit of ingredient.
     */
    public String getUnit() {
        return unit.name();
    }

    /**
     * @return verbose name of unit of ingredient.
     */
    public String getUnitVerbose() {
        return unit.getUnitVerbose();
    }

    /**
     * Set brand of ingredient.
     * If brand is empty, set to null.
     * @param newBrand new brand of ingredient.
     */
    public void setBrand(final String newBrand) {
        if (newBrand == "") {
            brand = null;
            return;
        }
        brand = newBrand;
    }

    /**
     * Set unit of ingredient.
     * @param newUnit new unit of ingredient.
     */
    public void setUnit(final String newUnit) {
        try {
            unit = UnitEnum.valueOf(newUnit);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Einheit muss 'G', 'ML' oder 'PIECE' sein.");
        }
    }

    /**
     * Set kcal of ingredient.
     * If kcal is empty, set to null.
     * Check for invalid values.
     * @param newKcal new kcal of ingredient.
     */
    public void setKcal(final Integer newKcal) {
        if (newKcal == null) {
            kcal = null;
            return;
        }
        this.checkNutritionalValue(newKcal);
        kcal = newKcal;
    }

    /**
     * Set carbs of ingredient.
     * If carbs is empty, set to null.
     * Check for invalid values.
     * @param newCarbs new carbs of ingredient.
     */
    public void setCarbs(final Integer newCarbs) {
        if (newCarbs == null) {
            carbs = null;
            return;
        }
        this.checkNutritionalValue(newCarbs);
        carbs = newCarbs;
    }

    /**
     * Set protein of ingredient.
     * If protein is empty, set to null.
     * Check for invalid values.
     * @param newProtein new protein of ingredient.
     */
    public void setProtein(final Integer newProtein) {
        if (newProtein == null) {
            protein = null;
            return;
        }
        this.checkNutritionalValue(newProtein);
        protein = newProtein;
    }

    /**
     * Set fat of ingredient.
     * If fat is empty, set to null.
     * Check for invalid values.
     * @param newFat new fat of ingredient.
     */
    public void setFat(final Integer newFat) {
        if (newFat == null) {
            fat = null;
            return;
        }
        this.checkNutritionalValue(newFat);
        fat = newFat;
    }

    /**
     * Check new nutritional value.
     * @param newValue New nutritional value.
     */
    private void checkNutritionalValue(final int newValue) {
        final int minValue = 0;
        final int maxValue = 999;

        if (newValue < minValue || newValue > maxValue) {
            throw new IllegalArgumentException(String.format("Wert muss zwischen %d und %d liegen.", minValue, maxValue));
        }
    }

    /**
     * Add single amount to ingredient.
     * @param newAmount New amount to add.
     */
    public void addAmount(final Amount newAmount) {
        amounts.add(newAmount);
    }

    /**
     * Default constructor for hibernate.
     */
    public Ingredient() {
    }

    /**
     * Constructor for ingredient.
     * @param paramName Name of ingredient.
     * @param paramBrand Brand of ingredient.
     * @param paramUnit Unit of ingredient.
     * @param paramKcal Kcal of ingredient.
     * @param paramCarbs Carbs of ingredient.
     * @param paramProtein Protein of ingredient.
     * @param paramFat Fat of ingredient.
     */
    public Ingredient(
            final String paramName,
            final String paramBrand,
            final String paramUnit,
            final Integer paramKcal,
            final Integer paramCarbs,
            final Integer paramProtein,
            final Integer paramFat) {
        this.name = paramName;
        this.setBrand(paramBrand);
        this.setUnit(paramUnit);
        this.setKcal(paramKcal);
        this.setCarbs(paramCarbs);
        this.setProtein(paramProtein);
        this.setFat(paramFat);
    }

    /**
     * @return String representation of ingredient.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ingredient)) {
            return false;
        }
        Ingredient ingredient = (Ingredient) obj;
        return Objects.equals(name, ingredient.name)
                && Objects.equals(brand, ingredient.brand)
                && Objects.equals(unit, ingredient.unit)
                && Objects.equals(kcal, ingredient.kcal)
                && Objects.equals(carbs, ingredient.carbs)
                && Objects.equals(protein, ingredient.protein)
                && Objects.equals(fat, ingredient.fat);
    }

    /**
     * @return Hash code of ingredient.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, brand, unit, kcal, carbs, protein, fat);
    }
}
