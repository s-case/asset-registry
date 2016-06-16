package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.dto.ArtefactDTO;
import eu.scasefp7.assetregistry.dto.JsonArtefact;
import eu.scasefp7.assetregistry.service.ArtefactService;
import eu.scasefp7.assetregistry.service.ProjectService;
import eu.scasefp7.assetregistry.service.db.DomainDbService;
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
 * rest api for artefact resource.
 *
 * @author rmagnus
 */
@Path( AssetRegistryRestApp.PART_ARTEFACT )
@Api( value = AssetRegistryRestApp.PART_ARTEFACT, description = "provides artefacts" )
@Produces( "application/json;charset=UTF-8" )
@Consumes( "application/json" )
@Stateless
public class ArtefactResource {

    private static final Logger LOG = LoggerFactory.getLogger( ArtefactResource.class );

    @EJB
    private ArtefactService artefactService;

    @EJB
    private ProjectService projectService;

    @EJB
    private DomainDbService domainService;

    /**
     * Find an artefact in the repository by ID.
     *
     * @param id Artefact ID
     * @return Artefact artefact
     */
    @GET
    @Path( "{id}" )
    @ApiOperation( value = "Finds an artefact in the repository by ID",
            response = JsonArtefact.class )
    @ApiResponses( value = {@ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
                            @ApiResponse( code = HttpStatus.SC_NO_CONTENT, message = "No content" ),
                            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    @Produces( MediaType.APPLICATION_JSON )
    public Response get( @PathParam( "id" ) @ApiParam( value = "Artefact ID" ) long id ) {
        final Artefact artefactEntity = this.artefactService.find( id );
        if ( null != artefactEntity ) {
            final JsonArtefact jsonArtefact = this.artefactService.convertEntityToJson( artefactEntity );
            return Response.status( Response.Status.OK ).entity( jsonArtefact ).build();
        }
        return Response.status( Response.Status.NO_CONTENT ).build();
    }

    /**
     * Checks whether an artefact exists in the repository.
     *
     * @param id Artefact ID
     * @return OK  if artefact does exist, otherwise NOT_FOUND
     */
    @GET
    @ApiOperation( value = "Checks if an artefact does exist in the repository", response = JsonArtefact.class )
    @ApiResponses( value = {@ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
                            @ApiResponse( code = HttpStatus.SC_NOT_FOUND, message = "Not found" ),
                            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    @Path( "exists/{id}" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response exists( @PathParam( "id" ) @ApiParam( value = "Artefact ID" ) long id ) {
        Artefact artefact = this.artefactService.find( id );

        if ( null != artefact ) {
            return Response.status( Response.Status.OK ).entity( artefactService.convertEntityToJson( artefact ) ).build();
        }
        return Response.status( Response.Status.NOT_FOUND ).build();
    }

    /**
     * Find a list of artefacts by search query.
     *
     * @param query Elastic Search query string
     * @return List<ArtefactDTO> artefacts
     */
    @GET
    @Path( "directsearch" )
    @ApiOperation( value = "Finds a list of artefacts by search query. <BR>"
            + "The search query has to be submitted in the Elastic Search JSON search syntax.",
            response = ArtefactDTO.class, responseContainer = "List" )
    @ApiResponses( value = {@ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
                            @ApiResponse( code = HttpStatus.SC_NO_CONTENT, message = "No content" ),
                            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    @Produces( MediaType.APPLICATION_JSON )
    public Response searchArtefacts(
            @QueryParam( "q" ) @ApiParam( value = "Search query in the Elastic Search JSON syntax" ) final String query ) {
        LOG.info( "search '{}'", query );
        final List<ArtefactDTO> artefacts = this.artefactService.find( query );
        if ( artefacts.isEmpty() ) {
            return Response.status( Response.Status.NO_CONTENT ).build();
        }
        return Response.status( Response.Status.OK ).entity( artefacts ).build();
    }

    /**
     * Find a list of artefacts by free text search strings.
     *
     * @param query        the query
     * @param domain       the domain
     * @param subdomain    the subdomain
     * @param artefacttype the artefact type
     * @return List<ArtefactDTO> artefacts
     */
    @GET
    @Path( "search" )
    @ApiOperation( value = "Finds a list of artefacts by free text search strings. <BR>"
            + "All query parameters are optional and can be combined as needed but "
            + "at least one parameter has to be submitted.",
            response = ArtefactDTO.class, responseContainer = "List" )
    @ApiResponses( value = {@ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
                            @ApiResponse( code = HttpStatus.SC_NO_CONTENT, message = "No content" ),
                            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    @Produces( MediaType.APPLICATION_JSON )
    public Response searchArtefacts(
            @QueryParam( "query" )
            @ApiParam( value = "Free text string that should be used to search inside of an artefact" ) final String query,
            @QueryParam( "domain" )
            @ApiParam( value = "Domain name string an artefact should be assigned to" ) final String domain,
            @QueryParam( "subdomain" )
            @ApiParam( value = "Subdomain name string an artefact should be assigned to" ) final String subdomain,
            @QueryParam( "type" )
            @ApiParam( value = "The Artefact Type of an artefact (e.g. USE_CASE)" ) final String artefacttype ) {
        final List<ArtefactDTO> artefacts = this.artefactService.find( query, domain, subdomain, artefacttype );
        if ( artefacts.isEmpty() ) {
            return Response.status( Response.Status.NO_CONTENT ).build();
        }
        return Response.status( Response.Status.OK ).entity( artefacts ).build();
    }

    /**
     * Create and store a new artefact in the repository.
     *
     * @param artefact An artefact to be stored inside of the Asset Repo
     * @return HTTP Response code
     */
    @POST
    @ApiOperation( value = "Creates and stores a new artefact in the repository" )
    @ApiResponses( value = {
            @ApiResponse( code = HttpStatus.SC_CREATED, message = "Created (artefact has been created successfully)" ),
            @ApiResponse( code = HttpStatus.SC_NOT_FOUND, message = "Not found" ),
            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    public Response create( @ApiParam( value = "Artefact to be stored in the Asset Repo" ) JsonArtefact artefact ) {
        final Project project = this.projectService.findByNameOrId( artefact.getProjectName() );

        if ( null == project ) {
            return Response
                    .status( Response.Status.NOT_FOUND )
                    .entity( "Project with ID" + artefact.getProjectName()
                                     + " could not be found inside of the Artefact Repository" ).build();
        }
        final Artefact artefactEntity = this.artefactService.convertJsonToEntity( artefact );
        final Artefact created = this.artefactService.create( artefactEntity );
        project.getArtefacts().add( created );
        this.projectService.update( project );

        return Response.status( Response.Status.CREATED ).entity( artefactService.convertEntityToJson( created ) ).build();
    }

    /**
     * Update an artefact in the repository.
     *
     * @param id       Artefact ID
     * @param artefact The artefact to be updated
     * @return HTTP Response Code
     */
    @PUT
    @Path( "{id}" )
    @ApiOperation( value = "Updates an artefact in the repository",
            response = JsonArtefact.class )
    @ApiResponses( value = {@ApiResponse( code = HttpStatus.SC_OK, message = "OK, artefact has been updated" ),
                            @ApiResponse( code = HttpStatus.SC_NOT_FOUND, message = "Not found" ),
                            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    public Response update( @PathParam( "id" ) @ApiParam( value = "artefact id" ) long id,
                            @ApiParam( value = "The artefact to be updated" ) JsonArtefact artefact ) {

        if ( null != artefactService.find( id ) ) {
            artefact.setId( id );
            final Artefact artefactEntity = this.artefactService.convertJsonToEntity( artefact );
            final Artefact updated = this.artefactService.update( artefactEntity );
            return Response.status( Response.Status.OK ).entity( artefactService.convertEntityToJson( updated ) ).build();
        }
        return Response.status( Response.Status.NOT_FOUND ).build();
    }

    /**
     * Delete an artefact from the repository.
     *
     * @param id Artefact ID of the artefact that shall be deleted from the
     *           Asset Repo
     */
    @DELETE
    @Path( "{id}" )
    @ApiOperation( value = "Deletes an artefact from the repository" )
    @ApiResponses( value = {@ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
                            @ApiResponse( code = HttpStatus.SC_NOT_FOUND, message = "Not found" ),
                            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    public Response delete(
            @PathParam( "id" )
            @ApiParam( value = "Artefact ID of the artefact that shall be deleted from the Asset Repo" ) long id ) {
        if ( null != artefactService.find( id ) ) {
            this.artefactService.delete( id );
            return Response.status( Response.Status.OK ).build();
        }
        return Response.status( Response.Status.NOT_FOUND ).build();
    }
}
