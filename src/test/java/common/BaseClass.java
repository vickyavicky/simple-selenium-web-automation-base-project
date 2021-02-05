package common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import generic.GenericLib;
import generic.Logging;
import libs.ExtentReporting;
import libs.PortalsLib;
import libs.ProjectStaticData;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BaseClass {

    public WebDriver launchPortalDashboard () {

        // System Property for Chrome Driver
        System.setProperty("webdriver.chrome.driver", "/replace/with /appropriate/path/to/driver");
        //Comment above and Uncomment below for Firefox
        // System Property for Gecko Driver
        //System.setProperty("webdriver.gecko.driver", "/replace/with /appropriate/path/to/driver");

        // Instantiate a ChromeDriver class.
        WebDriver driver=new ChromeDriver ();

        //If you want to use firefox instead, comment out the above line and and uncomment below(line 36) for firefox
        // Instantiate a GeckoDriver class.
        //WebDriver driver = new FirefoxDriver ();

        //Maximize the browser
        driver.manage().window().maximize();

        // Launch Website
        driver.get("https://replace.with.url");

        return driver;

    }

    public static void takeSnapShot(WebDriver driver,String fileWithPath) {
        try {
            TakesScreenshot scrShot =((TakesScreenshot)driver);
            File SrcFile=scrShot.getScreenshotAs (OutputType.FILE);
            File DestFile=new File(fileWithPath);
            FileUtils.copyFile (SrcFile, DestFile);
        }
        catch(Exception e) {
            Logging.log ("Some Exception occurred during taking screen shot  " + e);
        }
    }

    public void setReportConfigurations() {
        String reportPath = ProjectStaticData.htmlReportPath;
        reportPath = reportPath.replace(".html", "_" + ProjectStaticData.app_underTest + ".html");
        ProjectStaticData.htmlReportPath = reportPath;
        renameExisitngResults();
        ProjectStaticData.report = new ExtentSparkReporter (reportPath);
        ProjectStaticData.finalReport = new ExtentReports ();
        ProjectStaticData.report.config().setDocumentTitle("Portal Regression Tests");
        ProjectStaticData.report.config().setReportName("Web Portal " + ProjectStaticData.app_underTest + " Results");
        ProjectStaticData.report.config().setTheme (Theme.DARK);
        ProjectStaticData.finalReport.attachReporter(ProjectStaticData.report );
    }

    public void renameExisitngResults() {
        if(GenericLib.fileExists(ProjectStaticData.htmlReportPath)) {
            File f1 = new File(ProjectStaticData.htmlReportPath);
            String strNewPath = ProjectStaticData.htmlReportPath.replace(".html", GenericLib.getDateTime() + ".html");
            File f2 = new File(strNewPath );
            boolean b = f1.renameTo(f2);
        }
        String csvPath = ProjectStaticData.resultFilePath + "_" + ProjectStaticData.app_underTest +".csv";
        if(GenericLib.fileExists(csvPath)) {
            String strNewPath = csvPath.replace(".csv", GenericLib.getDateTime() + ".csv");
            if(GenericLib.fileExists(csvPath)) {
                File f1 = new File(csvPath);
                File f2 = new File(strNewPath);
                boolean b = f1.renameTo(f2);
            }
        }
    }

    public void resultUpdate_postExecution (ExtentTest test, WebDriver driver, Method method, ITestResult result) {
        Logging.log ("After test method " + result.getStatus ( ));
        String imagepath = ProjectStaticData.outputDataPath +  method.getName ( ) + GenericLib.getDateTime ( ) +  ".png";
        PortalsLib.takeSnapShot (driver, imagepath);
        driver.manage().timeouts().implicitlyWait (ProjectStaticData.implicitWait, TimeUnit.SECONDS);
        if(result.getStatus() == ITestResult.FAILURE)
            ExtentReporting.reportLog (test, "fail", "Some Error Occurred :" + result.getThrowable ( ),driver);
        else if(result.getStatus() == ITestResult.SKIP)
            ProjectStaticData.finalReport.removeTest(test); //removing it as it creates test in case of retry
            //ExtentReporting.reportLog(test, "skip", "Test Case Failed/skipped",driver); ProjectStaticData.finalReport.flush();
        else if(ProjectStaticData.currentTestStatus.toLowerCase().contains("fail")) {
            ExtentReporting.reportLog(test, "fail", "Some Failures found in Test case :Check Earlier failures for Details");
            result.setStatus(2);
        }
        else
            ExtentReporting.reportLog(test, "pass", "Test Case Passed",driver);
        ProjectStaticData.finalReport.flush();
    }

}
