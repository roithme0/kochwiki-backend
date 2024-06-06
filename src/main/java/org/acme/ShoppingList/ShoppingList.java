package org.acme.ShoppingList;

import java.util.ArrayList;
import java.util.List;

import org.acme.Ingredient.Ingredient;
import org.acme.ShoppingListItem.ShoppingListItemIngredient;
import org.acme.Step.Step;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class ShoppingList extends PanacheEntity {
    // #region fields

    @Column(unique = true, nullable = false)
    public Long customUserId;

    @ManyToMany
    // @JsonManagedReference("shoppingList-shoppingListItemIngredients")
    public List<ShoppingListItemIngredient> shoppingListItemIngredients = new ArrayList<>();

    // #endregion

    // #region constructors

    /**
     * Default constructor for hibernate.
     */
    public ShoppingList() {
    }

    public ShoppingList(Long paramCustomUserId) {
        customUserId = paramCustomUserId;
    }

    // #endregion
}
