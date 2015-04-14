package eu.fp7.scase.assetregistry.service;

import eu.fp7.scase.assetregistry.data.Artefact;

import java.util.List;

/**
 * Created by missler on 09/04/15.
 */
public interface ArtefactService {
    Artefact find(long id);

    List<Artefact> find(String query);

    Artefact create(Artefact artefact);

    Artefact update(Artefact artefact);

    void delete(long id);

    void delete(Artefact artefact);
}
