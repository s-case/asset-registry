package eu.scasefp7.assetregistry.index;

/**
 * Field names of the artefact index created in the AR in addition to the fields defined in the {@link eu.scasefp7.assetregistry.index.BaseIndex BaseIndex}
 */
public class ArtefactIndex extends BaseIndex {

    public static final String INDEX_NAME = "ARTEFACT_INDEX";

    public static final String URI_FIELD = "URI";
    public static final String GROUPID_FIELD = "GROUPID";
    public static final String DEPENDENCIES_FIELD = "DEPENDENCIES";
    public static final String ARTEFACT_TYPE_FIELD = "ARTEFACT_TYPE";
    public static final String DESCRIPTION_FIELD = "DESCRIPTION";
    public static final String TAGS_FIELD = "TAGS";
    public static final String METADATA_FIELD = "METADATA";
}
