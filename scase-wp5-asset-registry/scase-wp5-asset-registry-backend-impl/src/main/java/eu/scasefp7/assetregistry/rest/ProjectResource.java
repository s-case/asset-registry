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
import org.apache.http.HttpStatus;
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
import java.util.List;

/**
 * rest api for a project.
 *
 * @author rmagnus
 */
@Path( AssetRegistryRestApp.PART_PROJECT )
@Api( value = AssetRegistryRestApp.PART_PROJECT, description = "provides projects" )
@Produces( "application/json;charset=UTF-8" )
@Consumes( "application/json" )
@Stateless
public class ProjectResource {

    private static final Logger LOG = LoggerFactory.getLogger( ProjectResource.class );

    @EJB
    private ProjectService projectService;

    /**
     * Find a project in the repository by ID.
     *
     * @param id the project id
     * @return Project project
     */
    @GET
    @Path( "{id}" )
    @ApiOperation( value = "Finds a project in the repository by ID",
            response = JsonProject.class )
    @ApiResponses( value = {
            @ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
            @ApiResponse( code = HttpStatus.SC_NO_CONTENT, message = "No content" ),
            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    public Response get( @PathParam( "id" ) @ApiParam( value = "project ID" ) long id ) {

        final Project projectEntity = this.projectService.find( id );
        if ( null != projectEntity ) {
            JsonProject jsonProject = this.projectService.convertEntityToJson( projectEntity );
            return Response.status( Response.Status.OK ).entity( jsonProject ).build();
        }
        return Response.status( Response.Status.NO_CONTENT ).build();
    }

    /**
     * Find a project in the repository by its name.
     *
     * @param name - the name of the project
     * @return Project project
     */
    @GET
    @Path( "{name}" )
    @ApiOperation( value = "Finds a project in the repository by its name",
            response = JsonProject.class )
    @ApiResponses( value = {
            @ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
            @ApiResponse( code = HttpStatus.SC_NO_CONTENT, message = "No content" ),
            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    public Response get( @PathParam( "name" ) @ApiParam( value = "the name of the project" ) String name ) {

        Project projectEntity = this.projectService.findByNameOrId( name );

        if ( null != projectEntity ) {
            JsonProject jsonProject = this.projectService.convertEntityToJson( projectEntity );
            return Response.status( Response.Status.OK ).entity( jsonProject ).build();
        }

        return Response.status( Response.Status.NO_CONTENT ).build();
    }

    /**
     * Find a list of projects in the repository by search query.
     *
     * @param query the query
     * @return List<Projects> projects
     */
    @GET
    @Path( "directsearch" )
    @ApiOperation( value = "Finds a list of projects in the repository by search query. <BR>"
            + "The search query has to be submitted in the Elastic Search JSON search syntax.",
            response = ProjectDTO.class,
            responseContainer = "List" )
    @ApiResponses( value = {
            @ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
            @ApiResponse( code = HttpStatus.SC_NO_CONTENT, message = "No content" ),
            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    @Produces( MediaType.APPLICATION_JSON )
    public Response searchProjects( @QueryParam( "q" )
                                    @ApiParam( value = "Search query in the Elastic Search JSON syntax" ) final String query ) {
        LOG.info( "search '{}'", query );
        final List<ProjectDTO> projects = this.projectService.find( query );
        if ( projects.isEmpty() ) {
            return Response.status( Response.Status.NO_CONTENT ).build();
        }
        return Response.status( Response.Status.OK ).entity( projects ).build();
    }


    /**
     * Find a list of projects by free text search strings.
     *
     * @param query     the query
     * @param domain    the domain
     * @param subdomain the sub domain
     * @return List<ProjectsDTO> projects
     */
    @GET
    @Path( "search" )
    @ApiOperation( value = "Finds a list of projects by free text search strings. <BR> "
            + "All query parameters are optional and can be combined as needed but at least one parameter has to be submitted.",
            response = ProjectDTO.class,
            responseContainer = "List" )
    @ApiResponses( value = {
            @ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
            @ApiResponse( code = HttpStatus.SC_NO_CONTENT, message = "No content" ),
            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    @Produces( MediaType.APPLICATION_JSON )
    public Response searchProjects(
            @QueryParam( "query" ) @ApiParam( value = "Free text string that should be used to search inside of a project" )
            final String query,
            @QueryParam( "domain" ) @ApiParam( value = "Domain name string a project should be assigned to" ) final String domain,
            @QueryParam( "subdomain" ) @ApiParam( value = "Subdomain name string a project should be assigned to" )
            final String subdomain ) {
        final List<ProjectDTO> projects = this.projectService.find( query, domain, subdomain );

        if ( projects.isEmpty() ) {
            return Response.status( Response.Status.NO_CONTENT ).build();
        }
        return Response.status( Response.Status.OK ).entity( projects ).build();
    }

    /**
     * Create and store a new project in the repository.
     *
     * @param project The project to be stored inside of the Asset Repo
     * @return {#link javax.ws.rs.core.Response Response}
     */
    @POST
    @ApiOperation( value = "Creates and stores a new project in the repository" )
    @ApiResponses( value = {
            @ApiResponse( code = HttpStatus.SC_CREATED, message = "Created (project has been created successfully)" ),
            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    public Response create( @ApiParam( value = "The project to be stored inside of the Asset Repo" ) JsonProject project ) {

        final Project projectEntity = this.projectService.convertJsonToEntity( project );
        final Project created = this.projectService.create( projectEntity );
        final JsonProject jsonProject = this.projectService.convertEntityToJson( created );
        return Response.status( Response.Status.CREATED ).entity( jsonProject ).build();
    }

    /**
     * Update a project in the Asset Repo.
     *
     * @param id      Project ID
     * @param project The project to be updated inside of the Asset Repo
     * @return {#link javax.ws.rs.core.Response HTTP Response code}
     */
    @PUT
    @Path( "{id}" )
    @ApiOperation( value = "Updates a project in the Asset Repo" )
    @ApiResponses( value = {
            @ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
            @ApiResponse( code = HttpStatus.SC_NOT_FOUND, message = "Not found" ),
            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    public Response update( @PathParam( "id" ) @ApiParam( value = "Project ID" ) long id,
                            @ApiParam( value = "The project to be updated inside of the Asset Repo" ) JsonProject project ) {

        if ( null != projectService.find( id ) ) {
            project.setId( id );
            final Project projectEntity = this.projectService.convertJsonToEntity( project );
            final Project updated = this.projectService.update( projectEntity );
            final JsonProject jsonProject = this.projectService.convertEntityToJson( updated );
            return Response.status( Response.Status.OK ).entity( jsonProject ).build();
        }

        return Response.status( Response.Status.NOT_FOUND ).build();
    }

    /**
     * Delete a project from the Asset Repo by ID.
     *
     * @param id ID of the project to be deleted
     */
/**
 @DELETE
 @Path( "{id}" )
 @ApiOperation( value = "Deletes a project from the Asset Repo by ID" )
 @ApiResponses( value = {
 @ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
 @ApiResponse( code = HttpStatus.SC_NOT_FOUND, message = "Not found" ),
 @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
 public Response delete( @PathParam( "id" ) @ApiParam( value = "ID of the project to be deleted" ) long id ) {

 if ( null != projectService.find( id ) ) {
 this.projectService.delete( id );
 return Response.status( Response.Status.OK ).build();
 }
 return Response.status( Response.Status.NOT_FOUND ).build();
 }
 **/
    /**
     * Delete a project from the Asset Repo by name.
     *
     * @param name Name or ID string of the project to be deleted
     */
    @DELETE
    @Path( "{name}" )
    @ApiOperation( value = "Deletes a project from the Asset Repo by name" )
    @ApiResponses( value = {
            @ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
            @ApiResponse( code = HttpStatus.SC_NOT_FOUND, message = "Not found" ),
            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    public Response delete( @PathParam( "name" ) @ApiParam( value = "name of the project to be deleted" ) String name ) {

        Project project = projectService.findByNameOrId( name );
        if ( null != project ) {
            this.projectService.delete( project );
            return Response.status( Response.Status.OK ).build();
        }
        return Response.status( Response.Status.NOT_FOUND ).build();
    }
}
