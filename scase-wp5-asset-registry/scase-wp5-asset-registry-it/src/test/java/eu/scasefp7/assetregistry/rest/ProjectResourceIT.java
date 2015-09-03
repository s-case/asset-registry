package eu.scasefp7.assetregistry.rest;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.jayway.restassured.response.ValidatableResponse;

import eu.scasefp7.assetregistry.data.Project;

public class ProjectResourceIT
{
    @Test
    public void canCreateProject()
    {
        Project project = new Project();
        project.setName("testproject1");

        getBody(project);
    }

    @Test
    public void canCreateAndGetProject()
    {
        Project project = new Project();
        project.setName("testproject2");

        Integer id = getBody(project).extract().path("id");
    }

    private ValidatableResponse getBody(Project project)
    {
        return given().
            contentType("application/json").body(project).
        when().
            post(getUrl()).
        then().
            statusCode(200).
            body("name", equalTo(project.getName()));
    }

    protected String getUrl()
    {
        return "http://localhost:8080/s-case/assetregistry" + AssetRegistryRestApp.PART_PROJECT;
    }
}
