package org.acme.Amount;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
// import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Column;

import org.acme.Ingredient.Ingredient;
import org.acme.Recipe.Recipe;

@Entity
// unique constraints prevented updating recipes
// @Table(uniqueConstraints = {
// @UniqueConstraint(columnNames = {"index", "recipe_id"}),
// @UniqueConstraint(columnNames = {"ingredient_id", "recipe_id"})
// })
public class Amount extends PanacheEntity {
    /**
     * Max length for index.
     */
    private static final int MAX_INDEX = 2;
    /**
     * Max length for amount.
     */
    private static final int MAX_AMOUNT = 3;

    /**
     * Index of the amount in the recipe.
     */
    @Column(nullable = false, length = MAX_INDEX)
    public Integer index;

    /**
     * Amount of the ingredient in the recipe.
     */
    @Column(nullable = false, length = MAX_AMOUNT)
    public Float amount;

    /**
     * Ingredient of the amount.
     */
    @ManyToOne
    @JoinColumn
    @JsonBackReference("amount-ingredient")
    public Ingredient ingredient;

    /**
     * Recipe the amount is used in.
     */
    @ManyToOne
    @JoinColumn
    @JsonBackReference("recipe-amounts")
    public Recipe recipe;

    /**
     * @return id of referenced ingredient.
     */
    public Long getIngredientId() {
        return ingredient.id;
    }

    /**
     * @return id of recipe using this amount.
     */
    public Long getRecipeId() {
        return recipe.id;
    }

    /**
     * Set index of the amount.
     * Validate new index.
     * @param newIndex new index to set.
     */
    public void setIndex(final Integer newIndex) {
        final int minIndex = 1;
        final int maxIndex = 99;

        if (newIndex < minIndex || newIndex > maxIndex) {
            throw new IllegalArgumentException(String.format("Wert muss zwischen %d und %d liegen.", minIndex, maxIndex));
        }
        index = newIndex;
    }

    /**
     * Set amount of the ingredient in the recipe.
     * Validate new amount.
     * @param newAmount new amount to set.
     */
    public void setAmount(final Float newAmount) {
        final int minAmount = 1;
        final int maxAmount = 999;

        if (newAmount < minAmount || newAmount > maxAmount) {
            throw new IllegalArgumentException(String.format("Wert muss zwischen %d und %d liegen.", minAmount, maxAmount));
        }
        amount = newAmount;
    }

    /**
     * Set ingredient of the amount.
     * @param newIngredient new ingredient to set.
     */
    public void setIngredient(final Ingredient newIngredient) {
        ingredient = newIngredient;
        ingredient.addAmount(this);
    }

    /**
     * Set id of the ingredient of the amount.
     * Check if ingredient exists.
     * @param ingredientId id of new ingredient to set.
     */
    public void setIngredientId(final Long ingredientId) {
        Ingredient newIngredient = Ingredient.findById(ingredientId);
        if (newIngredient == null) {
            throw new IllegalArgumentException("Ingredient with id " + ingredientId + " does not exist");
        }
        setIngredient(newIngredient);
    }

    /**
     * Set recipe using the amount.
     * @param newRecipe new recipe to set.
     */
    public void setRecipe(final Recipe newRecipe) {
        recipe = newRecipe;
    }

    /**
     * Default constructor for hibernate.
     */
    public Amount() {
    }

    /**
     * Constructor.
     * @param paramIndex index of the amount.
     * @param paramAmount amount of the ingredient in the recipe.
     * @param paramIngredientId id of the ingredient of the amount.
     */
    public Amount(
            final Integer paramIndex,
            final Float paramAmount,
            final Long paramIngredientId) {
        this.setIndex(paramIndex);
        this.setAmount(paramAmount);
        this.setIngredientId(paramIngredientId);
    }
}
