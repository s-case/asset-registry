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

import eu.scasefp7.assetregistry.data.Domain;
import eu.scasefp7.assetregistry.service.db.DomainDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * rest api for a domain.
 * @author rmagnus
 *
 */
@Path(AssetRegistryRestApp.PART_DOMAIN)
@Api(value = AssetRegistryRestApp.PART_DOMAIN, description = "provides domains")
@Produces("application/json;charset=UTF-8")
@Consumes("application/json")
@Stateless
public class DomainResource
{
    @EJB
    private DomainDbService service;

    /**
     * Find a {@link eu.scasefp7.assetregistry.data.Domain Domain} by ID in the repository.
     *
     * @param id Domain ID
     * @return {@link eu.scasefp7.assetregistry.data.Domain Domain}
     */
    @GET
    @Path("{id}")
    @ApiOperation(value = "Finds a domain by ID in the repository")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_NO_CONTENT, message = "No content"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Request incorrect"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error")})
    @Produces(MediaType.APPLICATION_JSON)
    public Domain find(@PathParam("id") @ApiParam(value = "domain id") long id)
    {

        Domain domain = this.service.findDomain(id);
        return domain;
    }

    /**
     * Retrieve a list of all {@link eu.scasefp7.assetregistry.data.Domain Domains} in the repository.
     *
     * @return list of all {@link eu.scasefp7.assetregistry.data.Domain Domains}
     */
    @GET
    @Path("domains")
    @ApiOperation(value = "Retrieves a list of all domains in the repository")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_NO_CONTENT, message = "No content"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Request incorrect"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error")})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Domain> findAll()
    {
        return this.service.findAllDomains();
    }
}
