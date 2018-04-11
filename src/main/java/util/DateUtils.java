package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static String PATTERN = "dd/MM/yyyy";

    public static Date parse(String text) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(text);
    }

    public static String format(Date date) {
        return new SimpleDateFormat(PATTERN).format(date);
    }
}
