 package org.example.Lab11.repositories;

import org.example.Lab11.Database;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractRepositoryJPA<T, ID extends Serializable> implements Repository<T, ID> {
    protected abstract String getFindByIdQuery();
    protected abstract String getFindByNameQuery();
    protected abstract Class<T> getEntityClass();
    private final EntityManager entityManager = Database.getInstance().getEntityManager();
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

    @Override
    public void create(T entity) {
        try {
            beginTransaction();
            entityManager.persist(entity);
            commit();
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
        }
    }
    @Override
    public void delete(ID id){
        try{
            beginTransaction();
            entityManager.remove(findById(id));
            commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            rollback();
        }
    }

    @Override
    public T findById(ID id) {

        List<T> results = entityManager.createNamedQuery(getFindByIdQuery(), getEntityClass())
                .setParameter("myId", id)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public T findByName(String name) {
        List<T> results = entityManager.createNamedQuery(getFindByNameQuery(), getEntityClass())
                .setParameter("myName", name)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("FROM " + getEntityClass().getName(), getEntityClass()).getResultList();
    }
}
