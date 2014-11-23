package br.gov.caixa.monitormobile.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampUtils {

    // Utility
    protected TimeStampUtils() {}

    private static final String TIME_STAMP_FORMAT_STRING = "yyyyMMddHHmm";

    public static String dateToTimeStamp(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_STAMP_FORMAT_STRING);
        return sdf.format(date);
    }
}
