package eu.scasefp7.assetregistry.service;

import eu.scasefp7.assetregistry.util.BuildProperties;

/**
 * service class for the version.
 *
 * @author Robert Magnus
 */
public class VersionServiceImpl
{
    /**
     * return the build version.
     *
     * @return the version.
     */
    public String getVersion()
    {
        return BuildProperties.getBuildVersion();
    }
}
