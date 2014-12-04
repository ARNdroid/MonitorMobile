package br.com.arndroid.monitormobile.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("FieldCanBeLocal")
public class TimeStampUtils {

    // Utility
    protected TimeStampUtils() {}

    private static int DAY_START = 6;
    private static int DAY_END = 8;
    private static int MONTH_START = 4;
    private static int MONTH_END = 6;
    private static int YEAR_START = 0;
    private static int YEAR_END = 4;
    private static int HOUR_START = 8;
    private static int HOUR_END = 10;
    private static int MINUTE_START = 10;
    private static int MINUTE_END = 12;

    private static final String TIME_STAMP_FORMAT_STRING = "yyyyMMddHHmmss";

    public static String dateToTimeStamp(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_STAMP_FORMAT_STRING);
        return sdf.format(date);
    }

    public static String timeStampToFormattedString(String timeStamp) {
        return timeStamp.substring(DAY_START, DAY_END) + "/"
                + timeStamp.substring(MONTH_START, MONTH_END) + "/"
                + timeStamp.substring(YEAR_START, YEAR_END) + " às "
                + timeStamp.substring(HOUR_START, HOUR_END) + ":"
                + timeStamp.substring(MINUTE_START, MINUTE_END);
    }
}
