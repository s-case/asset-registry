package eu.scasefp7.assetregistry.service.es;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Inject
    ObjectMapper mapper;

    @Override
    public List<Artefact> find(final String query) {
        SearchResponse response = getSearchResponse(ArtefactIndex.INDEX_NAME, IndexType
                .TYPE_ARTEFACT, query);

        final List<Artefact> result = new ArrayList<Artefact>();
        for (SearchHit hit : response.getHits().hits()) {
            final Artefact artefact = this.dbService.find(Long.valueOf(hit.getId()));
            if (null != artefact) {
                result.add(artefact);
            } else {
                LOG.warn("Artefact with id " + hit.getId() + "could not be loaded");
            }
        }
        return result;
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

    private XContentBuilder builder(Artefact artefact) throws IOException {

        XContentBuilder builder = jsonBuilder().startObject()
                .field(ArtefactIndex.NAME_FIELD, artefact.getName())
                .field(ArtefactIndex.CREATED_BY_FIELD, artefact.getCreatedBy())
                .field(ArtefactIndex.UPDATED_BY_FIELD, artefact.getUpdatedBy())
                .field(ArtefactIndex.CREATED_AT_FIELD, artefact.getCreatedBy())
                .field(ArtefactIndex.UPDATED_AT_FIELD, artefact.getUpdatedAt())
                .field(ArtefactIndex.VERSION_FIELD, artefact.getVersion())
                .field(ArtefactIndex.URI_FIELD, artefact.getUri())
                .field(ArtefactIndex.GROUPID_FIELD, artefact.getGroupId())
                .array(ArtefactIndex.DEPENDENCIES_FIELD, artefact.getDependencies().toArray(new Long[artefact
                        .getDependencies().size()]))
                .field(ArtefactIndex.ARTEFACT_TYPE_FIELD, artefact.getType())
                .field(ArtefactIndex.DESCRIPTION_FIELD, artefact.getDescription())
                .array(ArtefactIndex.TAGS_FIELD, artefact.getTags().toArray(new String[artefact.getTags().size()]))
                .field(ArtefactIndex.METADATA_FIELD, artefact.getMetadata())
                .endObject();

        return builder;
    }
}
