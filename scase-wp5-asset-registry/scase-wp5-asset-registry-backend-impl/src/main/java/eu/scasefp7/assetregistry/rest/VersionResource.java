package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.service.VersionServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * REST class for getting the version.
 * @author Robert Magnus
 *
 */
@Path( AssetRegistryRestApp.PART_VERSION)
public class VersionResource {

    @Inject
    VersionServiceImpl versionService;


    /**
     * returns the version.
     * @return version
     */
    @GET
    public String version()
    {
        return this.versionService.getVersion();
    }



}
