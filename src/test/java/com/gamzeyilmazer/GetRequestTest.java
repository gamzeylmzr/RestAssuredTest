package com.gamzeyilmazer;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;


public class GetRequestTest {

    final static Logger logger= Logger.getLogger(GetRequestTest.class);
    private static RequestSpecification requestSpec;

    @Before
    public void setUp() {
        //Console da request ve response ları görebilmek için requestSpecBuilder kullanıldı.
        logger.info("Ergast api kullanılarak get sorgusu yapılıyor.");
        requestSpec=new RequestSpecBuilder().
                setBaseUri("http://ergast.com/api").
                setContentType(ContentType.JSON).
                addFilter(new RequestLoggingFilter()).
                addFilter(new ResponseLoggingFilter()).
                build();
    }


    @Test
    public void getDrivers_ShouldBeReturnMultipleValues() {
        logger.info("Parametresiz get sorgusu yapılması");
        given().
                spec(requestSpec).
        when().
                get("/f1/2016/drivers.json").
        then().
                assertThat().
                statusCode(200).
        and().
                body("MRData.DriverTable.Drivers.driverId", hasItems("alonso", "button"));

    }

    @Test
    public void getNumberOfCircuits_UsingPathParam() {
        logger.info("pathParam kullanılarak parametreli get sorgusu yapılması");

        String season = "2017";
        int numberOfRaces = 20;

        given().
                spec(requestSpec).
                pathParam("raceSeason",season).
        when().
                get("/f1/{raceSeason}/circuits.json").
        then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(numberOfRaces));
        logger.info("Response :"+requestSpec.toString());
    }

    @Test
    public void extractCircuitIdAndSendAnotherGetRequest() {
        logger.info("Get sorgusu ile elde edilen response daki değer diğer bir get sorgusunda kullanışmıştır.");
        String url="/f1/2017/circuits.json";
        String path="MRData.CircuitTable.Circuits.circuitId[0]";
        String circuitId=extractValueFromGetRequest(url,path);

        given().
                spec(requestSpec).
                pathParam("circuitId",circuitId).
                when().
                get("/f1/circuits/{circuitId}.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.Location[0].country",equalTo("Australia"));
    }

    @Test
    public void getSeriesName_UsingJsonPath() {
        logger.info("JSONPath kullanılarak response validasyonu yapılması");
        String responseBody=given().spec(requestSpec).when().get("/f1/2017/circuits.json").getBody().asString();

        JsonPath resJson=new JsonPath(responseBody);
        String seriesName=resJson.getString("MRData.series");

        assertEquals("f1",seriesName);
        logger.info("Series Name : " + seriesName);
    }

    public String extractValueFromGetRequest(String URL, String bodyPath)
    {
        String responseBody=RestAssured.given().spec(requestSpec)
                        .when()
                        .get(URL)
                        .getBody()
                        .asString();
        JsonPath resJson=new JsonPath(responseBody);
        return resJson.getString(bodyPath);
    }

}
