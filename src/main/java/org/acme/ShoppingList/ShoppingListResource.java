package org.acme.ShoppingList;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class ShoppingListResource implements PanacheRepository<ShoppingList> {

    @Inject
    EntityManager entityManager;

    public ShoppingList findByCustomUserId(final Long customUserId) {
        return entityManager
                .createQuery(
                        "SELECT shoppingList FROM ShoppingList shoppingList WHERE shoppingList.customUserId = :customUserId",
                        ShoppingList.class)
                .setParameter("customUserId", customUserId)
                .getSingleResult();
    }
}
