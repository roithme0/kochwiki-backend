package org.acme.Ingredient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
// import jakarta.persistence.Table;
// import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Column;

import java.util.ArrayList;
import java.util.List;

import org.acme.Foodstuff.Foodstuff;
import org.acme.FoodstuffMetaData.UnitEnum;
import org.acme.Recipe.Recipe;
import org.acme.ShoppingListItem.ShoppingListItemIngredient;

@Entity
// unique constraints prevented updating recipes
// @Table(uniqueConstraints = {
// @UniqueConstraint(columnNames = {"index", "recipe_id"}),
// @UniqueConstraint(columnNames = {"foodstuff_id", "recipe_id"})
// })
public class Ingredient extends PanacheEntity {
    // #region consts

    /**
     * Max length for index.
     */
    private static final int MAX_INDEX = 2;
    /**
     * Max length for ingredient.
     */
    private static final int MAX_AMOUNT = 4;

    // #endregion

    // #region fields

    /**
     * Index of the ingredient in the recipe.
     */
    @Column(nullable = false, length = MAX_INDEX)
    public Integer index;

    /**
     * Amount of the foodstuff in the recipe.
     */
    @Column(nullable = false, length = MAX_AMOUNT)
    public Float amount;

    /**
     * Foodstuff of the ingredient.
     */
    @ManyToOne
    @JoinColumn
    @JsonBackReference("ingredient-foodstuff")
    public Foodstuff foodstuff;

    /**
     * Recipe the ingredient is used in.
     */
    @ManyToOne
    @JoinColumn
    @JsonBackReference("recipe-ingredients")
    public Recipe recipe;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference("ingredient-shoppingListItemIngredients")
    public List<ShoppingListItemIngredient> shoppingListItemIngredients = new ArrayList<>();

    // #endregion

    // #region getters

    /**
     * @return id of referenced foodstuff.
     */
    public Long getFoodstuffId() {
        return foodstuff.id;
    }

    /**
     * @return id of recipe using this ingredient.
     */
    public Long getRecipeId() {
        return recipe.id;
    }

    public String getName() {
        return foodstuff.name;
    }

    public String getBrand() {
        return foodstuff.brand;
    }

    public UnitEnum getUnit() {
        return foodstuff.unit;
    }

    public String getUnitVerbose() {
        return foodstuff.getUnitVerbose();
    }

    public Integer getKcal() {
        return foodstuff.kcal;
    }

    public Float getCarbs() {
        return foodstuff.carbs;
    }

    public Float getProtein() {
        return foodstuff.protein;
    }

    public Float getFat() {
        return foodstuff.fat;
    }

    // #endregion

    // #region setters

    /**
     * Set index of the ingredient.
     * Validate new index.
     * 
     * @param newIndex new index to set.
     */
    public void setIndex(final Integer newIndex) {
        final int minIndex = 0;
        final int maxIndex = 100;

        if (newIndex <= minIndex || newIndex >= maxIndex) {
            throw new IllegalArgumentException(
                    String.format("Wert muss zwischen %d und %d liegen.", minIndex, maxIndex));
        }
        index = newIndex;
    }

    /**
     * Set amount of the foodstuff in the recipe.
     * Validate new amount.
     * 
     * @param newAmount new amount to set.
     */
    public void setAmount(final Float newAmount) {
        final int minAmount = 0;
        final int maxAmount = 10000;

        if (newAmount <= minAmount || newAmount >= maxAmount) {
            throw new IllegalArgumentException(
                    String.format("Wert muss zwischen %d und %d liegen.", minAmount, maxAmount));
        }
        amount = newAmount;
    }

    /**
     * Set foodstuff of the ingredient.
     * 
     * @param newFoodstuff new foodstuff to set.
     */
    public void setFoodstuff(final Foodstuff newFoodstuff) {
        foodstuff = newFoodstuff;
        foodstuff.addIngredient(this);
    }

    /**
     * Set id of the foodstuff of the ingredient.
     * Check if foodstuff exists.
     * 
     * @param foodstuffId id of new foodstuff to set.
     */
    public void setFoodstuffId(final Long foodstuffId) {
        Foodstuff newFoodstuff = Foodstuff.findById(foodstuffId);
        if (newFoodstuff == null) {
            throw new IllegalArgumentException("Foodstuff with id " + foodstuffId + " does not exist");
        }
        setFoodstuff(newFoodstuff);
    }

    /**
     * Set recipe using the ingredient.
     * 
     * @param newRecipe new recipe to set.
     */
    public void setRecipe(final Recipe newRecipe) {
        recipe = newRecipe;
    }

    // #endregion

    // #region constructors

    /**
     * Default constructor for hibernate.
     */
    public Ingredient() {
    }

    /**
     * @param paramIndex       index of the ingredient.
     * @param paramAmount      amount of the foodstuff in the recipe.
     * @param paramFoodstuffId id of the foodstuff of the ingredient.
     */
    public Ingredient(
            final Integer paramIndex,
            final Float paramAmount,
            final Long paramFoodstuffId) {
        this.setIndex(paramIndex);
        this.setAmount(paramAmount);
        this.setFoodstuffId(paramFoodstuffId);
    }

    // #endregion
}
