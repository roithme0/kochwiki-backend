package org.acme.ShoppingList;

import java.util.ArrayList;
import java.util.List;

import org.acme.CustomUser.CustomUser;
import org.acme.ShoppingListItem.ShoppingListItemIngredient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class ShoppingList extends PanacheEntity {
    // #region fields

    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference("shoppingList-shoppingListItemIngredients")
    public List<ShoppingListItemIngredient> shoppingListItemIngredients = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "customUser_id", referencedColumnName = "id")
    public CustomUser customUser;

    // #endregion

    // #region constructors

    /**
     * Default constructor for hibernate.
     */
    public ShoppingList() {
    }

    /**
     * @param paramCustomUser customUser of the shoppingList.
     */
    public ShoppingList(
            final CustomUser paramCustomUser) {
        this.customUser = paramCustomUser;
    }

    // #endregion
}
