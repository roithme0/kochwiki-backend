package org.acme.ShoppingList;

import java.util.ArrayList;
import java.util.List;

import org.acme.ShoppingListItem.IShoppingListItem;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

public class ShoppingList {
    /**
     * List of shoppingListItems.
     */
    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference("shoppingList-steps")
    public List<IShoppingListItem> shoppingListItems = new ArrayList<>();
}
