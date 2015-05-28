package eu.scasefp7.assetregistry.service.exception;

import eu.scasefp7.assetregistry.data.BaseEntity;
import eu.scasefp7.base.ScaseException;

/**
 * Exception to be thrown if an asset could not be stored
 */
public class NotCreatedException extends ScaseException {

    private static final long serialVersionUID = 3162547251893625565L;

    private final Class<? extends BaseEntity> clazz;
    private final long id;


    /**
     * constructor.
     *
     * @param clazz entity class
     * @param id    primary key
     */
    public NotCreatedException(Class<? extends BaseEntity> clazz, long id, Throwable thrown) {
        super(clazz.getSimpleName() + " with ID " + id + " could not be stored inside of the Asset Registry.", thrown);
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
