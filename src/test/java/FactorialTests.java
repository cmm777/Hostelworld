package test.java;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import main.java.pageObjects.HomePage;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.random.RandomGeneratorFactory;

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

    @Test
    public void endpointTestNumber(){
        RestAssured.baseURI = "http://qainterview.pythonanywhere.com/factorial";
        RequestSpecification request = RestAssured.given();
        request.post("14");
        Response response = request.post("/factorial");
        System.out.println("The status received: " + response.statusLine());
    }
}
