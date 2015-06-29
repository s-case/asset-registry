package eu.scasefp7.assetregistry.util;

import junit.framework.TestCase;

import static org.fest.assertions.api.Assertions.assertThat;

public class BuildPropertiesTest extends TestCase
{
    public void testGetBuildVersion() throws Exception
    {
        String version = BuildProperties.getBuildVersion();

        assertThat(version).isNotNull().isNotEmpty();
    }

    public void testGetBuildTimestamp() throws Exception
    {
        String timestamp = BuildProperties.getBuildTimestamp();

        assertThat(timestamp).isNotNull().isNotEmpty();
    }
}