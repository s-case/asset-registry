package eu.scasefp7.assetregistry.service.es;

import javax.ejb.EJB;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;

import eu.scasefp7.assetregistry.connector.ElasticSearchConnectorService;
import eu.scasefp7.assetregistry.data.BaseEntity;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * Created by missler on 16/03/15.
 * @param <E> the concrete type
 */
public abstract class AbstractEsServiceImpl<E extends BaseEntity> implements AbstractEsService<E> {

    @EJB
    protected ElasticSearchConnectorService connectorService;

    @Override
    public DeleteResponse delete(long id, final String index, final String type){
        return this.connectorService.getClient().prepareDelete(index,type,Long.toString(id)).execute().actionGet();
    }

    @Override
    public DeleteResponse delete(final E entity, final String index, final String type){
        return this.connectorService.getClient().prepareDelete(index,type,Long.toString(entity.getId())).execute().actionGet();
    }

    protected SearchResponse getSearchResponse(String index, String type, String query) {

        Client client = this.connectorService.getClient();
        return client.prepareSearch(index)
                .setTypes(type)
                .setQuery(query)
                .setFrom( 0 ).setSize( 500 ).setExplain( true )
                .execute()
                .actionGet();
    }

    protected SearchResponse getSearchResponse(String index, String type, QueryBuilder querybuilder) {

        Client client = this.connectorService.getClient();
        return client.prepareSearch(index)
                .setTypes(type)
                .setQuery(querybuilder)
                .setFrom( 0 ).setSize( 500 ).setExplain( true )
                .execute()
                .actionGet();
    }

}
