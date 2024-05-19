package org.acme.Ingredient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
// import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Column;

import org.acme.Foodstuff.Foodstuff;
import org.acme.Recipe.Recipe;

@Entity
// unique constraints prevented updating recipes
// @Table(uniqueConstraints = {
// @UniqueConstraint(columnNames = {"index", "recipe_id"}),
// @UniqueConstraint(columnNames = {"foodstuff_id", "recipe_id"})
// })
public class Ingredient extends PanacheEntity {
    /**
     * Max length for index.
     */
    private static final int MAX_INDEX = 2;
    /**
     * Max length for ingredient.
     */
    private static final int MAX_AMOUNT = 4;

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
     * foodstuff of the ingredient.
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

    /**
     * Default constructor for hibernate.
     */
    public Ingredient() {
    }

    /**
     * Constructor.
     * 
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
}
