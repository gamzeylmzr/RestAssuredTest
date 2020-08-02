package com.gamzeyilmazer;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@RunWith(Parameterized.class)
public class GetRequestParameterizedTest {

    final static Logger logger= Logger.getLogger(GetRequestTest.class);

    private String season;
    private int numberOfRaces;

    public GetRequestParameterizedTest(String season,int numberOfRaces){
        this.season=season;
        this.numberOfRaces=numberOfRaces;
    }

    @Test
    public void getNumberOfCircuits_MultipleData() {
        logger.info("Parameters annotation ı kullanılarak birden fazla data ile get sorgusu yapılması"+"\n"+
                "Season:"+season+" Number of Races:"+numberOfRaces);
        given().
                pathParam("raceSeason",season).
                when().
                get("http://ergast.com/api/f1/{raceSeason}/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(numberOfRaces));
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][] {
                {"2017",20},
                {"2016",21},
                {"1966",9}
        });
    }
}
