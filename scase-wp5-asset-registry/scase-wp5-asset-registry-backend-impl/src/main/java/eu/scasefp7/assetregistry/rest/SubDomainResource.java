package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.data.SubDomain;
import eu.scasefp7.assetregistry.service.db.DomainDbService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path( AssetRegistryRestApp.PART_SUBDOMAIN)
@Produces( "application/json;charset=UTF-8" )
@Consumes("application/json")
@Stateless
public class SubDomainResource {

    @EJB
    private DomainDbService service;

    /**
     * Find a {@link eu.scasefp7.assetregistry.data.SubDomain SubDomain} by ID inside of the Asset Repository
     * @param id
     * @return {@link eu.scasefp7.assetregistry.data.SubDomain SubDomain}
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public SubDomain find( @PathParam("id") long id ) {
        return this.service.findSubDomain(id);
    }

    /**
     * Retrieve a list of all {@link eu.scasefp7.assetregistry.data.SubDomain SubDomains} inside of the Asset Repository
     * @return list of all {@link eu.scasefp7.assetregistry.data.SubDomain SubDomains}
     */
    @GET
    @Path("subdomains")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SubDomain> findAll() {
        return this.service.findAllSubDomains();
    }
}
