package myUtil;

public class StringUtil {
    public static String addDoubleQuotationMarks(String input) {
        return "\"" + input + "\"";
    }

    public static String addSingleQuotationMarks(String input) {
        return "'" + input + "'";
    }

    public static String addBlank(String input) {
        return " " + input + " ";
    }

}
