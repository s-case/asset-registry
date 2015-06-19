package eu.scasefp7.assetregistry.index;

/**
 * Field names of the artefact index created in the AR in addition to the fields
 * defined in the {@link eu.scasefp7.assetregistry.index.BaseIndex BaseIndex}
 */
public class ArtefactIndex extends BaseIndex {

    public static final String INDEX_NAME = "artefact_index";

    public static final String PROJECT_NAME = "projectname";
    public static final String URI_FIELD = "uri";
    public static final String GROUPID_FIELD = "groupid";
    public static final String DEPENDENCIES_FIELD = "dependencies";
    public static final String ARTEFACT_TYPE_FIELD = "artefact_type";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String TAGS_FIELD = "tags";
    public static final String METADATA_FIELD = "metadata";
}
