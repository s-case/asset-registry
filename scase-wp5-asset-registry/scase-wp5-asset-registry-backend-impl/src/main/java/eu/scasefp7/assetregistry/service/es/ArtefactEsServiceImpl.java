package eu.scasefp7.assetregistry.service.es;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.FilterBuilders.queryFilter;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.dto.ArtefactDTO;
import eu.scasefp7.assetregistry.dto.JsonArtefact;
import eu.scasefp7.assetregistry.index.ArtefactIndex;
import eu.scasefp7.assetregistry.index.BaseIndex;
import eu.scasefp7.assetregistry.index.IndexType;
import eu.scasefp7.assetregistry.service.ArtefactService;
import eu.scasefp7.assetregistry.service.db.ArtefactDbService;

/**
 * Service class for Artefact related ElasticSearch operations.
 */
@Stateless
@Local(ArtefactEsService.class)
public class ArtefactEsServiceImpl extends AbstractEsServiceImpl<Artefact> implements ArtefactEsService {

    private static final Logger LOG = LoggerFactory.getLogger(ArtefactEsServiceImpl.class);

    @EJB
    private ArtefactDbService dbService;

    @EJB
    private ArtefactService artefactService;

    @Override
    public List<ArtefactDTO> find(final String query) {
        SearchResponse response = getSearchResponse(ArtefactIndex.INDEX_NAME, IndexType
                .TYPE_ARTEFACT, query);

        return getArtefactDTOs(response);
    }

    @Override
    public List<ArtefactDTO> find(String query, String domain, String subdomain, String artefacttype) {

        QueryBuilder qb;

        if (null == query) {
            qb = getDomainQueryOnly(domain, subdomain, artefacttype);
        } else {
            qb = getQuery(query, domain, subdomain, artefacttype);
        }

        SearchResponse response = getSearchResponse(ArtefactIndex.INDEX_NAME, IndexType.TYPE_ARTEFACT, qb);

        return getArtefactDTOs(response);
    }

    private QueryBuilder getQuery(String query, String domain, String subdomain, String artefacttype)
    {
        QueryBuilder qb;
        qb = QueryBuilders.matchQuery("_all", query);

        if (query.contains("+") || query.contains(" ")) {
            String q = query.replace("+", " ");
            qb = multiMatchQuery(q, "_all");
        }

        if (null != domain || null != subdomain || null != artefacttype) {

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
            if (null != artefacttype) {
                boolFilter.must(queryFilter(matchQuery(ArtefactIndex
                        .ARTEFACT_TYPE_FIELD, artefacttype)));
            }
            qb = QueryBuilders.filteredQuery(matchClause,
                    boolFilter);
        }
        return qb;
    }

    private QueryBuilder getDomainQueryOnly(String domain, String subdomain, String artefacttype)
    {
        QueryBuilder qb;
        qb = QueryBuilders.boolQuery();
        if (null != domain) {
            ((BoolQueryBuilder) qb).must(QueryBuilders.matchQuery(BaseIndex
                    .DOMAIN_FIELD, domain));
        }
        if (null != subdomain) {
            ((BoolQueryBuilder) qb).must(QueryBuilders.matchQuery(BaseIndex.SUBDOMAIN_FIELD, subdomain));
        }
        if (null != artefacttype) {
            ((BoolQueryBuilder) qb).must(QueryBuilders.matchQuery(ArtefactIndex.ARTEFACT_TYPE_FIELD, artefacttype));
        }
        return qb;
    }

    @Override
    public IndexResponse index(final Artefact artefact) throws IOException {

        Client client = this.connectorService.getClient();
        String string = artefact.getId().toString();
        IndexRequestBuilder prepareIndex = client.prepareIndex(ArtefactIndex.INDEX_NAME,
                IndexType.TYPE_ARTEFACT, string);
        XContentBuilder builder = builder(artefact);
        IndexRequestBuilder setSource = prepareIndex.setSource(builder);
        ListenableActionFuture<IndexResponse> execute = setSource.execute();
        IndexResponse response = execute.actionGet();

        return response;
    }

    @Override
    public UpdateResponse update(final Artefact artefact) throws IOException {

        UpdateResponse response = this.connectorService.getClient().prepareUpdate(ArtefactIndex.INDEX_NAME,
                IndexType.TYPE_ARTEFACT, artefact.getId().toString()).setDoc(builder(artefact)).get();

        return response;
    }

    private List<ArtefactDTO> getArtefactDTOs(SearchResponse response) {
        final List<ArtefactDTO> result = new ArrayList<ArtefactDTO>();
        for (SearchHit hit : response.getHits().hits()) {
            String documentId = hit.getId();
            try{
                Long artefactId = Long.valueOf(documentId);
                final Artefact artefact = this.dbService.find(artefactId);
                if (null != artefact) {
                    ArtefactDTO dto = new ArtefactDTO();
                    JsonArtefact jsonArtefact = this.artefactService.convertEntityToJson(artefact);
                    dto.setArtefact(jsonArtefact);
                    dto.setScore(hit.getScore());
                    result.add(dto);
                } else {
                    LOG.warn("Artefact with id " + documentId + "could not be loaded");
                }
            }catch(NumberFormatException ex){
                LOG.error("Elastic document ID "  + documentId + " could not be converted into a number. ",ex);
            }
        }
        return result;
    }

    private XContentBuilder builder(Artefact artefact) throws IOException {

        XContentBuilder builder = jsonBuilder().startObject()
                .field(BaseIndex.NAME_FIELD, artefact.getName())
                .field(BaseIndex.PRIVACY_LEVEL_FIELD, artefact.getPrivacyLevel())
                .field(BaseIndex.CREATED_BY_FIELD, artefact.getCreatedBy())
                .field(BaseIndex.UPDATED_BY_FIELD, artefact.getUpdatedBy())
                .field(BaseIndex.CREATED_AT_FIELD, artefact.getCreatedBy())
                .field(BaseIndex.UPDATED_AT_FIELD, artefact.getUpdatedAt())
                .field(BaseIndex.VERSION_FIELD, artefact.getVersion())
                .field(ArtefactIndex.PROJECT_NAME, artefact.getProjectName())
                .field(ArtefactIndex.URI_FIELD, artefact.getUri())
                .field(ArtefactIndex.GROUPID_FIELD, artefact.getGroupId())
                .array(ArtefactIndex.DEPENDENCIES_FIELD, (Object[])artefact.getDependencies().toArray(new Long[artefact
                        .getDependencies().size()]))
                .field(ArtefactIndex.ARTEFACT_TYPE_FIELD, artefact.getType())
                .field(ArtefactIndex.DESCRIPTION_FIELD, artefact.getDescription())
                .array(ArtefactIndex.TAGS_FIELD, artefact.getTags().toArray(new String[artefact.getTags().size()]))
                .field(ArtefactIndex.METADATA_FIELD, artefact.getMetadata())
                .field(BaseIndex.DOMAIN_FIELD, (null != artefact.getDomain() ? artefact.getDomain().getName() :
                        null))
                .field(BaseIndex.SUBDOMAIN_FIELD, (null != artefact.getSubDomain() ? artefact.getSubDomain()
                        .getName() : null))
                .endObject();

        return builder;
    }
}
