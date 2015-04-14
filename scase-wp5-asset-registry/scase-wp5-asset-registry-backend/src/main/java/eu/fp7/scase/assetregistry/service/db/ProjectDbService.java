package eu.fp7.scase.assetregistry.service.db;

import eu.fp7.scase.assetregistry.data.Project;

/**
 * Created by missler on 09/04/15.
 */
public interface ProjectDbService extends BaseCrudDbService<Project> {

    @Override
    Project update(Project entity);
}
