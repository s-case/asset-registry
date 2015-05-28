package eu.scasefp7.assetregistry.service.es;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.scasefp7.assetregistry.data.PrivacyLevel;
import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.dto.ArtefactDTO;
import eu.scasefp7.assetregistry.dto.ProjectDTO;
import org.elasticsearch.action.update.UpdateResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by missler on 09/04/15.
 */
public interface ProjectEsService extends AbstractEsService<Project>{

    List<ProjectDTO> find(String query);
    UpdateResponse updatePrivacyLevel(final long id, final PrivacyLevel privacyLevel) throws IOException;
}
