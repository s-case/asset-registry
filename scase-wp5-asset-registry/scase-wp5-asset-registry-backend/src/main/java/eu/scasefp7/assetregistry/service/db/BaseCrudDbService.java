package eu.scasefp7.assetregistry.service.db;

import eu.fp7.scase.assetregistry.data.BaseEntity;

import java.util.List;

/**
 * Created by missler on 09/04/15.
 */
public interface BaseCrudDbService<E extends BaseEntity> {

    E create(E entity);

    E update(E entity);

    void delete(E entity);

    void delete(long entityId);

    E find(long id);

    List<E> findAll();

    Long countUsed(E e);

    E createOrUpdate(E e);

    void validateVersion(E loaded, E external);
}
