package eu.fp7.scase.assetregistry.service.db;

import eu.fp7.scase.assetregistry.data.BaseEntity;
import eu.fp7.scase.assetregistry.service.exception.NotFoundException;
import eu.fp7.scase.base.ScaseException;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Date;
import java.util.List;


abstract class BaseCrudDbServiceImpl<E extends BaseEntity> implements BaseCrudDbService<E> {

    public static final int MAX_RESULTS = 5000;

    abstract Class<E> getEntityClass();

    @Override
    public E create(final E entity) {
        if (entity.getId() == null) {
            entity.setCreatedAt(new Date());
            em().persist(entity);
            return entity;
        } else {
            throw new IllegalStateException("id must not be set");
        }
    }

    @Override
    public void delete(final E entity) {
        if (countUsed(entity) > 0) {
            throw new ScaseException("Could not delete " +
                    getEntityClass().getSimpleName());
        }
        em().remove(entity);
    }

    @Override
    public void delete(final long entityId) {
        final E e = find(entityId);
        if (e == null) {
            throw new NotFoundException(getEntityClass(), entityId);
        }
        delete(e);
    }

    @Override
    public E find(final long id) {
        return em().find(getEntityClass(), id);
    }

    @Override
    public List<E> findAll() {
        final CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(getEntityClass());
        cq.from(getEntityClass());
        return em().createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }

    protected abstract EntityManager em();

    // checks if this entity is in use
    @Override
    public Long countUsed(final E e) {
        return 0L;
    }

    @Override
    public E createOrUpdate(E e) {
        if (e.getId() == null) {
            return create(e);
        } else {
            return update(e);
        }
    }

    @Override
    public void validateVersion(E loaded, E external) {
        if (loaded.getVersion() > external.getVersion()) {
            throw new OptimisticLockException(loaded.getClass().getSimpleName());
        }
    }
}
