package test.java;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import main.java.utils.Constants;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {

    public static WebDriver myDriver;
    public ExtentSparkReporter reporter;
    public static ExtentReports extent;
    public static ExtentTest logger;

    @BeforeTest
    public void beforeTest(){
        reporter = new ExtentSparkReporter(System.getProperty("user.dir")+ File.separator + "reports" + File.separator + "AutomationReport.html");
        reporter.config().setEncoding("utf-8");
        reporter.config().setDocumentTitle("Hostelworld automation report");
        reporter.config().setReportName("Run results");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Cristian Maillard", "2022");
    }

    @BeforeMethod
    public void beforeMethod(Method testMethod){
        logger = extent.createTest(testMethod.getName());
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ File.separator +"drivers"+ File.separator + "chromedriver.exe");
        myDriver = new ChromeDriver();
        myDriver.manage().window().maximize();
        myDriver.get(Constants.url);
        myDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void afterMethod(ITestResult result){
        if(result.getStatus()== ITestResult.SUCCESS){
            String methodName = result.getMethod().getMethodName();
            String logText = "Test case: "+methodName+" - Passed";
            Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
            logger.log(Status.PASS, m);
        }
        else if (result.getStatus()==ITestResult.FAILURE) {
            //Log
            String methodName = result.getMethod().getMethodName();
            String logText = "Test case: "+methodName+" - Failed";
            Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
            logger.log(Status.FAIL, m);
            //Screenshot
            String fileName = (System.getProperty("user.dir")+ File.separator + "screenshots" + File.separator + result.getMethod().getMethodName());
            File f = ((TakesScreenshot) BaseTest.myDriver).getScreenshotAs(OutputType.FILE);
            try{
                FileUtils.copyFile(f, new File(fileName+".png"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        myDriver.quit();
    }

    @AfterTest
    public void afterTest(){
        extent.flush();
    }
}
