package eu.scasefp7.assetregistry.service.es;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.dto.ArtefactDTO;

import java.util.List;

/**
 * Created by missler on 09/04/15.
 */
public interface ArtefactEsService extends AbstractEsService<Artefact>{

    List<ArtefactDTO> find(String query);

    List<ArtefactDTO> find(String query, String domain, String subdomain, String artefacttype);
}
