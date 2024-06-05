package org.acme.ShoppingListItem;

import org.acme.FoodstuffMetaData.UnitEnum;
import org.acme.Ingredient.Ingredient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ShoppingListItemIngredient extends PanacheEntity implements IShoppingListItem {
    // #region fields

    @Column(nullable = false)
    public Boolean isChecked;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    @JsonBackReference("ingredient-shoppingListItemIngredients")
    public Ingredient ingredient;

    // #endregion

    // #region getters

    public Boolean getIsChecked() {
        return this.isChecked;
    }

    public String getName() {
        return this.ingredient.getName();
    }

    public String getBrand() {
        return this.ingredient.getBrand();
    }

    public Float getAmount() {
        return this.ingredient.amount;
    }

    public UnitEnum getUnit() {
        return this.ingredient.getUnit();
    }

    public String getUnitVerbose() {
        return this.ingredient.getUnitVerbose();
    }

    // #endregion

    // #region constructors

    /**
     * Default constructor for hibernate.
     */
    public ShoppingListItemIngredient() {
    }

    /**
     * @param paramIngredient
     */
    public ShoppingListItemIngredient(Ingredient paramIngredient) {
        this.isChecked = false;
        this.ingredient = paramIngredient;
    }

    // #endregion
}