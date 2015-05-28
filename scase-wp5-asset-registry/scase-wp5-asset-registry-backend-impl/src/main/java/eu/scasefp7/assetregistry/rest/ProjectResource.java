package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.data.Project;
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
import javax.ws.rs.core.Application;
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
    public Project get( @PathParam("id") long id ) {
        return this.projectService.find( id );
    }

    /**
     * Find a project in the repository by its name
     * @param name - the name of the project
     * @return Project project
     */
    @GET
    @Path("{name}")
    public Project get(@PathParam("name") String name){
        return this.projectService.findByName(name);
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
     * @param project
     * @return
     * @throws URISyntaxException
     */
    @POST
    public Response create( Project project ) throws URISyntaxException {
        final Project created = this.projectService.create(project);

        return redirect( "project/" + created.getId() );
    }

    /**
     * Update a project in the repository
     * @param id
     * @param project
     * @return
     * @throws URISyntaxException
     */
    @PUT
    @Path("{id}")
    public Response update( @PathParam("id") long id, Project project ) throws URISyntaxException {
        project.setId( id );
        final Project updated = this.projectService.update(project);
        return redirect( "project/", updated );
    }

    /**
     * Delete a project from the repository
     * @param id
     */
    @DELETE
    @Path("{id}")
    public void delete( @PathParam("id") long id ) {
        this.projectService.delete(id);
    }

    @DELETE
    @Path("{name}")
    public void delete(@PathParam("name") String name) {
        this.projectService.delete(name);
    }

}
