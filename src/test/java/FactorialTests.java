package test.java;

import java.util.*;
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

    @Test(description="Manual test case #1")
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
        request.formParam("number", num);
        Response response = request.post("/factorial");
        System.out.println("The status received: " + response.statusLine());
        String result = response.asString();
        result = result.trim();
        result = result.substring(10);
        result = result.substring(0, result.length() - 1);
        System.out.println("Factorial calculated for number: "+num);
        System.out.println("Expected: "+Long.toString(CombinatoricsUtils.factorial(num)));
        System.out.println("Actual: "+result);
        Assert.assertEquals(result, Long.toString(CombinatoricsUtils.factorial(num)));
    }
}
