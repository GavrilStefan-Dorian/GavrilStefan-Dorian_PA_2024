package org.example.Lab11;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Database {

    // JPA
    static final String PERSISTENCE_UNIT_NAME = "Lab11PU";
    private static Database instance;
    private static EntityManagerFactory entityManagerFactory;
    private static final String URL = "jdbc:postgresql://localhost:5432/Books_Lab8Java";
    // JDBC
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Lab11PU");
        return entityManagerFactory.createEntityManager();
    }
}

