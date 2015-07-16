package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.service.VersionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * REST class for getting the version.
 *
 * @author Robert Magnus
 */
@Path(AssetRegistryRestApp.PART_VERSION)
@Api(value = AssetRegistryRestApp.PART_VERSION, description = "provides version info")
public class VersionResource
{
    @Inject
    private VersionServiceImpl versionService;

    /**
     * Return the version number
     *
     * @return version
     */
    @GET
    @ApiOperation(value = "Returns the version number")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 400, message = "Request incorrect"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Internal Server error")})
    public String version()
    {
        return this.versionService.getVersion();
    }
}
