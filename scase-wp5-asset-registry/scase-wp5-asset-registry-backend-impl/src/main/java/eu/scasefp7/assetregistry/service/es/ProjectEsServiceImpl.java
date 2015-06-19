package eu.scasefp7.assetregistry.service.es;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.data.PrivacyLevel;
import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.dto.JsonProject;
import eu.scasefp7.assetregistry.dto.ProjectDTO;
import eu.scasefp7.assetregistry.index.BaseIndex;
import eu.scasefp7.assetregistry.index.IndexType;
import eu.scasefp7.assetregistry.index.ProjectIndex;
import eu.scasefp7.assetregistry.service.ProjectService;
import eu.scasefp7.assetregistry.service.db.ProjectDbService;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.FilterBuilders.queryFilter;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

/**
 * Service class for Project related ElasticSearch operations
 */
@Stateless
@Local(ProjectEsService.class)
public class ProjectEsServiceImpl extends AbstractEsServiceImpl<Project> implements ProjectEsService {

    private final static Logger LOG = LoggerFactory.getLogger(ProjectEsServiceImpl.class);

    @EJB
    private ProjectDbService dbService;

    @EJB
    private ProjectService projectService;

    @Override
    public List<ProjectDTO> find(final String query) {
        SearchResponse response = getSearchResponse(ProjectIndex.INDEX_NAME, IndexType
                .TYPE_PROJECT, query);

        return getProjectDTOs(response);
    }

    @Override
    public List<ProjectDTO> find(String query, String domain, String subdomain) {

        QueryBuilder qb;

        if (null != query) {

            qb = QueryBuilders.matchQuery("_all", query);

            if (query.contains("+") || query.contains(" ")) {
                query = query.replace("+", " ");
                qb = multiMatchQuery(query, "_all");
            }

            if (null != domain || null != subdomain) {

                QueryBuilder matchClause = qb;

                BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();

                if (null != domain) {
                    boolFilter.must(queryFilter(matchQuery(BaseIndex
                            .DOMAIN_FIELD, domain)));
                }
                if (null != subdomain) {
                    boolFilter.must(queryFilter(matchQuery(BaseIndex
                            .SUBDOMAIN_FIELD, subdomain)));
                }
                qb = QueryBuilders.filteredQuery(matchClause,
                        boolFilter);
            }
        } else {
            qb = QueryBuilders.boolQuery();
            if (null != domain) {
                ((BoolQueryBuilder) qb).must(QueryBuilders.matchQuery(BaseIndex
                        .DOMAIN_FIELD, domain));
            }
            if (null != subdomain) {
                ((BoolQueryBuilder) qb).must(QueryBuilders.matchQuery(BaseIndex.SUBDOMAIN_FIELD, subdomain));
            }
        }

        SearchResponse response = getSearchResponse(ProjectIndex.INDEX_NAME, IndexType
                .TYPE_PROJECT, qb);

        return getProjectDTOs(response);
    }

    @Override
    public IndexResponse index(final Project project) throws IOException {

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
                IndexType.TYPE_PROJECT, Long.toString(id)).setDoc(jsonBuilder().startObject
                ().field(BaseIndex.PRIVACY_LEVEL_FIELD, privacyLevel).endObject()).get();

        return response;
    }

    private List<ProjectDTO> getProjectDTOs(SearchResponse response) {
        final List<ProjectDTO> result = new ArrayList<ProjectDTO>();
        for (SearchHit hit : response.getHits().hits()) {

            String documentId = hit.getId();
            LOG.info("found {} because of {}", documentId, hit.getExplanation());

            try {
                Long projectId = Long.valueOf(documentId);
                final Project project = dbService.find(projectId);
                if (null != project) {
                    final JsonProject jsonProject = projectService.convertEntityToJson(project);
                    ProjectDTO dto = new ProjectDTO();
                    dto.setProject(jsonProject);
                    dto.setScore(hit.getScore());
                    result.add(dto);
                } else {
                    LOG.warn("Project with id " + projectId + "could not be loaded");
                }

            } catch (NumberFormatException ex) {
                LOG.error("Elastic document ID " + documentId + " could not be converted into a number. ", ex);
            }
        }
        return result;
    }

    private XContentBuilder builder(Project project) throws IOException {
        Long[] artefactIds = null;

        if (null != project.getArtefacts()) {
            artefactIds = new Long[project.getArtefacts().size()];
            List<Artefact> artefacts = project.getArtefacts();
            for (int i = 0; i < artefacts.size(); i++) {
                artefactIds[i] = artefacts.get(i).getId();
            }
        }

        XContentBuilder builder = jsonBuilder().startObject()
                .field(BaseIndex.NAME_FIELD, project.getName())
                .field(BaseIndex.PRIVACY_LEVEL_FIELD, project.getPrivacyLevel())
                .field(BaseIndex.DOMAIN_FIELD, (null != project.getDomain() ? project.getDomain().getName() : null))
                .field(BaseIndex.SUBDOMAIN_FIELD, (null != project.getSubDomain() ? project.getSubDomain().getName
                        () : null))
                .field(BaseIndex.CREATED_BY_FIELD, project.getCreatedBy())
                .field(BaseIndex.UPDATED_BY_FIELD, project.getUpdatedBy())
                .field(BaseIndex.CREATED_AT_FIELD, project.getCreatedAt())
                .field(BaseIndex.UPDATED_AT_FIELD, project.getUpdatedAt())
                .field(BaseIndex.VERSION_FIELD, project.getVersion())
                .array(ProjectIndex.ARTEFACTS_FIELD, artefactIds).endObject();

        return builder;
    }
}