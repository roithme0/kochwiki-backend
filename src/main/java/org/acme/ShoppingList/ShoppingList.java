package org.acme.ShoppingList;

import java.util.HashSet;
import java.util.Set;

import org.acme.ShoppingListItem.ShoppingListItemIngredient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ShoppingList extends PanacheEntity {
    // #region fields

    @Column(unique = true, nullable = false)
    public Long customUserId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shoppingList_shoppingListItemIngredient")
    public Set<ShoppingListItemIngredient> shoppingListItemIngredients = new HashSet<>();

    // #endregion

    // #region methods

    public void addIngredient(final ShoppingListItemIngredient newShoppingListItemIngredient) {
        shoppingListItemIngredients.add(newShoppingListItemIngredient);
        newShoppingListItemIngredient.addShoppingList(this);
    }

    public void removeIngredient(final ShoppingListItemIngredient shoppingListItemIngredient) {
        shoppingListItemIngredients.remove(shoppingListItemIngredient);
        shoppingListItemIngredient.removeShoppingList(this);
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
