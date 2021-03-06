package eu.scasefp7.assetregistry.service.es;

import java.io.IOException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

import eu.scasefp7.assetregistry.data.BaseEntity;

/**
 * Created by missler on 09/04/15.
 */
public interface AbstractEsService<E extends BaseEntity> {

    IndexResponse index(E entity) throws IOException;

    UpdateResponse update(E entity) throws IOException;

    DeleteResponse delete(long id, String index, String type);

    DeleteResponse delete(E entity, String index, String type);
}
