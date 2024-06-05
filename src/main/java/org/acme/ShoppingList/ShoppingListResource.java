package org.acme.ShoppingList;

import org.acme.CustomUser.CustomUser;
import org.acme.CustomUser.CustomUserResource;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ShoppingListResource implements PanacheRepository<ShoppingList> {

    @Inject
    private CustomUserResource customUserResource;

    public ShoppingList findByCustomUserId(final Long customUserId) {
        CustomUser customUser = customUserResource.findById(customUserId);
        return customUser.shoppingList;
    }
}
