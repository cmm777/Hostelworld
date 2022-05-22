package test.java;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import main.java.pageObjects.HomePage;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Random;

public class FactorialTests extends test.java.BaseTest {

    @Test
    public void verifyPageTitle(){
        String pageTitle = HomePage.getPageTitle();
        Assert.assertEquals(pageTitle, "Factorial");
    }

    @Test
    public void calculateFactorial(){
        Random rand = new Random();
        int upperbound = 20; //Apparently 20 is the limit for the factorial calculator provided by Apache
        int num = rand.nextInt(upperbound);
        String result = HomePage.enterValueAndCalculate(Integer.toString(num));
        Assert.assertEquals(result, "The factorial of "+num+" is: "+CombinatoricsUtils.factorial(num));
    }

    @org.junit.Test
    public void endpointTestNumber(){
        Random rand = new Random();
        int upperbound = 20;
        int num = rand.nextInt(upperbound);
        RestAssured.baseURI = "http://qainterview.pythonanywhere.com";
        RequestSpecification request = RestAssured.given();
        request.header("Host","qainterview.pythonanywhere.com");
        request.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        request.body("{\"Key\":\"'number'\" , \"Value\":\"5\"}");
        Response response = request.post("/factorial");
        System.out.println("The status received: " + response.statusLine());
    }
}
