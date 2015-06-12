package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.dto.JsonProject;
import eu.scasefp7.assetregistry.dto.ProjectDTO;
import eu.scasefp7.assetregistry.service.ProjectService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URISyntaxException;
import java.util.List;

import static eu.scasefp7.assetregistry.rest.ResourceTools.redirect;

@Path( AssetRegistryRestApp.PART_PROJECT)
@Produces( "application/json;charset=UTF-8" )
@Consumes("application/json")
@Stateless
public class ProjectResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectResource.class);

    @EJB
    private ProjectService projectService;

    /**
     * Find a project in the repository by ID
     * @param id
     * @return Project project
     */
    @GET
    @Path("{id}")
    public JsonProject get( @PathParam("id") long id ) {
        final Project projectEntity = this.projectService.find( id );
        final JsonProject jsonProject = projectService.convertEntityToJson(projectEntity);
        return jsonProject;
    }

    /**
     * Find a project in the repository by its name
     * @param name - the name of the project
     * @return Project project
     */
    @GET
    @Path("{name}")
    public JsonProject get(@PathParam("name") String name){
        final Project projectEntity = this.projectService.findByName(name);
        final JsonProject jsonProject = projectService.convertEntityToJson(projectEntity);
        return jsonProject;
    }
    /**
     *
     * @param query
     * @return List<Projects> projects
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectDTO> searchProjects(@QueryParam("q") final String query) {
        LOG.info("search '{}'", query);
        final List<ProjectDTO> projects = projectService.find(query);
        return projects;
    }

    /**
     * Create and store a new project in the repository
     * @param project The project to be stored inside of the Asset Repo
     * @return {#link javax.ws.rs.core.Response Response}
     * @throws URISyntaxException
     */
    @POST
    public Response create( JsonProject project ) throws URISyntaxException {
        final Project projectEntity = projectService.convertJsonToEntity(project);
        final Project created = this.projectService.create(projectEntity);
        return redirect( "project/" + created.getId() );
    }

    /**
     * Update a project in the Asset Repo
     * @param id Project ID
     * @param project The project to be updated inside of the Asset Repo
     * @return {#link javax.ws.rs.core.Response HTTP Response code}
     * @throws URISyntaxException
     */
    @PUT
    @Path("{id}")
    public Response update( @PathParam("id") long id, JsonProject project ) throws URISyntaxException {
        project.setId( id );
        final Project projectEntity = projectService.convertJsonToEntity(project);
        final Project updated = this.projectService.update(projectEntity);
        return redirect( "project/", updated );
    }

    /**
     * Delete a project from the Asset Repo
     * @param id ID of the project to be deleted
     */
    @DELETE
    @Path("{id}")
    public void delete( @PathParam("id") long id ) {
        this.projectService.delete(id);
    }

    /**
     * Delete a project from the Asset Repo
     * @param name Name string of the project to be deleted
     */
    @DELETE
    @Path("{name}")
    public void delete(@PathParam("name") String name) {
        this.projectService.delete(name);
    }

}
