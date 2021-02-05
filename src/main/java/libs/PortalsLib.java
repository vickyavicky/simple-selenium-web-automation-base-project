package libs;

import generic.Logging;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class PortalsLib {

    public static void takeSnapShot (WebDriver driver, String fileWithPath) {
        try {
            TakesScreenshot scrShot =((TakesScreenshot)driver);
            File SrcFile=scrShot.getScreenshotAs (OutputType.FILE);
            File DestFile=new File(fileWithPath);
            FileUtils.copyFile (SrcFile, DestFile);
        }
        catch(Exception e) {
            Logging.log ("Some Exception during screen shot  " + e);
        }
    }

}
