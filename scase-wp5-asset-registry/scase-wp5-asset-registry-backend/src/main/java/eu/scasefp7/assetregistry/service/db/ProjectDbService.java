package eu.scasefp7.assetregistry.service.db;

import eu.scasefp7.assetregistry.data.Project;

/**
 * Created by missler on 09/04/15.
 */
public interface ProjectDbService extends BaseCrudDbService<Project> {

    Project find(String name);

    @Override
    Project update(Project entity);
}
