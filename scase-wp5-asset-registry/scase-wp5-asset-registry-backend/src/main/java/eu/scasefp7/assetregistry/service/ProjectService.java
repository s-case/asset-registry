package eu.scasefp7.assetregistry.service;

import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.dto.JsonProject;
import eu.scasefp7.assetregistry.dto.ProjectDTO;

import java.util.List;

/**
 * Interface for Project related services to interact with the S-Case Asset Repository.
 */
public interface ProjectService {

    /**
     * Service to retrieve a {@link eu.scasefp7.assetregistry.data.Project Project} from the repository by ID.
     *
     * @param id - The ID of the {@link eu.scasefp7.assetregistry.data.Project Project}
     * @return {@link eu.scasefp7.assetregistry.data.Project Project}
     */
    Project find( long id );

    /**
     * Service to retrieve a {@link eu.scasefp7.assetregistry.data.Project Project} from the repository by it's name.
     *
     * @param name - The name of the {@link eu.scasefp7.assetregistry.data.Project Project}.
     * @return {@link eu.scasefp7.assetregistry.data.Project Project}.
     */
    Project findByNameOrId( String nameOrId );

    /**
     * Service to find a list of {@link eu.scasefp7.assetregistry.dto.JsonProject Projects}
     * and it's score provided from Elastic Search.
     *
     * @param query A query string in the JSON format as defined by the Elastic Search query engine documentation
     *              e.g. {"filtered":
     *              {"query":
     *              {"match":{"_all":"simple"}},
     *              "filter":{
     *              "bool":{
     *              "must":{"query":{"match":{"domain":"Autos & Vehicles"}}},
     *              "must":{"query":{"match":{"subdomain":"Campers & RVs"}}}
     *              }
     *              }
     *              }
     *              }
     * @return List<ProjectDTO> -
     * A list of {@link eu.scasefp7.assetregistry.dto.ProjectDTO Projects} and it's score.
     */
    List<ProjectDTO> find( String query );

    /**
     * Service to find a list of {@link eu.scasefp7.assetregistry.dto.JsonProject Projects}
     * and it's score provided from Elastic Search. All parameters are optional and can be combined
     * as needed. At least one parameter should be provided.
     *
     * @param query     A free text string that should be searched for inside of the AR
     * @param domain    A domain a {@link eu.scasefp7.assetregistry.data.Project Project} is assigned to.
     * @param subdomain A subdomain a {@link eu.scasefp7.assetregistry.data.Project Project} is assigned to.
     * @return List<ProjectDTO> -
     * A list of {@link eu.scasefp7.assetregistry.dto.ProjectDTO Projects} and it's score.
     */
    List<ProjectDTO> find( String query, String domain, String subdomain );

    /**
     * Service to create an {@link eu.scasefp7.assetregistry.data.Project Project} inside
     * of the Asset Repository.
     *
     * @param project {@link eu.scasefp7.assetregistry.data.Project Project} to be created.
     * @return {@link eu.scasefp7.assetregistry.data.Project Project} - the project created.
     */
    Project create( Project project );

    /**
     * Service to update an {@link eu.scasefp7.assetregistry.data.Project project} already stored inside
     * of the Asset Repository.
     *
     * @param project {@link eu.scasefp7.assetregistry.data.Project Project} to be updated.
     * @return {@link eu.scasefp7.assetregistry.data.Project Project} - the updated project.
     */
    Project update( Project project );

    /**
     * Service to remove a {@link eu.scasefp7.assetregistry.data.Project Project} from the repository by
     * providing it's ID.
     *
     * @param id - the ID of the {@link eu.scasefp7.assetregistry.data.Project Project} to be deleted.
     */
    void delete( long id );

    /**
     * Service to remove a {@link eu.scasefp7.assetregistry.data.Project Project} from the repository by
     * providing it's name.
     *
     * @param name - the name of the {@link eu.scasefp7.assetregistry.data.Project Project} to be deleted.
     */
    void delete( String name );

    /**
     * Service to remove a {@link eu.scasefp7.assetregistry.data.Project Project} from the repository
     *
     * @param project the {@link eu.scasefp7.assetregistry.data.Project Project}
     */
    void delete( Project project );

    /**
     * Service to convert an {@link eu.scasefp7.assetregistry.dto.JsonProject JsonProject} into a
     * {@link eu.scasefp7.assetregistry.data.Project Project}.
     *
     * @param jsonProject {@link eu.scasefp7.assetregistry.dto.JsonProject JsonProject}
     * @return {@link eu.scasefp7.assetregistry.data.Project Project}
     */
    Project convertJsonToEntity( JsonProject jsonProject );

    /**
     * Service to convert an {@link eu.scasefp7.assetregistry.data.Project Project} into a
     * {@link eu.scasefp7.assetregistry.dto.JsonProject JsonProject}.
     *
     * @param project {@link eu.scasefp7.assetregistry.data.Project Project}
     * @return {@link eu.scasefp7.assetregistry.dto.JsonProject JsonProject}
     */
    JsonProject convertEntityToJson( Project project );
}
