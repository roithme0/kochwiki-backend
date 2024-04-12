package org.acme.Step;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StepResource implements PanacheRepository<Step> {
}
