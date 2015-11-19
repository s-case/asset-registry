package eu.scasefp7.assetregistry.rest;

import static com.jayway.restassured.RestAssured.given;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.data.Project;

public class ArtefactResourceIT
{

    private Project project;
    private Artefact artefact;

    @Before
    public void createProject() throws IOException
    {
        project = TestDataUtil.createProject();
        artefact = TestDataUtil.createArtefact(project);
    }

    @After
    public void deleteProject()
    {
        TestDataUtil.deleteArtefact(artefact);
        TestDataUtil.deleteProject(project);
    }

    @Test
    public void canCreateAndGet()
    {
        Long id = artefact.getId();
        
        assertThat(id).isNotNull();
        
        given().
        when().
            get(getUrl() + "/" + id).
        then().
            statusCode(200).
            body("name", equalTo(artefact.getName())).
            body("id", notNullValue());
    }

    @Test
    public void checkExisting()
    {
        Long id = artefact.getId();
        
        assertThat(id).isNotNull();
        
        given().
        when().
            get(getUrl() + "/exists/" + id).
        then().
            statusCode(200).
            body(equalTo("true"));
    }

    @Test
    public void checkNonExisting()
    {
        given().
        when().
            get(getUrl() + "/exists/-1").
        then().
            statusCode(200).
            body(equalTo("false"));
    }

    @Test
    public void canUpdate()
    {
        com.jayway.restassured.response.Response response = given().when().get(getUrl() + "/" + artefact.getId());
        response.then().statusCode(200);
        Artefact artefact = response.as(Artefact.class);
        String newName = artefact.getName()+"1";
        artefact.setName(newName);
        
        given().when().contentType("application/json").body(artefact).put(getUrl() + "/" + artefact.getId()).then().statusCode(200);
        
        given().when().get(getUrl() + "/" + artefact.getId()).then().statusCode(200).body("name",
                equalTo(newName));
        
    }

    public static String getUrl()
    {
        return "http://localhost:8080/s-case/assetregistry" + AssetRegistryRestApp.PART_ARTEFACT;
    }

}
