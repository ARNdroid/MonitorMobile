package br.com.arndroid.monitormobile.utils;

public class IssueUtils {

    // Attention: don't change this values. They are stored in database.

    public static final int STATE_CLOSED = 0;
    public static final int STATE_OPEN = 1;


    public static final int FLAG_BLACK = 0;
    public static final int FLAG_RED = 1;
    public static final int FLAG_YELLOW = 2;
    public static final int FLAG_BLUE = 3;
    public static final int TOTAL_FLAGS = 4;


    public static final int CLOCK_BLACK = 0;
    public static final int CLOCK_RED = 1;
    public static final int CLOCK_YELLOW = 2;
    public static final int CLOCK_BLUE = 3;
    public static final int TOTAL_CLOCKS = 4;

    // Utility class.
    protected IssueUtils() {}
}
