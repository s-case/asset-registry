package eu.scasefp7.assetregistry.service;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.dto.ArtefactDTO;
import eu.scasefp7.assetregistry.dto.JsonArtefact;

import java.util.List;

/**
 * Created by missler on 09/04/15.
 */
public interface ArtefactService {

    /**
     * Service to retrieve an {@link eu.scasefp7.assetregistry.data.Artefact Artefact} from the repository by ID.
     * @param id - The ID of the {@link eu.scasefp7.assetregistry.data.Artefact Artefact}
     * @return {@link eu.scasefp7.assetregistry.data.Artefact Artefact}
     */
    Artefact find(long id);

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
     *      A list of {@link eu.scasefp7.assetregistry.dto.ArtefactDTO} Artefacts and it's score.
     */
    List<ArtefactDTO> find(String query);

    /**
     * Service to find a list of {@link eu.scasefp7.assetregistry.dto.JsonArtefact Artefacts}
     * and it's score provided from Elastic Search. All parameters are optional and can be combined
     * as needed. At least one parameter should be provided.
     * @param query A free text string that should be searched for inside of the AR.
     * @param domain A domain an {@link eu.scasefp7.assetregistry.data.Artefact} Artefact is assigned to.
     * @param subdomain A subdomain an {@link eu.scasefp7.assetregistry.data.Artefact} Artefact is assigned to.
     * @param artefacttype An {@link eu.scasefp7.assetregistry.data.ArtefactType ArtefactType} assigned to an
     *           {@link eu.scasefp7.assetregistry.data.Artefact Artefact}.
     * @return List<ArtefactDTO> -
     *      A list of {@link eu.scasefp7.assetregistry.dto.ArtefactDTO Artefacts} and their score.
     */
    List<ArtefactDTO> find(String query, String domain, String subdomain, String artefacttype);

    /**
     * Service to create an {@link eu.scasefp7.assetregistry.data.Artefact Artefact} inside
     *  of the Asset Repository.
     * @param artefact {@link eu.scasefp7.assetregistry.data.Artefact Artefact} to be created.
     * @return {@link eu.scasefp7.assetregistry.data.Artefact Artefact} - the artefact created.
     */
    Artefact create(Artefact artefact);

    /**
     * Service to update an {@link eu.scasefp7.assetregistry.data.Artefact Artefact} already stored inside
     *  of the Asset Repository.
     * @param artefact {@link eu.scasefp7.assetregistry.data.Artefact Artefact} to be updated.
     * @return {@link eu.scasefp7.assetregistry.data.Artefact Artefact} - the updated artefact.
     */
    Artefact update(Artefact artefact);

    /**
     * Service to remove an {@link eu.scasefp7.assetregistry.data.Artefact Artefact} from the repository
     *  by providing it's ID.
     * @param id - the ID of the {@link eu.scasefp7.assetregistry.data.Artefact Artefact}to be deleted.
     */
    void delete(long id);

    /**
     * Service to remove an {@link eu.scasefp7.assetregistry.data.Artefact Artefact} from the repository
     *  by providing the artefact itself.
     * @param artefact - the {@link eu.scasefp7.assetregistry.data.Artefact Artefact} to be deleted.
     */
    void delete(Artefact artefact);

    /**
     * Service to convert an {@link eu.scasefp7.assetregistry.dto.JsonArtefact JsonArtefact} into an
     *  {@link eu.scasefp7.assetregistry.data.Artefact Artefact}.
     * @param jsonArtefact {@link eu.scasefp7.assetregistry.dto.JsonArtefact JsonArtefact}
     * @return {@link eu.scasefp7.assetregistry.data.Artefact Artefact}
     */
    Artefact convertJsonToEntity(JsonArtefact jsonArtefact);

    /**
     * Service to convert an {@link eu.scasefp7.assetregistry.data.Artefact Artefact} into an
     *  {@link eu.scasefp7.assetregistry.dto.JsonArtefact JsonArtefact}.
     * @param artefact {@link eu.scasefp7.assetregistry.data.Artefact Artefact}
     * @return {@link eu.scasefp7.assetregistry.dto.JsonArtefact JsonArtefact}
     */
    JsonArtefact convertEntityToJson(Artefact artefact);
}
