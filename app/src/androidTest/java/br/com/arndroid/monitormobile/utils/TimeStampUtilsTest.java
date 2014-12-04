package br.com.arndroid.monitormobile.utils;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Calendar;

public class TimeStampUtilsTest extends TestCase {

    public void testDateToTimeStampMustReturnCorrectValues() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, Calendar.FEBRUARY, 3, 4, 5, 6);
        Assert.assertEquals("00010203040506", TimeStampUtils.dateToTimeStamp(calendar.getTime()));
    }
}
