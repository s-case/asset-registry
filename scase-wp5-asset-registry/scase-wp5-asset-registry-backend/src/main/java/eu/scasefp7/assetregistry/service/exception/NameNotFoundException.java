package eu.scasefp7.assetregistry.service.exception;

import eu.scasefp7.base.ScaseException;

/**
 * Exception to be thrown if an asset for a given name could not be found
 */
public class NameNotFoundException extends ScaseException{

    private static final long serialVersionUID = 1L;
    private final Class<?> clazz;

    private final String name;

    /**
     * constructor.
     *
     * @param clazz entity class
     * @param name  the name of the entity
     */
    public NameNotFoundException( Class<?> clazz, String name ) {
        super( clazz.getSimpleName() + " with name " + name + " was not found.");
        this.clazz = clazz;
        this.name = name;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public String getName() {
        return this.name;
    }
}
