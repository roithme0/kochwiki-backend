package org.acme.ShoppingList;

import java.util.HashSet;
import java.util.Set;

import org.acme.ShoppingListItem.ShoppingListItemIngredient;

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
    public Set<ShoppingListItemIngredient> shoppingListItemIngredients = new HashSet<>();

    // #endregion

    // #region setters

    public void addIngredient(final ShoppingListItemIngredient newShoppingListItemIngredient) {
        shoppingListItemIngredients.add(newShoppingListItemIngredient);
        newShoppingListItemIngredient.shoppingLists.add(this);
    }

    public void removeIngredient(final ShoppingListItemIngredient shoppingListItemIngredient) {
        shoppingListItemIngredients.remove(shoppingListItemIngredient);
        shoppingListItemIngredient.shoppingLists.remove(this);
    }

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
