package eu.scasefp7.assetregistry.service;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.dto.ArtefactDTO;
import eu.scasefp7.assetregistry.dto.JsonArtefact;

import java.util.List;

/**
 * Created by missler on 09/04/15.
 */
public interface ArtefactService {
    Artefact find(long id);

    List<ArtefactDTO> find(String query);

    Artefact create(Artefact artefact);

    Artefact update(Artefact artefact);

    void delete(long id);

    void delete(Artefact artefact);

    Artefact convertJsonToEntity(JsonArtefact jsonArtefact);
    JsonArtefact convertEntityToJson(Artefact artefact);
}
