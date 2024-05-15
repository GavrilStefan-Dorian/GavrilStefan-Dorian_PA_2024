package com.example.lab9;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseUtils {

    String PERSISTENCE_UNIT_NAME = "Lab9PU";
    private static DatabaseUtils instance;
    private final EntityManagerFactory entityManagerFactory;

    private DatabaseUtils() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static DatabaseUtils getInstance() {
        if (instance == null) {
            instance = new DatabaseUtils();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}

