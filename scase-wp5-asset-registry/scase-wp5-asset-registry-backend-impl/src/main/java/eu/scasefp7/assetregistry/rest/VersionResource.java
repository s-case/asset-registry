package eu.scasefp7.assetregistry.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.http.HttpStatus;

import eu.scasefp7.assetregistry.service.VersionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
     * Return the version number.
     *
     * @return version
     */
    @GET
    @ApiOperation(value = "Returns the version number")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_NO_CONTENT, message = "No content"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Request incorrect"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server error")})
    public String version()
    {
        return this.versionService.getVersion();
    }
}
