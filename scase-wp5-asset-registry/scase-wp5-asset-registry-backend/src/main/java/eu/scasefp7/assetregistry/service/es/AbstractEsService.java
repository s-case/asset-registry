package eu.scasefp7.assetregistry.service.es;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.scasefp7.assetregistry.data.BaseEntity;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

import java.util.List;

/**
 * Created by missler on 09/04/15.
 */
public interface AbstractEsService<E extends BaseEntity> {
    List<E> find(String query);

    IndexResponse index(E entity) throws JsonProcessingException;

    UpdateResponse update(E entity) throws JsonProcessingException;

    DeleteResponse delete(long id, String index, String type);

    DeleteResponse delete(E entity, String index, String type);
}
