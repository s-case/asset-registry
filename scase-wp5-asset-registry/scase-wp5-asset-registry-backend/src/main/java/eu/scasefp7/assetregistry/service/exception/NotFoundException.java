package eu.scasefp7.assetregistry.service.exception;

import eu.scasefp7.assetregistry.data.BaseEntity;
import eu.scasefp7.base.ScaseException;

/**
 * exception for not found entities.
 * @author Robert Magnus
 *
 */
public class NotFoundException extends ScaseException {

    private static final long serialVersionUID = 3162547251893625565L;

    private final Class<? extends BaseEntity> clazz;
    private final long id;


    /**
     * constructor.
     * @param clazz entity class
     * @param id primary key
     */
    public NotFoundException( Class<? extends BaseEntity> clazz, long id ) {
        super( clazz.getSimpleName() + " with ID " + id + " was not found.");
        this.clazz = clazz;
        this.id = id;
    }

    public Class<? extends BaseEntity> getClazz() {
        return this.clazz;
    }

    public long getId() {
        return this.id;
    }
}
