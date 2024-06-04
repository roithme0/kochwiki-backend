package org.acme.CustomUser;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class CustomUser extends PanacheEntity {

    /**
     * Maximum length of username attribute.
     */
    private static final int MAX_LENGTH_USERNAME = 50;

    /**
     * Username of the useraccount.
     */
    @Column(unique = true, nullable = false, length = MAX_LENGTH_USERNAME)
    public String username;

    /**
     * Default constructor for hibernate.
     */
    public CustomUser() {
    }

    /**
     * Constructor.
     * 
     * @param paramUsername username of the user.
     */
    public CustomUser(
            final String paramUsername) {
        this.username = paramUsername;
    }
}
