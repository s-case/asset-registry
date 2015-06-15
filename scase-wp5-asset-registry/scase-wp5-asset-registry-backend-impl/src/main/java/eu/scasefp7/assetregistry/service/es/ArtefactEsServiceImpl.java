package eu.scasefp7.assetregistry.service.es;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import eu.scasefp7.assetregistry.dto.ArtefactDTO;
import eu.scasefp7.assetregistry.dto.JsonArtefact;
import eu.scasefp7.assetregistry.service.ArtefactService;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.index.ArtefactIndex;
import eu.scasefp7.assetregistry.index.IndexType;
import eu.scasefp7.assetregistry.service.db.ArtefactDbService;

/**
 * Service class for Artefact related ElasticSearch operations
 */
@Stateless
@Local(ArtefactEsService.class)
public class ArtefactEsServiceImpl extends AbstractEsServiceImpl<Artefact> implements ArtefactEsService {

    private final static Logger LOG = LoggerFactory.getLogger(ArtefactEsServiceImpl.class);

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
    public List<ArtefactDTO> findByDomainAndSubdomain(String domain, String subdomain){
        QueryBuilder querybuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery(ArtefactIndex.DOMAIN_FIELD, domain)).must(QueryBuilders.matchQuery(ArtefactIndex.SUBDOMAIN_FIELD,subdomain));

        SearchResponse response = getSearchResponse(ArtefactIndex.INDEX_NAME, IndexType
                .TYPE_ARTEFACT, querybuilder);
        return getArtefactDTOs(response);
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
            final Artefact artefact = this.dbService.find(Long.valueOf(hit.getId()));
            if (null != artefact) {
                ArtefactDTO dto = new ArtefactDTO();
                JsonArtefact jsonArtefact = artefactService.convertEntityToJson(artefact);
                dto.setArtefact(jsonArtefact);
                dto.setScore(hit.getScore());
                result.add(dto);
            } else {
                LOG.warn("Artefact with id " + hit.getId() + "could not be loaded");
            }
        }
        return result;
    }

    private XContentBuilder builder(Artefact artefact) throws IOException {

        XContentBuilder builder = jsonBuilder().startObject()
                .field(ArtefactIndex.NAME_FIELD, artefact.getName())
                .field(ArtefactIndex.PRIVACY_LEVEL_FIELD, artefact.getPrivacyLevel())
                .field(ArtefactIndex.CREATED_BY_FIELD, artefact.getCreatedBy())
                .field(ArtefactIndex.UPDATED_BY_FIELD, artefact.getUpdatedBy())
                .field(ArtefactIndex.CREATED_AT_FIELD, artefact.getCreatedBy())
                .field(ArtefactIndex.UPDATED_AT_FIELD, artefact.getUpdatedAt())
                .field(ArtefactIndex.VERSION_FIELD, artefact.getVersion())
                .field(ArtefactIndex.PROJECT_NAME, artefact.getProjectName())
                .field(ArtefactIndex.URI_FIELD, artefact.getUri())
                .field(ArtefactIndex.GROUPID_FIELD, artefact.getGroupId())
                .array(ArtefactIndex.DEPENDENCIES_FIELD, artefact.getDependencies().toArray(new Long[artefact
                        .getDependencies().size()]))
                .field(ArtefactIndex.ARTEFACT_TYPE_FIELD, artefact.getType())
                .field(ArtefactIndex.DESCRIPTION_FIELD, artefact.getDescription())
                .array(ArtefactIndex.TAGS_FIELD, artefact.getTags().toArray(new String[artefact.getTags().size()]))
                .field(ArtefactIndex.METADATA_FIELD, artefact.getMetadata())
                .field(ArtefactIndex.DOMAIN_FIELD,(null!=artefact.getDomain() ? artefact.getDomain().getName() : null))
                .field(ArtefactIndex.SUBDOMAIN_FIELD,(null!=artefact.getSubDomain() ? artefact.getSubDomain().getName() : null))
                .endObject();

        return builder;
    }
}
