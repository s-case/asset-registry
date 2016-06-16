package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.data.SubDomain;
import eu.scasefp7.assetregistry.service.db.DomainDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.http.HttpStatus;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * rest api for sud domain.
 *
 * @author rmagnus
 */
@Path( AssetRegistryRestApp.PART_SUBDOMAIN )
@Api( value = AssetRegistryRestApp.PART_SUBDOMAIN, description = "provides subdomains" )
@Produces( "application/json;charset=UTF-8" )
@Consumes( "application/json" )
@Stateless
public class SubDomainResource {

    @EJB
    private DomainDbService service;

    /**
     * Find a {@link eu.scasefp7.assetregistry.data.SubDomain SubDomain} by ID in the Asset Repository.
     *
     * @param id the id
     * @return {@link eu.scasefp7.assetregistry.data.SubDomain SubDomain}
     */
    @GET
    @Path( "{id}" )
    @ApiOperation( value = "Finds a subdomain by ID in the Asset Repository", response = SubDomain.class )
    @ApiResponses( value = {
            @ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
            @ApiResponse( code = HttpStatus.SC_NOT_FOUND, message = "Not found" ),
            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    @Produces( MediaType.APPLICATION_JSON )
    public Response find( @PathParam( "id" ) @ApiParam( value = "subdomain ID" ) long id ) {
        SubDomain subDomain = this.service.findSubDomain( id );
        if ( null != subDomain ) {
            return Response.status( Response.Status.OK ).entity( subDomain ).build();
        }
        return Response.status( Response.Status.NOT_FOUND ).build();
    }

    /**
     * Retrieve a list of all {@link eu.scasefp7.assetregistry.data.SubDomain SubDomains} in the Asset Repository.
     *
     * @return list of all {@link eu.scasefp7.assetregistry.data.SubDomain SubDomains}
     */
    @GET
    @Path( "subdomains" )
    @ApiOperation( value = "Retrieves a list of all subdomains in the Asset Repository", response = SubDomain.class, responseContainer = "List" )
    @ApiResponses( value = {
            @ApiResponse( code = HttpStatus.SC_OK, message = "OK" ),
            @ApiResponse( code = HttpStatus.SC_NO_CONTENT, message = "No content" ),
            @ApiResponse( code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error" )} )
    @Produces( MediaType.APPLICATION_JSON )
    public Response findAll() {

        List<SubDomain> subDomains = this.service.findAllSubDomains();
        if ( subDomains.isEmpty() ) {
            return Response.status( Response.Status.NO_CONTENT ).build();
        }

        return Response.status( Response.Status.OK ).entity( subDomains ).build();
    }
}
