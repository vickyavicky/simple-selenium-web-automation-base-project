package libs;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ProjectStaticData {

    public static ExtentReports finalReport ;
    public static String outputDataPath = "/replace/with/appropriate/path";
    public static String htmlReportPath = "/replace/with/appropriate/path";
    public static String resultFilePath = "/replace/with/appropriate/path";
    public static long implicitWait = 10;
    public static String currentTestStatus = "Pass";
    public static ExtentSparkReporter report ;
    public static String app_underTest = "replace_with_appropriate_name";

}
