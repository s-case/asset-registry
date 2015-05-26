package eu.scasefp7.assetregistry.index;

/**
 * Field names of the project index created in the AR in addition to the fields defined in the {@link eu.scasefp7.assetregistry.index.BaseIndex BaseIndex}
 */
public class ProjectIndex extends BaseIndex {

    public static final String INDEX_NAME = "PROJECT_INDEX";

    public static final String PRIVACY_LEVEL_FIELD = "PRIVACY_LEVEL";
    public static final String DOMAIN_FIELD = "DOMAIN";
    public static final String SUBDOMAIN_FIELD = "SUBDOMAIN";
    public static final String ARTEFACTS_FIELD = "ARTEFACTS";
}
