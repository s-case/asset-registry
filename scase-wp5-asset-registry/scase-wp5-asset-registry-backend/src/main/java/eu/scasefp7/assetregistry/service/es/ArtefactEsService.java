package eu.scasefp7.assetregistry.service.es;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.dto.ArtefactDTO;

import java.util.List;

/**
 * Created by missler on 09/04/15.
 */
public interface ArtefactEsService extends AbstractEsService<Artefact>{

    /**
     * Service to find a list of {@link eu.scasefp7.assetregistry.dto.JsonArtefact Artefacts}
     * and it's score provided from Elastic Search.
     * @param query A query string in the JSON format as defined by the Elastic Search query engine documentation
     *              e.g. {"filtered":
     *                      {"query":
     *                          {"match":{"_all":"simple"}},
     *                              "filter":{
     *                              "bool":{
     *                                  "must":{"query":{"match":{"domain":"Autos & Vehicles"}}},
     *                                  "must":{"query":{"match":{"subdomain":"Campers & RVs"}}}
     *                                     }
     *                               }
     *                      }
     *                    }
     * @return List<ArtefactDTO> -
     *      A list of {@link eu.scasefp7.assetregistry.dto.ArtefactDTO Artefacts}and it's score.
     */
    List<ArtefactDTO> find(String query);

    /**
     * Service to find a list of {@link eu.scasefp7.assetregistry.dto.JsonArtefact Artefacts}
     * and it's score provided from Elastic Search. All parameters are optional and can be combined
     * as needed. At least one parameter should be provided.
     * @param query A free text string that should be searched for inside of the AR.
     * @param domain A domain an {@link eu.scasefp7.assetregistry.data.Artefact Artefact} is assigned to.
     * @param subdomain A subdomain an {@link eu.scasefp7.assetregistry.data.Artefact Artefact} is assigned to.
     * @param artefacttype An {@link eu.scasefp7.assetregistry.data.ArtefactType ArtefactType} an
     *           {@link eu.scasefp7.assetregistry.data.Artefact Artefact} is assigned to.
     * @return List<ArtefactDTO> -
     *      A list of {@link eu.scasefp7.assetregistry.dto.ArtefactDTO Artefacts} and it's score.
     */
    List<ArtefactDTO> find(String query, String domain, String subdomain, String artefacttype);
}
