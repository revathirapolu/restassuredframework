package com.revz.api.config;

import com.revz.api.utils.ProjectUtil;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class BaseTest {

    public static RequestSpecification requestSpec;

    @BeforeClass
    public static void setup() {
        ProjectUtil projectUtil =new ProjectUtil();
        String url = projectUtil.getUrlValue();
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(url);
        builder.setContentType(ContentType.JSON);
        requestSpec = builder.build();
        RestAssured.requestSpecification = requestSpec;
    }

    public static ResponseOptions<Response> getMethod(String url) {
        try {
            return given().spec(requestSpec).when().get(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseOptions<Response> getWithPathParameter(String url, Map<String, String> pathParams) {
        try {
            return given().spec(requestSpec).pathParams(pathParams).when().get(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseOptions<Response> postWithBody(String url, Map<String, String> body)  {
        return given(requestSpec).body(body).log().all().when().post(url);
    }

    public static ResponseOptions<Response> deleteWithPathParams(String url,Map<String, String> pathParams)  {
        return given().spec(requestSpec).pathParams(pathParams).when().delete(url);
    }

}
