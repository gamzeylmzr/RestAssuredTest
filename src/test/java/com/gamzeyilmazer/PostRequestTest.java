package com.gamzeyilmazer;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;

public class PostRequestTest {

    final static Logger logger= Logger.getLogger(GetRequestTest.class);

    @BeforeClass
    public static void setBaseUrl(){
        RestAssured.baseURI="http://dummy.restapiexample.com/api/v1";

    }

    @Test()
    public void postRegisterUser()
    {
        HashMap<String,String> body=new HashMap<>();
        body.put("name","Gamze");
        body.put("salary","1000");
        body.put("age","20");

        logger.debug("Post request");
        Response response=given().
                body(body).
                when().
                post("/create").
                then().assertThat().
                statusCode(200).and().
                extract().
                response();

        String id=response.jsonPath().getString("data.id");
        logger.info("Response :"+response.prettyPrint());
        assertNotNull(id);
    }

}
