package org.acme.ShoppingList;

import java.util.ArrayList;
import java.util.List;

import org.acme.ShoppingListItem.ShoppingListItemIngredient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class ShoppingList extends PanacheEntity {
    // #region fields

    @Column(unique = true, nullable = false)
    public Long customUserId;

    @ManyToMany
    @JoinTable(name = "shoppingList_shoppingListItemIngredient")
    @JsonManagedReference("shoppingLists-shoppingListItemIngredients")
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
