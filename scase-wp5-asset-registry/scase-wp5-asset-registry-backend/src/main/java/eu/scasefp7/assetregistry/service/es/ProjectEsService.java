package eu.scasefp7.assetregistry.service.es;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.scasefp7.assetregistry.data.PrivacyLevel;
import eu.scasefp7.assetregistry.data.Project;
import org.elasticsearch.action.update.UpdateResponse;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by missler on 09/04/15.
 */
public interface ProjectEsService extends AbstractEsService<Project>{

    UpdateResponse updatePrivacyLevel(final long id, final PrivacyLevel privacyLevel) throws IOException;
}
