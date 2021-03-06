package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.data.BaseEntity;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Response Helpers.
 */
public final class ResourceTools {

    private ResourceTools(){}

    /**
     * Build a redirect URI.
     * @param to the uri 
     * @return the redirect
     */
    public static Response redirect(final String to){
        try {
            return Response.seeOther( new URI( to ) ).build();
        } catch ( URISyntaxException e ) {
            throw new RuntimeException( "failed to build uri", e );
        }
    }

    /**
     * Build a redirect URI like path + entity.getId().
     * @param path the path 
     * @param entity the entity 
     * @return  URI
     */
    public static Response redirect(final String path, final BaseEntity entity){
        return redirect( path + entity.getId() );
    }
}