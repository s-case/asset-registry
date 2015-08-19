package eu.scasefp7.assetregistry.service.es;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.update.UpdateResponse;

import eu.scasefp7.assetregistry.data.PrivacyLevel;
import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.dto.ProjectDTO;

/**
 * Created by missler on 09/04/15.
 */
public interface ProjectEsService extends AbstractEsService<Project>{

    /**
     * Service to find a list of {@link eu.scasefp7.assetregistry.dto.JsonProject Projects}
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
     * @return List<ProjectDTO> -
     *      A list of {@link eu.scasefp7.assetregistry.dto.ProjectDTO Projects} and it's score.
     */
    List<ProjectDTO> find(String query);

    /**
     * Service to find a list of {@link eu.scasefp7.assetregistry.dto.JsonProject Projects}
     * and it's score provided from Elastic Search. All parameters are optional and can be combined
     * as needed. At least one parameter should be provided.
     * @param query A free text string that should be searched for inside of the AR
     * @param domain A domain a {@link eu.scasefp7.assetregistry.data.Project Project} is assigned to.
     * @param subdomain A subdomain a {@link eu.scasefp7.assetregistry.data.Project Project} is assigned to.
     * @return List<ProjectDTO> -
     *      A list of {@link eu.scasefp7.assetregistry.dto.ProjectDTO Projects} and it's score.
     */
    List<ProjectDTO> find(String query, String domain, String subdomain);

    UpdateResponse updatePrivacyLevel(final long id, final PrivacyLevel privacyLevel) throws IOException;
}
