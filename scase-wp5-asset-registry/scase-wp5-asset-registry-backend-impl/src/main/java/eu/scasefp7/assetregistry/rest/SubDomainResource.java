package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.data.SubDomain;
import eu.scasefp7.assetregistry.service.db.DomainDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

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
     * Find a {@link eu.scasefp7.assetregistry.data.SubDomain SubDomain} by ID in the Asset Repository
     *
     * @param id
     * @return {@link eu.scasefp7.assetregistry.data.SubDomain SubDomain}
     */
    @GET
    @Path("{id}")
    @ApiOperation(value = "Finds a subdomain by ID in the Asset Repository")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 400, message = "Request incorrect"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Internal Server error")})
    @Produces(MediaType.APPLICATION_JSON)
    public SubDomain find(@PathParam("id") @ApiParam(value = "subdomain ID") long id)
    {
        return this.service.findSubDomain(id);
    }

    /**
     * Retrieve a list of all {@link eu.scasefp7.assetregistry.data.SubDomain SubDomains} in the Asset Repository
     *
     * @return list of all {@link eu.scasefp7.assetregistry.data.SubDomain SubDomains}
     */
    @GET
    @Path("subdomains")
    @ApiOperation(value = "Retrieves a list of all subdomains in the Asset Repository")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 400, message = "Request incorrect"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Internal Server error")})
    @Produces(MediaType.APPLICATION_JSON)
    public List<SubDomain> findAll()
    {
        return this.service.findAllSubDomains();
    }
}
