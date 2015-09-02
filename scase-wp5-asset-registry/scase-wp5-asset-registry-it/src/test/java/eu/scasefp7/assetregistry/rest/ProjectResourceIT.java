package eu.scasefp7.assetregistry.rest;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import eu.scasefp7.assetregistry.data.Project;

public class ProjectResourceIT
{
    @Test
    public void canCreateProject()
    {
        Project project = new Project();
        project.setName("testproject");

        given().
            contentType("application/json").body(project).
        when().
            post(getUrl()).
        then().
            statusCode(200).
            body("lotto.lottoId", notNullValue());

    }

    protected String getUrl()
    {
        return "http://localhost:8080/s-case/assetregistry" + AssetRegistryRestApp.PART_PROJECT;
    }
}
