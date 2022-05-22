package main.java.pageObjects;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static test.java.BaseTest.logger;
import static test.java.BaseTest.myDriver;

public class HomePage {
    public static WebElement pageTitle = myDriver.findElement(By.xpath("//title"));
    public static WebElement searchBox = myDriver.findElement(By.id("number"));
    public static WebElement calculateButton = myDriver.findElement(By.id("getFactorial"));
    public static WebElement calculationResult = myDriver.findElement(By.xpath("//p[@id='resultDiv']"));

    public static String getPageTitle(){ return pageTitle.getAttribute("text");}


    public static String enterValueAndCalculate(String valueToCalculate){
        searchBox.sendKeys(valueToCalculate);
        calculateButton.click();
        //This needs a fancy wait for condition to be met: Wait until element text != ""
        while(calculationResult.getText()==""){
            calculateButton.click();
        }
        return calculationResult.getText();
    }
}
