package eu.scasefp7.assetregistry.rest;

import static com.jayway.restassured.RestAssured.given;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.response.ValidatableResponse;

import eu.scasefp7.assetregistry.data.Project;

public class ProjectResourceIT
{
    
    private static Logger LOG = LoggerFactory.getLogger(ProjectResourceIT.class);
    
    private Project project;
    
    @Before
    public void createProject()
    {
        project = new Project();
        project.setName("testproject");
        
        ValidatableResponse body = given().
            contentType("application/json").body(project).
        when().
            post(getUrl()).
        then().
            statusCode(200).
            body("name", equalTo(project.getName()));
        
        assertThat(body).isNotNull();
        project.setId(Long.valueOf(body.extract().path("id").toString()));
    }
    
    @After
    public void deleteProject()
    {
        if (project == null) { return; }
        
        Long id = project.getId();
        if (id != null) {
            given().when().delete(getUrl() + "/" + id).then().statusCode(204);
        }
    }
    
    @Test
    public void canCreateAndGetProject()
    {
        Long id = project.getId();
        
        assertThat(id).isNotNull();
        
        given().
        when().
            get(getUrl() + "/" + id).
        then().
            statusCode(200).
            body("name", equalTo(project.getName())).
            body("id", notNullValue());
    }
    
    @Test
    public void canDeleteByName()
    {
        given().when().delete(getUrl() + "/" + project.getName()).then().statusCode(204);
        project = null;
    }

    @Test
    public void canSearchByName() throws InterruptedException
    {
        // wait 5 seconds for lucene indexing
        Thread.sleep(5 * 1000);
        String query = "{\"term\" : {\"name\" : \"" + project.getName() + "\"}}";
        ValidatableResponse response = 
                given().
                    param("query" , query).                 
                when().
                    get(getUrl() + "/" + "search").
                then().statusCode(200);
        
        LOG.debug("response: {}", response.extract().asString());
               
        response.
            body("project.name[0]", equalTo(project.getName())).
            body("project.id[0]", equalTo(Integer.valueOf(project.getId().toString()))).
            body("score[0]", notNullValue());
        
    }

    protected String getUrl()
    {
        return "http://localhost:8080/s-case/assetregistry" + AssetRegistryRestApp.PART_PROJECT;
    }
}
