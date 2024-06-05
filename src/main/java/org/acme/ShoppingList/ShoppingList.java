package org.acme.ShoppingList;

import java.util.ArrayList;
import java.util.List;

import org.acme.CustomUser.CustomUser;
import org.acme.ShoppingListItem.IShoppingListItem;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

public class ShoppingList {
    /**
     * List of shoppingListItems.
     */
    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference("shoppingList-shoppingListItems")
    public List<IShoppingListItem> shoppingListItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customUser_id", referencedColumnName = "id")
    public CustomUser customUser;
}
