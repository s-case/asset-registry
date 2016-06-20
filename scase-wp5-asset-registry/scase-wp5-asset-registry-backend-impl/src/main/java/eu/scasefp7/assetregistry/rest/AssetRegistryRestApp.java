package eu.scasefp7.assetregistry.rest;

import eu.scasefp7.assetregistry.util.BuildProperties;
import io.swagger.jaxrs.config.BeanConfig;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * class for holding all the names.
 *
 * @author Robert Magnus
 */
@ApplicationPath(AssetRegistryRestApp.BASE)
public class AssetRegistryRestApp extends Application
{
    private static final Logger LOG = LoggerFactory.getLogger( AssetRegistryRestApp.class );

    public static final String ROOT = "/rest";

    public static final String BASE = "/assetregistry";

    public static final String PART_VERSION = "/version";
    public static final String PART_PROJECT = "/project";
    public static final String PART_ARTEFACT = "/artefact";
    public static final String PART_DOMAIN = "/domain";
    public static final String PART_SUBDOMAIN = "/subdomain";

    // this.getClasses().getClass().getPackage().getName()
    public static final String REST_PACKAGE_NAME = "eu.scasefp7.assetregistry.rest"; 

    /**
     * Configure Swagger.
     * <ul>
     * <li>localhost:8080/s-case/assetregistry/swagger.json
     * <li>localhost:8080/s-case/assetregistry/swagger.yaml
     * </ul>
     * <p>
     * Generate static HTML file documentation using the Swagger Code Generator (https://github.com/swagger-api/swagger-codegen):
     * <ul>
     * <li>git clone git@github.com:swagger-api/swagger-codegen.git
     * <li>export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_51.jdk/Contents/Home
     * <li>cd swagger-codegen
     * <li>mvn package
     * <li>java -jar modules/swagger-codegen-cli/target/swagger-codegen-cli.jar generate -i
     * http://localhost:8080/s-case/assetregistry/swagger.json -l html -o swagger/html
     * </ul>
     * <p>
     * Preferred alternative: bootprint-swagger - a Bootprint module to render swagger specifications
     * <ul>
     * <li>Swagger: http://de.slideshare.net/allendean/a-tour-of-swagger-for-apis</li>
     * <li>bootprint-swagger: https://www.npmjs.com/package/bootprint-swagger</li>
     * <li>https://github.com/nknapp/bootprint-swagger</li>
     * <li>npm install -g bootprint</li>
     * <li>npm install -g bootprint-swagger</li>
     * <li>bootprint swagger http://localhost:8080/s-case/assetregistry/swagger.json scase-swagger</li>
     * </ul>
     */
    public AssetRegistryRestApp()
    {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(BuildProperties.getBuildVersion());
        beanConfig.setSchemes(new String[]{"http", "https"});
        beanConfig.setHost(getHostName());
        beanConfig.setBasePath("/s-case" + BASE);
        beanConfig.setResourcePackage(String.format("io.swagger.resources,%s", REST_PACKAGE_NAME));
        beanConfig.setScan(true);

        beanConfig.setTitle("S-CASE Asset Registry REST API");
        beanConfig.setDescription("Description of " + beanConfig.getTitle());


//        beanConfig.setContact("lars.kuettner@akquinet.de");
//
//        io.swagger.models.Info info = new io.swagger.models.Info();
//        info.setTitle("S-CASE Asset Registry REST API");
//        info.setDescription("The description of the " + info.getTitle());
//        info.setTermsOfService("The terms of service of the " + info.getTitle());
//
//        io.swagger.models.Contact contact = new io.swagger.models.Contact();
//        contact.setName("Robert Magnus");
//        contact.setUrl("http://www.scasefp7.eu");
//        contact.setEmail("robert.magnus@akquinet.de");
//        info.setContact(contact);
//
//        beanConfig.setInfo(info);
//
//        beanConfig.setContact("lars.kuettner@akquinet.de");
    }

    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> resources = new HashSet<Class<?>>();

        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        addRestResourceCLasses(resources);

        return resources;
    }

    private String getHostName() {
        try {
            return InetAddress.getLocalHost().getCanonicalHostName() + ":8080";
        } catch (UnknownHostException e) {
            String msg = "Kann Hostnamen nicht bestimmen.";
            LOG.warn(msg, e); 
            return "localhost:8080";
        }
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
