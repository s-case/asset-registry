package eu.scasefp7.assetregistry.service;

import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.dto.JsonProject;
import eu.scasefp7.assetregistry.dto.ProjectDTO;

import java.util.List;

/**
 * Created by missler on 09/04/15.
 */
public interface ProjectService {
    Project find(long id);

    Project findByName(String name);

    List<ProjectDTO> find(String query);

    Project create(Project project);

    Project update(Project project);

    void delete(long id);

    void delete(String name);

    void delete(Project project);

    Project convertJsonToEntity(JsonProject jsonProject);
    JsonProject convertEntityToJson(Project project);
}
