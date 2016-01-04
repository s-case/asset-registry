package eu.scasefp7.assetregistry.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;

import eu.scasefp7.assetregistry.data.SubDomain;
import eu.scasefp7.assetregistry.service.db.DomainDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * rest api for sud domain.
 * @author rmagnus
 *
 */
@Path(AssetRegistryRestApp.PART_SUBDOMAIN)
@Api(value = AssetRegistryRestApp.PART_SUBDOMAIN, description = "provides subdomains")
@Produces("application/json;charset=UTF-8")
@Consumes("application/json")
@Stateless
public class SubDomainResource
{
    @EJB
    private DomainDbService service;

    /**
     * Find a {@link eu.scasefp7.assetregistry.data.SubDomain SubDomain} by ID in the Asset Repository.
     *
     * @param id the id
     * @return {@link eu.scasefp7.assetregistry.data.SubDomain SubDomain}
     */
    @GET
    @Path("{id}")
    @ApiOperation(value = "Finds a subdomain by ID in the Asset Repository")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_NO_CONTENT, message = "No content"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Request incorrect"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error")})
    @Produces(MediaType.APPLICATION_JSON)
    public SubDomain find(@PathParam("id") @ApiParam(value = "subdomain ID") long id)
    {
        return this.service.findSubDomain(id);
    }

    /**
     * Retrieve a list of all {@link eu.scasefp7.assetregistry.data.SubDomain SubDomains} in the Asset Repository.
     *
     * @return list of all {@link eu.scasefp7.assetregistry.data.SubDomain SubDomains}
     */
    @GET
    @Path("subdomains")
    @ApiOperation(value = "Retrieves a list of all subdomains in the Asset Repository")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_NO_CONTENT, message = "No content"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Request incorrect"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error")})
    @Produces(MediaType.APPLICATION_JSON)
    public List<SubDomain> findAll()
    {
        return this.service.findAllSubDomains();
    }
}
