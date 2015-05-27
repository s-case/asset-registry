package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.data.Domain;
import eu.scasefp7.assetregistry.service.db.DomainDbService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path( AssetRegistryRestApp.PART_DOMAIN)
@Produces( "application/json;charset=UTF-8" )
@Consumes("application/json")
@Stateless
public class DomainResource extends Application {

    @EJB
    private DomainDbService service;

    /**
     * Find a {@link eu.scasefp7.assetregistry.data.Domain Domain} by ID inside of the Asset Repository
     * @param id
     * @return {@link eu.scasefp7.assetregistry.data.Domain Domain}
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Domain find( @PathParam("id") long id ) {
        return this.service.findDomain(id);
    }

    /**
     * Retrieve a list of all {@link eu.scasefp7.assetregistry.data.Domain Domains} inside of the Asset Repository
     * @return list of all {@link eu.scasefp7.assetregistry.data.Domain Domains}
     */
    @GET
    @Path("domains")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Domain> findAll() {
        return this.service.findAllDomains();
    }
}
