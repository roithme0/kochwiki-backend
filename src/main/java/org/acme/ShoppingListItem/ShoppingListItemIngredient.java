package org.acme.ShoppingListItem;

import java.util.HashSet;
import java.util.Set;

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
    @JoinColumn(unique = true, name = "ingredient_id", nullable = false)
    @JsonBackReference("ingredient-shoppingListItemIngredients")
    public Ingredient ingredient;

    @Column(nullable = false)
    public Float amount;

    @ManyToMany(mappedBy = "shoppingListItemIngredients")
    @JsonBackReference
    public Set<ShoppingList> shoppingLists = new HashSet<>();

    // #endregion

    // #region getters

    public Boolean getIsChecked() {
        return isChecked;
    }

    public Boolean getIsPinned() {
        return isPinned;
    }

    public String getName() {
        return ingredient.getName();
    }

    public String getBrand() {
        return ingredient.getBrand();
    }

    public Float getAmount() {
        return amount;
    }

    public UnitEnum getUnit() {
        return ingredient.getUnit();
    }

    public String getUnitVerbose() {
        return ingredient.getUnitVerbose();
    }

    public String getRecipeName() {
        return ingredient.recipe.name;
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
    public ShoppingListItemIngredient(Ingredient paramIngredient, Float paramAmount) {
        this.isChecked = false;
        this.isPinned = false;
        this.ingredient = paramIngredient;
        this.amount = paramAmount;
    }

    // #endregion
}
