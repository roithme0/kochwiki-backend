package org.acme.Foodstuff;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.UniqueConstraint;

import org.acme.FoodstuffMetaData.UnitEnum;
import org.acme.Ingredient.Ingredient;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name", "brand" })
})
public class Foodstuff extends PanacheEntity {

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
     * Name of the foodstuff.
     */
    @Column(nullable = false, length = MAX_LENGTH_NAME)
    public String name;

    /**
     * Brand of the foodstuff.
     */
    @Column(nullable = true, length = MAX_LENGTH_BRAND)
    public String brand;

    /**
     * Unit of the foodstuff.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = MAX_LENGTH_UNIT)
    public UnitEnum unit;

    /**
     * Nutritional value of the foodstuff.
     */
    @Column(nullable = true, length = MAX_LENGTH_KCAL)
    public Integer kcal;

    /**
     * Nutritional value of the foodstuff.
     */
    @Column(nullable = true, length = MAX_LENGTH_CARBS)
    public Float carbs;

    /**
     * Nutritional value of the foodstuff.
     */
    @Column(nullable = true, length = MAX_LENGTH_PROTEIN)
    public Float protein;

    /**
     * Nutritional value of the foodstuff.
     */
    @Column(nullable = true, length = MAX_LENGTH_FAT)
    public Float fat;

    /**
     * List of ingredients the foodstuff is used in.
     */
    @OneToMany(mappedBy = "foodstuff", fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference("ingredient-foodstuff")
    public List<Ingredient> ingredients = new ArrayList<>();

    /**
     * @return name of unit of foodstuff.
     */
    public String getUnit() {
        return unit.name();
    }

    /**
     * @return verbose name of unit of foodstuff.
     */
    public String getUnitVerbose() {
        return unit.getUnitVerbose();
    }

    /**
     * Set brand of foodstuff.
     * If brand is empty, set to null.
     * 
     * @param newBrand new brand of foodstuff.
     */
    public void setBrand(final String newBrand) {
        if (newBrand == "") {
            brand = null;
            return;
        }
        brand = newBrand;
    }

    /**
     * Set unit of foodstuff.
     * 
     * @param newUnit new unit of foodstuff.
     */
    public void setUnit(final String newUnit) {
        try {
            unit = UnitEnum.valueOf(newUnit);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Einheit muss 'G', 'ML' oder 'PIECE' sein.");
        }
    }

    /**
     * Set kcal of foodstuff.
     * If kcal is empty, set to null.
     * Check for invalid values.
     * 
     * @param newKcal new kcal of foodstuff.
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
     * Set carbs of foodstuff.
     * If carbs is empty, set to null.
     * Check for invalid values.
     * 
     * @param newCarbs new carbs of foodstuff.
     */
    public void setCarbs(final Float newCarbs) {
        if (newCarbs == null) {
            carbs = null;
            return;
        }
        this.checkNutritionalValue(newCarbs);
        carbs = newCarbs;
    }

    /**
     * Set protein of foodstuff.
     * If protein is empty, set to null.
     * Check for invalid values.
     * 
     * @param newProtein new protein of foodstuff.
     */
    public void setProtein(final Float newProtein) {
        if (newProtein == null) {
            protein = null;
            return;
        }
        this.checkNutritionalValue(newProtein);
        protein = newProtein;
    }

    /**
     * Set fat of foodstuff.
     * If fat is empty, set to null.
     * Check for invalid values.
     * 
     * @param newFat new fat of foodstuff.
     */
    public void setFat(final Float newFat) {
        if (newFat == null) {
            fat = null;
            return;
        }
        this.checkNutritionalValue(newFat);
        fat = newFat;
    }

    /**
     * Check new nutritional value.
     * 
     * @param newValue New nutritional value.
     */
    private void checkNutritionalValue(final float newValue) {
        final double minValue = 0;
        final double maxValue = 1000;

        if (newValue < minValue || newValue >= maxValue) {
            throw new IllegalArgumentException(
                    String.format("Wert muss zwischen %d und %d liegen.", minValue, maxValue));
        }
    }

    /**
     * Add single ingredient to foodstuff.
     * 
     * @param newIngredient New ingredient to add.
     */
    public void addIngredient(final Ingredient newIngredient) {
        ingredients.add(newIngredient);
    }

    /**
     * Default constructor for hibernate.
     */
    public Foodstuff() {
    }

    /**
     * Constructor for foodstuff.
     * 
     * @param paramName    Name of foodstuff.
     * @param paramBrand   Brand of foodstuff.
     * @param paramUnit    Unit of foodstuff.
     * @param paramKcal    Kcal of foodstuff.
     * @param paramCarbs   Carbs of foodstuff.
     * @param paramProtein Protein of foodstuff.
     * @param paramFat     Fat of foodstuff.
     */
    public Foodstuff(
            final String paramName,
            final String paramBrand,
            final String paramUnit,
            final Integer paramKcal,
            final Float paramCarbs,
            final Float paramProtein,
            final Float paramFat) {
        this.name = paramName;
        this.setBrand(paramBrand);
        this.setUnit(paramUnit);
        this.setKcal(paramKcal);
        this.setCarbs(paramCarbs);
        this.setProtein(paramProtein);
        this.setFat(paramFat);
    }

    /**
     * @return String representation of foodstuff.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Foodstuff)) {
            return false;
        }
        Foodstuff foodstuff = (Foodstuff) obj;
        return Objects.equals(name, foodstuff.name)
                && Objects.equals(brand, foodstuff.brand)
                && Objects.equals(unit, foodstuff.unit)
                && Objects.equals(kcal, foodstuff.kcal)
                && Objects.equals(carbs, foodstuff.carbs)
                && Objects.equals(protein, foodstuff.protein)
                && Objects.equals(fat, foodstuff.fat);
    }

    /**
     * @return Hash code of foodstuff.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, brand, unit, kcal, carbs, protein, fat);
    }
}
