package org.acme.ShoppingList;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShoppingListResource implements PanacheRepository<ShoppingList> {
}
