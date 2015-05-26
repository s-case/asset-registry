package eu.scasefp7.assetregistry.service.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.data.PrivacyLevel;
import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.index.IndexType;
import eu.scasefp7.assetregistry.index.ProjectIndex;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Service class for Project related ElasticSearch operations
 */
@Stateless
@Local(ProjectEsService.class)
public class ProjectEsServiceImpl extends AbstractEsServiceImpl<Project> implements ProjectEsService {

    private final static Logger LOG = LoggerFactory.getLogger(ProjectEsServiceImpl.class);

    @Inject
    private ObjectMapper mapper;

    public List<Project> find(final String query) {
        SearchResponse response = getSearchResponse(ProjectIndex.INDEX_NAME, IndexType
                .TYPE_PROJECT, query);

        final List<Project> result = new ArrayList<Project>();
        for (SearchHit hit : response.getHits().hits()) {
            try {
                final Project project = mapper.readValue(hit.sourceAsString(), Project.class);
                result.add(project);
                LOG.info("found {} because of {}", project, hit.getExplanation());
            } catch (IOException e) {
                LOG.error("reading object failed", e);
            }
        }
        return result;
    }

    @Override
    public IndexResponse index(final Project project) throws IOException {
        // String json = mapper.writeValueAsString(project);
        // IndexResponse response = connectorService.getClient().prepareIndex(ProjectIndex.INDEX_NAME,
        // ElasticSearchConnectorService.TYPE_PROJECT, project.getId().toString()).setSource(json).execute()
        // .actionGet();

        IndexResponse response = connectorService.getClient().prepareIndex(ProjectIndex.INDEX_NAME,
                IndexType.TYPE_PROJECT, project.getId().toString()).setSource(builder(project)).execute()
                .actionGet();

        return response;
    }

    @Override
    public UpdateResponse update(final Project project) throws IOException {

        UpdateResponse response = connectorService.getClient().prepareUpdate(ProjectIndex.INDEX_NAME,
                IndexType.TYPE_PROJECT, project.getId().toString()).setDoc(builder(project)).get();
        return response;
    }

    @Override
    public UpdateResponse updatePrivacyLevel(final long id, final PrivacyLevel privacyLevel) throws IOException {

        UpdateResponse response = connectorService.getClient().prepareUpdate(ProjectIndex.INDEX_NAME,
                IndexType.TYPE_PROJECT, new Long(id).toString()).setDoc(jsonBuilder().startObject
                ().field(ProjectIndex.PRIVACY_LEVEL_FIELD, privacyLevel).endObject()).get();

        return response;
    }

    private XContentBuilder builder(Project project) throws IOException {

        Long[] artefactIds = new Long[project.getArtefacts().size()];
            List<Artefact> artefacts = project.getArtefacts();
            for (int i = 0; i < artefacts.size(); i++) {
                artefactIds[i] = artefacts.get(i).getId();
            }

        XContentBuilder builder = jsonBuilder().startObject()
                .field(ProjectIndex.NAME_FIELD, project.getName())
                .field(ProjectIndex.PRIVACY_LEVEL_FIELD, project.getPrivacyLevel())
                .field(ProjectIndex.DOMAIN_FIELD, project.getDomain().getName())
                .field(ProjectIndex.SUBDOMAIN_FIELD, project.getSubDomain().getName())
                .field(ProjectIndex.CREATED_BY_FIELD, project.getCreatedBy())
                .field(ProjectIndex.UPDATED_BY_FIELD, project.getUpdatedBy())
                .field(ProjectIndex.CREATED_AT_FIELD, project.getCreatedAt())
                .field(ProjectIndex.UPDATED_AT_FIELD, project.getUpdatedAt())
                .field(ProjectIndex.VERSION_FIELD, project.getVersion())
                .array(ProjectIndex.ARTEFACTS_FIELD, artefactIds).endObject();

        return builder;
    }

    /**
    private Map<String, Object> generateObjectMap(Project project) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put(ProjectIndex.NAME_FIELD, project.getName());
        objectMap.put(ProjectIndex.PRIVACY_LEVEL_FIELD, project.getPrivacyLevel());
        objectMap.put(ProjectIndex.DOMAIN_FIELD, project.getDomain().getName());
        objectMap.put(ProjectIndex.SUBDOMAIN_FIELD, project.getSubDomain().getName());
        objectMap.put(ProjectIndex.CREATED_BY_FIELD, project.getCreatedBy());
        objectMap.put(ProjectIndex.UPDATED_BY_FIELD, project.getUpdatedBy());
        objectMap.put(ProjectIndex.CREATED_AT_FIELD, project.getCreatedAt());
        objectMap.put(ProjectIndex.UPDATED_AT_FIELD, project.getUpdatedAt());
        objectMap.put(ProjectIndex.ARTEFACTS_FIELD, new Long[0]);

        if (null != project.getArtefacts()) {
            List<Artefact> artefacts = project.getArtefacts();
            Long[] arr = new Long[artefacts.size()];
            for (int i = 0; i < artefacts.size(); i++) {
                arr[i] = artefacts.get(i).getId();
            }
            objectMap.put(ProjectIndex.ARTEFACTS_FIELD, arr);
        }
        return objectMap;
    }**/
}
