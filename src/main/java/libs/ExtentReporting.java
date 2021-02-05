package libs;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Base64;

public class ExtentReporting {

    public static void reportLog (ExtentTest test, String status, String message)
    {
        reportLog(test, status, message, null);

    }

    public static void reportLog (ExtentTest test, String status, String message, WebDriver driver) {
        try {
            String base64="";

            String imagePath = "";
            Status st;
            switch(status.toLowerCase()) {
                case "pass":
                    st = Status.PASS;
                    break;
                case "fail":
                    st = Status.FAIL;
                    break;
                case "skip":
                    st = Status.SKIP;
                    break;
                default:
                    st = Status.INFO;
                    break;
            }
            if(driver != null) {
                try {
                    base64 = screenShot(driver);
                    test.log (st, message, MediaEntityBuilder.createScreenCaptureFromBase64String (base64).build ( ));
                }
                catch(Exception e) {
                    reportLog(test, "fail", "Some exception :" +e.toString());
                    ProjectStaticData.currentTestStatus = "fail";
                }
            }
            else
                test.log(st, message);
        }
        catch(Exception e) {
            reportLog(test, "fail", "Some exception :" +e.toString());
            ProjectStaticData.currentTestStatus = "fail";
        }


    }

    public static String screenShot (WebDriver driver) {
        try {
            String Base64StringofScreenshot = "";
            File src = ((TakesScreenshot) driver).getScreenshotAs (OutputType.FILE);
            byte[] fileContent = FileUtils.readFileToByteArray (src);
            Base64StringofScreenshot = "data:image/png;base64," + Base64.getEncoder ( ).encodeToString (fileContent);
            return Base64StringofScreenshot;
        } catch (Exception e) {
            return "";
        }
    }

}
