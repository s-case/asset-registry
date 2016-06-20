package eu.scasefp7.assetregistry.rest;

import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import eu.scasefp7.assetregistry.data.Project;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ProjectResourceIT {

    private static Logger LOG = LoggerFactory.getLogger( ProjectResourceIT.class );

    private Project project;
//  private Artefact artefact;

    @Before
    public void createProject() throws IOException {
        this.project = TestDataUtil.createProject();
//      artefact = TestDataUtil.createArtefact(project);
    }

    @After
    public void deleteProject() {
//      TestDataUtil.deleteArtefact(artefact);
        TestDataUtil.deleteProject( this.project );
    }

    @Test
    public void canCreateAndGet() {
        Long id = this.project.getId();

        assertThat( id ).isNotNull();

        given().
                       when().
                       get( getUrl() + "/" + id ).
                       then().
                       statusCode( 200 ).
                       body( "name", equalTo( this.project.getName() ) ).
                       body( "id", notNullValue() );
    }

    @Test
    public void canUpdate() {
        com.jayway.restassured.response.Response response = given().when().get( getUrl() + "/" + this.project.getId() );
        response.then().statusCode( 200 );
        Project project = response.as( Project.class );
        String newName = project.getName() + "-" + UUID.randomUUID().toString();
        project.setName( newName );

        given().when().contentType( "application/json" ).body( project ).put( getUrl() + "/" + project.getId() ).then().statusCode( 200 );

        given().when().get( getUrl() + "/" + project.getId() ).then().statusCode( 200 ).body( "name",
                                                                                              equalTo( newName ) );
    }

    @Test
    public void canDeleteByName() {
        given().when().delete( getUrl() + "/" + this.project.getName() ).then().statusCode( 200 );
        this.project = null;
//      artefact = null;
    }

    @Test
    @Ignore( "Exception mapper doesn't work" )
    public void canDeleteNonExistingByName() {
        ValidatableResponse then = given().when().delete( getUrl() + "/" + this.project.getName() + "1" ).then();
        then.statusCode( Response.Status.NOT_FOUND.ordinal() );
        then.statusLine( containsString( "The entity was not found." ) );
    }

    @Test
    public void canSearchDirect() throws InterruptedException {
        // wait 5 seconds for lucene indexing
        Thread.sleep( 5 * 1000 );
        String query = "{\"term\" : {\"name\" : \"" + this.project.getName() + "\"}}";

        RequestSpecification when = given().param( "q", query ).when();

//        await().until(() -> when.get(getUrl() + "/" + "directsearch").then().statusCode(200));

        ValidatableResponse response =
                when.
                            get( getUrl() + "/" + "directsearch" ).
                            then().statusCode( 200 );

        LOG.debug( "response: {}", response.extract().asString() );

        response.
                        body( "project.name[0]", equalTo( this.project.getName() ) ).
                        body( "project.id[0]", equalTo( Integer.valueOf( this.project.getId().toString() ) ) ).
                        body( "score[0]", notNullValue() );
    }

    @Test
    public void canSearchWithDomains() throws InterruptedException {
        // wait 5 seconds for lucene indexing
        Thread.sleep( 5 * 1000 );
        String query = "{\"term\" : {\"name\" : \"" + this.project.getName() + "\"}}";
        ValidatableResponse response =
                given().
                               param( "query", query ).
                               when().
                               get( getUrl() + "/" + "search" ).
                               then().statusCode( 200 );

        LOG.debug( "response: {}", response.extract().asString() );

        response.
                        body( "project.name[0]", equalTo( this.project.getName() ) ).
                        body( "project.id[0]", equalTo( Integer.valueOf( this.project.getId().toString() ) ) ).
                        body( "score[0]", notNullValue() );
    }

    public static String getUrl() {
        return "http://localhost:8080/s-case/assetregistry" + AssetRegistryRestApp.PART_PROJECT;
    }
}

