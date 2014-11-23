package br.gov.caixa.monitormobile.utils;

import junit.framework.TestCase;

import java.util.Calendar;

public class TimeStampUtilsTest extends TestCase {

    public void testDateToTimeStampMustReturnCorrectValues() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, Calendar.FEBRUARY, 3, 4, 5);
        assertEquals("000102030405", TimeStampUtils.dateToTimeStamp(calendar.getTime()));
    }
}
