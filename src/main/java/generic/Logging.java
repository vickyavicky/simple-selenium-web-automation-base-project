package generic;

public class Logging {

    public static void log(String strMessage) {
        log(strMessage, "info");
    }

    public static void log(String strMessage, String level) {
        System.out.println(strMessage);


    }

}
