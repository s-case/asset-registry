package eu.fp7.scase.assetregistry.service.es;

import eu.fp7.scase.assetregistry.connector.ElasticSearchConnectorService;
import eu.fp7.scase.assetregistry.data.BaseEntity;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import javax.ejb.EJB;

/**
 * Created by missler on 16/03/15.
 */
public abstract class AbstractEsServiceImpl<E extends BaseEntity> implements AbstractEsService<E> {

    @EJB
    protected ElasticSearchConnectorService connectorService;

    @Override
    public DeleteResponse delete(long id, final String index, final String type){
        return connectorService.getClient().prepareDelete(index,type,Long.toString(id)).execute().actionGet();
    }

    @Override
    public DeleteResponse delete(final E entity, final String index, final String type){
        return connectorService.getClient().prepareDelete(index,type,Long.toString(entity.getId())).execute().actionGet();
    }

    protected QueryBuilder queryStringQuery( final String query ) {
        QueryBuilder qb = QueryBuilders.queryString(query);
        return qb;
    }

    protected SearchResponse getSearchResponse(String index, String type, String query) {
        final QueryBuilder queryBuilder = queryStringQuery( query );

        return connectorService.getClient().prepareSearch(index)
                .setTypes(type )
                .setQuery( queryBuilder )
                .setFrom( 0 ).setSize( 500 ).setExplain( true )
                .execute()
                .actionGet();
    }

}
