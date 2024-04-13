package org.acme.Ingredient;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IngredientResource implements PanacheRepository<Ingredient> {
}
