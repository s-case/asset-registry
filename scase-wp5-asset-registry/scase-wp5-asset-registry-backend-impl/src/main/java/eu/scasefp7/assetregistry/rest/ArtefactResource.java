package eu.scasefp7.assetregistry.rest;


import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.dto.ArtefactDTO;
import eu.scasefp7.assetregistry.dto.JsonArtefact;
import eu.scasefp7.assetregistry.service.ArtefactService;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URISyntaxException;
import java.util.List;

import static eu.scasefp7.assetregistry.rest.ResourceTools.redirect;

@Path( AssetRegistryRestApp.PART_ARTEFACT)
@Produces( "application/json;charset=UTF-8" )
@Consumes("application/json")
@Stateless
public class ArtefactResource {

    private static final Logger LOG = LoggerFactory.getLogger(ArtefactResource.class);

    @EJB
    private ArtefactService artefactService;

    @EJB
    private ProjectService projectService;


    /**
     * Find an artefact in the repository by ID
     * @param id
     * @return Artefact artefact
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArtefact get( @PathParam("id") long id ) {
        final Artefact artefactEntity = this.artefactService.find( id );
        final JsonArtefact jsonArtefact = artefactService.convertEntityToJson(artefactEntity);
        return jsonArtefact;
    }

    /**
     * Find an artefact in the repository by ID
     * @param id
     * @return boolean bool
     */
    @GET
    @Path("exists/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean exists( @PathParam("id") long id ) {
        if(null!=this.artefactService.find(id)) {
            return true;
        }
        return false;
    }

    /**
     *Find a list of artefacts by search query
     * @param query
     * @return List<Artefact> artefacts
     */
    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ArtefactDTO> searchArtefacts(@QueryParam("q") final String query) {
        LOG.info("search '{}'", query);
        final List<ArtefactDTO> artefacts = artefactService.find(query);
        return artefacts;
    }

    /**
     * Create and store a new artefact in the repository
     * @param artefact
     * @return
     * @throws URISyntaxException
     */
    @POST
    public Response create(JsonArtefact artefact) throws URISyntaxException {

        final Project project = this.projectService.findByName(artefact.getProjectName());
        if(null== project) {
            return Response.status(Response.Status.NOT_FOUND).entity("Project with ID" + artefact.getProjectName() + " could not be found inside of the Artefact Repository").build();

        }
            final Artefact artefactEntity = artefactService.convertJsonToEntity(artefact);
            final Artefact created = this.artefactService.create( artefactEntity );
            project.getArtefacts().add(created);
            projectService.update(project);

            return redirect("artefact/" + created.getId());
    }

    /**
     * Update an artefact in the repository
     * @param id
     * @param artefact
     * @return
     * @throws URISyntaxException
     */
    @PUT
    @Path("{id}")
    public Response update( @PathParam("id") long id, JsonArtefact artefact ) throws URISyntaxException {
        artefact.setId( id );
        final Artefact artefactEntity = artefactService.convertJsonToEntity(artefact);
        final Artefact updated = this.artefactService.update( artefactEntity );
        return redirect( "artefact/", updated );
    }

    /**
     * Delete an artefact from the repository
     * @param id
     */
    @DELETE
    @Path("{id}")
    public void delete( @PathParam("id") long id ) {
        this.artefactService.delete(id);
    }

}
