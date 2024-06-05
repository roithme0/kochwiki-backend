package org.acme.Recipe;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;

import org.acme.Ingredient.Ingredient;
import org.acme.ShoppingList.ShoppingList;
import org.acme.Step.Step;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Recipe extends PanacheEntity {
    /**
     * Maximum length of name attribute.
     */
    static final int MAX_LENGTH_NAME = 200;
    /**
     * Maximum length of servings attribute.
     */
    static final int MAX_LENGTH_SERVINGS = 2;
    /**
     * Maximum length of preptime attribute.
     */
    static final int MAX_LENGTH_PREPTIME = 3;
    /**
     * Maximum length of originName attribute.
     */
    static final int MAX_LENGTH_ORIGINNAME = 200;
    /**
     * Maximum length of originUrl attribute.
     */
    static final int MAX_LENGTH_ORIGINURL = 200;
    /**
     * Maximum length of kcal attribute.
     */
    private static final int MAX_LENGTH_KCAL = 4;
    /**
     * Maximum length of carbs attribute.
     */
    private static final int MAX_LENGTH_CARBS = 4;
    /**
     * Maximum length of protein attribute.
     */
    private static final int MAX_LENGTH_PROTEIN = 4;
    /**
     * Maximum length of fat attribute.
     */
    private static final int MAX_LENGTH_FAT = 4;

    /**
     * Name of the recipe.
     */
    @Column(unique = true, nullable = false, length = MAX_LENGTH_NAME)
    public String name;

    /**
     * Number of servings the recipe is for.
     */
    @Column(nullable = false, length = MAX_LENGTH_SERVINGS)
    public Integer servings;

    /**
     * Preparation time of the recipe in minutes.
     */
    @Column(nullable = true, length = MAX_LENGTH_PREPTIME)
    public Integer preptime;

    /**
     * Name of the origin of the recipe.
     */
    @Column(nullable = true, length = MAX_LENGTH_ORIGINNAME)
    public String originName;

    /**
     * URL of the origin of the recipe.
     */
    @Column(nullable = true, length = MAX_LENGTH_ORIGINURL)
    public URL originUrl;

    // /**
    // * File containing the original recipe.
    // */
    // @Column(nullable = true)
    // public File original;

    // /**
    // * Image of the recipe.
    // */
    // @Column(nullable = true)
    // public File image;

    /**
     * Nutritional value per serving
     */
    @Column(nullable = true, length = MAX_LENGTH_KCAL)
    public Integer kcal;

    /**
     * Nutritional value per serving
     */
    @Column(nullable = true, length = MAX_LENGTH_CARBS)
    public Float carbs;

    /**
     * Nutritional value per serving
     */
    @Column(nullable = true, length = MAX_LENGTH_PROTEIN)
    public Float protein;

    /**
     * Nutritional value per serving
     */
    @Column(nullable = true, length = MAX_LENGTH_FAT)
    public Float fat;

    /**
     * List of ingredients used in the recipe.
     */
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference("recipe-ingredients")
    public List<Ingredient> ingredients = new ArrayList<>();

    /**
     * List of steps of the recipe.
     */
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference("recipe-steps")
    public List<Step> steps = new ArrayList<>();

    /**
     * Set the number of servings of recipe.
     * Check for invalid values.
     * 
     * @param newServings New number of servings.
     */
    public void setServings(final Integer newServings) {
        final int minServings = 1;
        final int maxServings = 99;

        if (newServings < minServings || newServings > maxServings) {
            throw new IllegalArgumentException(
                    String.format("Wert muss zwischen %d und %d liegen.", minServings, maxServings));
        }
        servings = newServings;
    }

    /**
     * Set preparation time of recipe.
     * Check for invalid values.
     * 
     * @param newPreptime New preparation time in minutes.
     */
    public void setPreptime(final Integer newPreptime) {
        final int minPreptime = 1;
        final int maxPreptime = 999;

        if (newPreptime == null) { // allow null values
            return;
        }
        if (newPreptime < minPreptime || newPreptime > maxPreptime) {
            throw new IllegalArgumentException(
                    String.format("Wert muss zwischen %d und %d liegen.", minPreptime, maxPreptime));
        }
        preptime = newPreptime;
    }

    /**
     * Set url of origin of recipe.
     * Check for invalid values.
     * Convert to URL.
     * 
     * @param newOriginUrl New URL of origin.
     */
    public void setOriginUrl(final String newOriginUrl) {
        if (newOriginUrl == null || newOriginUrl == "") {
            originUrl = null;
            return;
        }

        try {
            originUrl = new URL(newOriginUrl);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL format for 'originUrl'", e);
        }
    }

    /**
     * Replace ingredients used in recipe.
     * 
     * @param newIngredients New list of ingredients.
     */
    public void setIngredients(final List<Ingredient> newIngredients) {
        List<Ingredient> oldIngredients = new ArrayList<>(ingredients);
        for (Ingredient ingredient : oldIngredients) {
            this.removeIngredient(ingredient);
        }
        for (Ingredient ingredient : newIngredients) {
            addIngredient(ingredient);
        }
    }

    /**
     * Add single ingredient to recipe.
     * 
     * @param newIngredient New ingredient to add.
     */
    public void addIngredient(final Ingredient newIngredient) {
        ingredients.add(newIngredient);
        newIngredient.setRecipe(this);
    }

    /**
     * Remove single ingredient from recipe.
     * 
     * @param ingredient Ingredient to remove.
     */
    public void removeIngredient(final Ingredient ingredient) {
        ingredients.remove(ingredient);
        ingredient.setRecipe(null);
    }

    /**
     * Replace steps of recipe.
     * 
     * @param newSteps New list of steps.
     */
    public void setSteps(final List<Step> newSteps) {
        List<Step> oldSteps = new ArrayList<>(steps);
        for (Step step : oldSteps) {
            this.removeStep(step);
        }
        for (Step step : newSteps) {
            this.addStep(step);
        }
    }

    /**
     * Add single step to recipe.
     * 
     * @param newStep New step to add.
     */
    public void addStep(final Step newStep) {
        steps.add(newStep);
        newStep.recipe = this;
    }

    /**
     * Remove single step from recipe.
     * 
     * @param step Step to remove.
     */
    public void removeStep(final Step step) {
        steps.remove(step);
        step.recipe = null;
    }

    /**
     * Default constructor for hibernate.
     */
    public Recipe() {
    }

    /**
     * Constructor for recipe.
     * 
     * @param paramName        Name of the recipe.
     * @param paramServings    Number of servings the recipe is for.
     * @param paramPreptime    Preparation time of the recipe in minutes.
     * @param paramOriginName  Name of the origin of the recipe.
     * @param paramOriginUrl   URL of the origin of the recipe.
     * @param paramIngredients List of ingredients used in the recipe.
     * @param paramSteps       List of steps of the recipe.
     */
    public Recipe(
            final String paramName,
            final Integer paramServings,
            final Integer paramPreptime,
            final String paramOriginName,
            final String paramOriginUrl,
            // final File paramOriginal,
            // final File paramImage,
            final List<Ingredient> paramIngredients,
            final List<Step> paramSteps) {
        this.name = paramName;
        this.setServings(paramServings);
        this.setPreptime(paramPreptime);
        this.originName = paramOriginName;
        this.setOriginUrl(paramOriginUrl);
        this.setIngredients(paramIngredients);
        this.setSteps(paramSteps);
    }
}
