package eu.scasefp7.assetregistry.rest;

import com.jayway.restassured.response.ValidatableResponse;
import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.data.ArtefactPayload;
import eu.scasefp7.assetregistry.data.PayloadFormat;
import eu.scasefp7.assetregistry.data.PayloadType;
import eu.scasefp7.assetregistry.data.Project;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.jayway.restassured.RestAssured.given;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestDataUtil {

    public static Project createProject() {
        Project project = new Project();
        project.setName( "testproject3" );

        ValidatableResponse body = given().
                                                  contentType( "application/json" ).body( project ).
                                                  when().
                                                  post( ProjectResourceIT.getUrl() ).
                                                  then().
                                                  statusCode( 201 ).
                                                  body( "name", equalTo( project.getName() ) );

        assertThat( body ).isNotNull();
        project.setId( Long.valueOf( body.extract().path( "id" ).toString() ) );
        return project;
    }

    public static Artefact createArtefact( Project project ) throws IOException {
        Artefact artefact = new Artefact();
        artefact.setProjectName( project.getName() );
        artefact.setName( "testartefact" );
        artefact.setDescription( "testdescription" );

        ArtefactPayload artefactPayload = new ArtefactPayload();
        artefactPayload.setFormat( PayloadFormat.JAVA_CODE );
        artefactPayload.setType( PayloadType.TEXTUAL );
        artefactPayload.setName( "source code" );
        URL url = TestDataUtil.class.getResource( "TestDataUtil.class" );
        String src = url.toString().replace( "TestDataUtil.class", "TestDataUtil.java" ).replace(
                "target" + File.separator + "test-classes", "src" + File.separator + "test" + File.separator + "java" );
        artefactPayload.setPayload( IOUtils.toByteArray( new URL( src ) ) );

        artefact.getPayload().add( artefactPayload );

        ValidatableResponse body = given().
                                                  contentType( "application/json" ).body( artefact ).
                                                  when().
                                                  post( ArtefactResourceIT.getUrl() ).
                                                  then().
                                                  statusCode( 201 ).
                                                  body( "name", equalTo( artefact.getName() ) );

        assertThat( body ).isNotNull();
        artefact.setId( Long.valueOf( body.extract().path( "id" ).toString() ) );
        return artefact;
    }

    public static void deleteProject( Project project ) {
        if ( project == null ) {
            return;
        }

        Long id = project.getId();
        if ( id != null ) {
            given().when().delete( ProjectResourceIT.getUrl() + "/" + id ).then().statusCode( 200 );
        }
    }

    public static void deleteArtefact( Artefact artefact ) {
        if ( artefact == null ) {
            return;
        }

        Long id = artefact.getId();
        if ( id != null ) {
            given().when().delete( ArtefactResourceIT.getUrl() + "/" + id ).then().statusCode( 200 );
        }
    }
}
