package tests.rest;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import org.example.dto.Project;
import org.example.utils.PropertiesLoader;
import org.testng.annotations.Test;

import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class QaseRestTest {
    String projectCode;

    //LIST USERS
    @Test(priority = 0)
    public void getListUsers() {
        Properties properties = PropertiesLoader.loadProperties();
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                headers("Token", properties.getProperty("token")).
                log().ifValidationFails().
        when().
                get("https://api.qase.io/v1/project").
        then().
                statusCode(200).
                body("result", not(empty()),
                        "status", equalTo(true));
    }

    // POST/CreateProject
    @Test(priority = 1)
    public void createProject() {
        Properties properties = PropertiesLoader.loadProperties();
        Faker faker = new Faker();
        projectCode = faker.code().asin();
        Project expectedProject = Project.builder()
                .title("morpheus")
                .code(projectCode)
                .description("Test project")
                .build();

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                headers("Token", properties.getProperty("token")).
                body(expectedProject).
                log().ifValidationFails().
        when().
                post("https://api.qase.io/v1/project").
        then().
                log().ifValidationFails().
                statusCode(200).
                body("result", not(empty()),
                        "status", equalTo(true),
                        "result.code", equalTo(projectCode));
    }

    // Get Project by code
    @Test(priority = 2)
    public void getProjectByCode() {
        Properties properties = PropertiesLoader.loadProperties();
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                headers("Token", properties.getProperty("token")).
                log().ifValidationFails().
        when().
                get("https://api.qase.io/v1/project/" + projectCode).
        then().
                log().ifValidationFails().
                statusCode(200).
                body("result", not(empty()),
                        "status", equalTo(true),
                        "result.code", equalTo(projectCode));
    }

    // Delete Project by code
    @Test(priority = 3)
    public void deleteProjectByCode() {
        Properties properties = PropertiesLoader.loadProperties();
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                headers("Token", properties.getProperty("token")).
                log().ifValidationFails().
        when().
                delete("https://api.qase.io/v1/project/" + projectCode).
        then().
                log().ifValidationFails().
                statusCode(200).
                body("status", not(empty()),
                        "status", equalTo(true));
    }
}