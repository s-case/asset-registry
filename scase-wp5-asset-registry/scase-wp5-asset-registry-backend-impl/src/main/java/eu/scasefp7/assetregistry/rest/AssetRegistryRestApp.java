package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.util.BuildProperties;
import io.swagger.jaxrs.config.BeanConfig;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * class for holding all the names.
 *
 * @author Robert Magnus
 */
@ApplicationPath(AssetRegistryRestApp.BASE)
public class AssetRegistryRestApp extends Application
{
    public static final String ROOT = "/rest";

    public static final String BASE = "/assetregistry";

    public static final String PART_VERSION = "/version";
    public static final String PART_PROJECT = "/project";
    public static final String PART_ARTEFACT = "/artefact";
    public static final String PART_DOMAIN = "/domain";
    public static final String PART_SUBDOMAIN = "/subdomain";

    public static final String REST_PACKAGE_NAME = "eu.scasefp7.assetregistry.rest";  // this.getClasses().getClass().getPackage().getName()

    // localhost:8080/s-case/assetregistry/swagger.json
    // localhost:8080/s-case/assetregistry/swagger.yaml
    public AssetRegistryRestApp()
    {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(BuildProperties.getBuildVersion());
        beanConfig.setSchemes(new String[]{"http", "https"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath(BASE);
        beanConfig.setResourcePackage(String.format("io.swagger.resources,%s", REST_PACKAGE_NAME));

        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> resources = new HashSet();

        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        addRestResourceCLasses(resources);

        return resources;
    }

    private void addRestResourceCLasses(Set<Class<?>> resources)
    {
//        resources.add(eu.scasefp7.assetregistry.rest.ArtefactResource.class);
//        resources.add(eu.scasefp7.assetregistry.rest.DomainResource.class);
//        resources.add(eu.scasefp7.assetregistry.rest.ProjectResource.class);
//        resources.add(eu.scasefp7.assetregistry.rest.SubDomainResource.class);
//        resources.add(eu.scasefp7.assetregistry.rest.VersionResource.class);

        // Add all classes in the rest package ending in Resource
        final Reflections reflections = new Reflections(REST_PACKAGE_NAME, new SubTypesScanner(false));
        final Set<Class<?>> allRestPackageClasses = reflections.getSubTypesOf(Object.class);

        for (final Class<?> clazz : allRestPackageClasses) {
            if (clazz.getName().endsWith("Resource")) {
                resources.add(clazz);
            }
        }
    }
}
