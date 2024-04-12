package org.acme.Recipe;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;

import org.acme.Amount.Amount;
import org.acme.Step.Step;

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
    //  * File containing the original recipe.
    //  */
    // @Column(nullable = true)
    // public File original;

    // /**
    //  * Image of the recipe.
    //  */
    // @Column(nullable = true)
    // public File image;

    /**
     * List of amounts used in the recipe.
     */
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference("recipe-amounts")
    public List<Amount> amounts = new ArrayList<>();

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
     * @param newServings New number of servings.
     */
    public void setServings(final Integer newServings) {
        final int minServings = 1;
        final int maxServings = 99;

        if (newServings < minServings || newServings > maxServings) {
            throw new IllegalArgumentException(String.format("Wert muss zwischen %d und %d liegen.", minServings, maxServings));
        }
        servings = newServings;
    }

    /**
     * Set preparation time of recipe.
     * Check for invalid values.
     * @param newPreptime New preparation time in minutes.
     */
    public void setPreptime(final Integer newPreptime) {
        final int minPreptime = 1;
        final int maxPreptime = 999;

        if (newPreptime == null) { // allow null values
            return;
        }
        if (newPreptime < minPreptime || newPreptime > maxPreptime) {
            throw new IllegalArgumentException(String.format("Wert muss zwischen %d und %d liegen.", minPreptime, maxPreptime));
        }
        preptime = newPreptime;
    }

    /**
     * Set url of origin of recipe.
     * Check for invalid values.
     * Convert to URL.
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
     * Replace amounts used in recipe.
     * @param newAmounts New list of amounts.
     */
    public void setAmounts(final List<Amount> newAmounts) {
        List<Amount> oldAmounts = new ArrayList<>(amounts);
        for (Amount amount : oldAmounts) {
            this.removeAmount(amount);
        }
        for (Amount amount : newAmounts) {
            addAmount(amount);
        }
    }

    /**
     * Add single amount to recipe.
     * @param newAmount New amount to add.
     */
    public void addAmount(final Amount newAmount) {
        amounts.add(newAmount);
        newAmount.setRecipe(this);
    }

    /**
     * Remove single amount from recipe.
     * @param amount Amount to remove.
     */
    public void removeAmount(final Amount amount) {
        amounts.remove(amount);
        amount.setRecipe(null);
    }

    /**
     * Replace steps of recipe.
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
     * @param newStep New step to add.
     */
    public void addStep(final Step newStep) {
        steps.add(newStep);
        newStep.recipe = this;
    }

    /**
     * Remove single step from recipe.
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
     * @param paramName Name of the recipe.
     * @param paramServings Number of servings the recipe is for.
     * @param paramPreptime Preparation time of the recipe in minutes.
     * @param paramOriginName Name of the origin of the recipe.
     * @param paramOriginUrl URL of the origin of the recipe.
     * @param paramAmounts List of amounts used in the recipe.
     * @param paramSteps List of steps of the recipe.
     */
    public Recipe(
            final String paramName,
            final Integer paramServings,
            final Integer paramPreptime,
            final String paramOriginName,
            final String paramOriginUrl,
            // final File paramOriginal,
            // final File paramImage,
            final List<Amount> paramAmounts,
            final List<Step> paramSteps) {
        this.name = paramName;
        this.setServings(paramServings);
        this.setPreptime(paramPreptime);
        this.originName = paramOriginName;
        this.setOriginUrl(paramOriginUrl);
        this.setAmounts(paramAmounts);
        this.setSteps(paramSteps);
    }
}
