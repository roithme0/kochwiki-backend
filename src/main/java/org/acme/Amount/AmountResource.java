package org.acme.Amount;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AmountResource implements PanacheRepository<Amount> {
}
