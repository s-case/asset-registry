package eu.scasefp7.assetregistry.service.db;

import eu.scasefp7.assetregistry.data.Artefact;

/**
 * Created by missler on 09/04/15.
 */
public interface ArtefactDbService extends BaseCrudDbService<Artefact> {

    @Override
    Artefact update(Artefact entity);
}
