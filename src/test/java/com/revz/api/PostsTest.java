package com.revz.api;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.revz.api.config.BaseTest;
import com.revz.api.config.EndPoint;
import com.revz.api.entities.Post;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.emptyString;

public class PostsTest extends BaseTest {
    public static ResponseOptions<Response> responseOptions;

    @Test
    public void validateGetAllPosts() {
        responseOptions = getMethod(EndPoint.POSTS); // get the posts

        String contentType = responseOptions.getContentType();

        assertThat(contentType, containsStringIgnoringCase("application/json"));
        assertThat(contentType, containsStringIgnoringCase("charset=utf-8"));
        assertThat("Status code not OK ", responseOptions.getStatusCode(), is(200));
        assertThat(responseOptions.getBody().asString(), is(not(emptyString())));

        Post[] posts = responseOptions.getBody().as(Post[].class, ObjectMapperType.GSON);
        assertThat(posts.length, is(100));
    }


    @Test
    public void validateGetSinglePost() {
        Map<String, String> pathParamMap = new HashMap<>();
        pathParamMap.put("postId", "5");
        responseOptions = getWithPathParameter(EndPoint.POSTS + "/{postId}", pathParamMap);
        Post post = responseOptions.getBody().as(Post.class);

        String id = post.getId();

        assertThat(id, is("5"));
        assertThat("Status code not OK ", responseOptions.getStatusCode(), is(200));
    }

    @Test
    public void validatePostSchemaJSON() {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(ValidationConfiguration.newBuilder()
                        .setDefaultVersion(SchemaVersion.DRAFTV4).freeze()).freeze();
        given().pathParam("postId", 5).
                when().get(EndPoint.POSTS + "/{postId}").
                then().assertThat().
                body(matchesJsonSchemaInClasspath("post.json").using(jsonSchemaFactory));
    }

    @Test
    public void validateCreateNewPost() {
        String line1 = "Think this weather app might be broken";
        String line2 = "It's meant to get even hotter in the weekend, optimistic?";

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("userId", "11");
        bodyMap.put("title", "Warm as in November");
        bodyMap.put("body", String.format("%s \r\n %s", line1, line2));

        // Get the newly created post by id and validate that it is created successfully
        responseOptions = postWithBody(EndPoint.POSTS, bodyMap); // post action

        assertThat("Resource is not created ", responseOptions.getStatusCode(), is(201)); // check the resource is created

        Post post = responseOptions.getBody().as(Post.class);

        Map<String, String> pathParamMap = new HashMap<>();
        pathParamMap.put("postId", post.getId());

        responseOptions = getWithPathParameter(EndPoint.POSTS + "/" + "{postId}", pathParamMap);

        assertThat("Resource created in the previous in the test is not found", responseOptions.getStatusCode(), is(200));
    }

    @Test
    public void validateDeleteAPost() {
        Map<String, String> pathParamMap = new HashMap<>();
        pathParamMap.put("postId", "5");

        //Check that the resource item exists before deleting it
        responseOptions = getWithPathParameter(EndPoint.POSTS + "/{postId}", pathParamMap);
        assertThat("Status code not OK ", responseOptions.getStatusCode(), is(200));
        Post post = responseOptions.getBody().as(Post.class);
        String id = post.getId();
        assertThat(id, is("5"));

        //Make a delete call on the resource item
        responseOptions = deleteWithPathParams(EndPoint.POSTS + "/{postId}", pathParamMap);
        assertThat("Status code not OK ", responseOptions.getStatusCode(), is(200));

        //And access the resource again, as its deleted should return a 404
        responseOptions = getWithPathParameter(EndPoint.POSTS + "/{postId}", pathParamMap);
        assertThat("Resource deleted in previous step may not be deleted", responseOptions.getStatusCode(), is(404));
    }

}
