package org.acme.Step;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
// import jakarta.persistence.UniqueConstraint;

import org.acme.Recipe.Recipe;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
// unique constraints prevented updating recipes
// @Table(uniqueConstraints = {
// @UniqueConstraint(columnNames = {"index", "recipe_id"})
// })
public class Step extends PanacheEntity {
    /**
     * Maximum length of the index.
     */
    private static final int MAX_LENGTH_INDEX = 2;
    /**
     * Maximum length of the description.
     */
    private static final int MAX_LENGTH_DESCRIPTION = 200;

    /**
     * Index of the step in the recipe.
     */
    @Column(nullable = false, length = MAX_LENGTH_INDEX)
    public Integer index;

    /**
     * Description of the step.
     */
    @Column(nullable = false, length = MAX_LENGTH_DESCRIPTION)
    public String description;

    /**
     * Recipe the step is used in.
     */
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonBackReference("recipe-steps")
    public Recipe recipe;

    /**
     * @return id of the recipe the step is used in
     */
    public Long getRecipeId() {
        return recipe.id;
    }

    /**
     * Set index of the step.
     * @param newIndex new index
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
     * Default constructor for hibernate.
     */
    public Step() {
    }

    /**
     * Constructor for creating a new step.
     * @param paramIndex index of the step
     * @param paramDescription description of the step
     */
    public Step(
            final Integer paramIndex,
            final String paramDescription) {
        this.setIndex(paramIndex);
        this.description = paramDescription;
    }
}
