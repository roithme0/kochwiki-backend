package org.acme.ShoppingListItem;

import java.util.List;

import org.acme.FoodstuffMetaData.UnitEnum;
import org.acme.Ingredient.Ingredient;
import org.acme.ShoppingList.ShoppingList;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class ShoppingListItemIngredient extends PanacheEntity implements IShoppingListItem {
    // #region fields

    @Column(nullable = false)
    public Boolean isChecked;

    @Column(nullable = false)
    public Boolean isPinned;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    @JsonBackReference("ingredient-shoppingListItemIngredients")
    public Ingredient ingredient;

    @ManyToMany(mappedBy = "shoppingListItemIngredients")
    @JsonBackReference("shoppingLists-shoppingListItemIngredients")
    public List<ShoppingList> shoppingLists;

    // #endregion

    // #region getters

    public Boolean getIsChecked() {
        return this.isChecked;
    }

    public Boolean getIsPinned() {
        return this.isPinned;
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
        this.isPinned = false;
        this.ingredient = paramIngredient;
    }

    // #endregion
}
