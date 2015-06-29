package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.dto.JsonProject;
import eu.scasefp7.assetregistry.dto.ProjectDTO;
import eu.scasefp7.assetregistry.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@Path(AssetRegistryRestApp.PART_PROJECT)
@Api(value = AssetRegistryRestApp.PART_SUBDOMAIN, description = "provides projects")
@Produces("application/json;charset=UTF-8")
@Consumes("application/json")
@Stateless
public class ProjectResource
{
    private static final Logger LOG = LoggerFactory.getLogger(ProjectResource.class);

    @EJB
    private ProjectService projectService;

    /**
     * Find a project in the repository by ID
     *
     * @param id
     * @return Project project
     */
    @GET
    @Path("{id}/id")
    @ApiOperation(value = "Finds a project in the repository by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 400, message = "Request incorrect"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Server problem")})
    public JsonProject get(@PathParam("id") @ApiParam(value = "project ID") long id)
    {
        final Project projectEntity = this.projectService.find(id);
        final JsonProject jsonProject = projectService.convertEntityToJson(projectEntity);
        return jsonProject;
    }

    /**
     * Find a project in the repository by its name
     *
     * @param name - the name of the project
     * @return Project project
     */
    @GET
    @Path("{name}/name")
    @ApiOperation(value = "Finds a project in the repository by its name")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 400, message = "Request incorrect"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Server problem")})
    public JsonProject get(@PathParam("name") @ApiParam(value = "the name of the project") String name)
    {

        Project projectEntity = null;
        JsonProject jsonProject = null;

        try {
            long id = Long.parseLong(name);
            projectEntity = this.projectService.find(id);
        } catch (NumberFormatException nfe) {
            LOG.warn("Value " + name + " could not be parsed into a number. Trying to find the project by name.");
            projectEntity = this.projectService.findByName(name);
        }

        if (null != projectEntity) {
            jsonProject = projectService.convertEntityToJson(projectEntity);
        }

        return jsonProject;
    }

    /**
     * Find a list of projects in the repository by search query
     *
     * @param query
     * @return List<Projects> projects
     */
    @GET
    @Path("directsearch")
    @ApiOperation(value = "Finds a list of projects in the repository by search query")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 400, message = "Request incorrect"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Server problem")})
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectDTO> searchProjects(@QueryParam("q") @ApiParam(value = "search query") final String query)
    {
        LOG.info("search '{}'", query);
        final List<ProjectDTO> projects = projectService.find(query);
        return projects;
    }


    /**
     * Find a list of projects by free text search strings
     *
     * @param domain
     * @param subdomain
     * @return List<ProjectsDTO> projects
     */
    @GET
    @Path("search")
    @ApiOperation(value = "Finds a list of projects by free text search strings")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 400, message = "Request incorrect"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Server problem")})
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectDTO> searchProjects(@QueryParam("query") @ApiParam(value = "Elastic Search query string") final String query,
                                           @QueryParam("domain") @ApiParam(value = "Elastic Search domain") final String domain,
                                           @QueryParam("subdomain") @ApiParam(value = "Elastic Search subdomain") final String subdomain)
    {
        final List<ProjectDTO> projects = projectService.find(query, domain, subdomain);
        return projects;
    }

    /**
     * Create and store a new project in the repository
     *
     * @param project The project to be stored inside of the Asset Repo
     * @return {#link javax.ws.rs.core.Response Response}
     * @throws URISyntaxException
     */
    @POST
    @ApiOperation(value = "Creates and stores a new project in the repository")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 400, message = "Request incorrect"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Server problem")})
    public Response create(@ApiParam(value = "The project to be stored inside of the Asset Repo") JsonProject project)
            throws URISyntaxException
    {
        final Project projectEntity = projectService.convertJsonToEntity(project);
        final Project created = this.projectService.create(projectEntity);

        return redirect("project/" + created.getId());
    }

    /**
     * Update a project in the Asset Repo
     *
     * @param id      Project ID
     * @param project The project to be updated inside of the Asset Repo
     * @return {#link javax.ws.rs.core.Response HTTP Response code}
     * @throws URISyntaxException
     */
    @PUT
    @Path("{id}")
    @ApiOperation(value = "Updates a project in the Asset Repo")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 400, message = "Request incorrect"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Server problem")})
    public Response update(@PathParam("id") @ApiParam(value = "Project ID") long id,
                           @ApiParam(value = "The project to be updated inside of the Asset Repo") JsonProject project)
            throws URISyntaxException
    {
        project.setId(id);
        final Project projectEntity = projectService.convertJsonToEntity(project);
        final Project updated = this.projectService.update(projectEntity);
        return redirect("project/", updated);
    }

    /**
     * Delete a project from the Asset Repo by ID
     *
     * @param id ID of the project to be deleted
     */
    @DELETE
    @Path("{id}")
    @ApiOperation(value = "Deletes a project from the Asset Repo by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 400, message = "Request incorrect"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Server problem")})
    public void delete(@PathParam("id") @ApiParam(value = "ID of the project to be deleted") long id)
    {
        this.projectService.delete(id);
    }

    /**
     * Delete a project from the Asset Repo by name
     *
     * @param name Name string of the project to be deleted
     */
    @DELETE
    @Path("{name}")
    @ApiOperation(value = "Deletes a project from the Asset Repo by name")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 400, message = "Request incorrect"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Server problem")})
    public void delete(@PathParam("name") @ApiParam(value = "name of the project to be deleted") String name)
    {
        this.projectService.delete(name);
    }
}
