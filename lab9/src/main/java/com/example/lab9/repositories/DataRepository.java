package com.example.lab9.repositories;
import java.io.Serializable;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.lab9.DatabaseUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public abstract class DataRepository<T, ID extends Serializable> {
//    private static final Logger logger = Logger.getLogger(DataRepository.class.getName());
    protected abstract Class<T> getEntityClass();

        private boolean runWithRollback(T entity, Consumer<T> consumer) {
            long startTime = System.currentTimeMillis();

            try {
                beginTransaction();
                consumer.accept(entity);
                commit();
                return true;
            } catch (Exception e) {
//                logger.log(Level.SEVERE, "Exception during transaction", e);
                e.printStackTrace();
                rollback();
                return false;
            }
            finally {
                long endTime = System.currentTimeMillis();
//                logger.log(Level.INFO, "Execution time: " + (endTime - startTime) + " ms");
            }
        }

        public T findById(ID id) {
            return getEntityManager().find(getEntityClass(), id);
        }

        public boolean delete(T entity) {
            return runWithRollback(entity, (e) -> getEntityManager().remove(e));
        }

        public boolean save(T entity) {
            return runWithRollback(entity, (e) -> getEntityManager().persist(e));
        }

        private final EntityManager entityManager = DatabaseUtils.getInstance().getEntityManager();
        private EntityTransaction entityTransaction;

        public EntityManager getEntityManager() {
            return this.entityManager;
        }

        private void beginTransaction() {
            entityTransaction = getEntityManager().getTransaction();
            entityTransaction.begin();
        }

        private void commit() {
            entityTransaction.commit();
        }

        private void rollback() {
            entityTransaction.rollback();
        }


}
