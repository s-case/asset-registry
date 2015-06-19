package eu.scasefp7.assetregistry.service.es;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.update.UpdateResponse;

import eu.scasefp7.assetregistry.data.PrivacyLevel;
import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.dto.ProjectDTO;

/**
 * Created by missler on 09/04/15.
 */
public interface ProjectEsService extends AbstractEsService<Project>{

    List<ProjectDTO> find(String query);

    List<ProjectDTO> find(String query, String domain, String subdomain);

    UpdateResponse updatePrivacyLevel(final long id, final PrivacyLevel privacyLevel) throws IOException;
}
