package eu.scasefp7.assetregistry.service;

import eu.scasefp7.assetregistry.data.Project;

import java.util.List;

/**
 * Created by missler on 09/04/15.
 */
public interface ProjectService {
    Project find(long id);

    List<Project> find(String query);

    Project create(Project project);

    Project update(Project project);

    void delete(long id);

    void delete(String name);

    void delete(Project project);
}
