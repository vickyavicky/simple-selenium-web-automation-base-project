package generic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenericLib {

    public static String getDateTime() {
        DateFormat df = new SimpleDateFormat ("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        String dateTime = df.format(dateobj);
        dateTime = dateTime.replaceAll("[/: ]", "_");
        return dateTime;
    }

    public static boolean fileExists(String strPath) {
        File f = new File(strPath);
        if(f.exists())
            return true;
        return false;

    }

    public static void wait(int sec) {
        Logging.log("Waiting for "+ sec + " seconds");
        try {
            Thread.sleep (sec* 1000L);
        }
        catch(Exception e) {}
    }

    public static WebElement explicitWaitById (WebDriver driver, String id) {
        WebDriverWait wait = new WebDriverWait (driver, 20);
        wait.until (ExpectedConditions.presenceOfAllElementsLocatedBy (By.id ( id )));
        return driver.findElement (By.id (id) );
    }


}
