package br.com.arndroid.monitormobile.utils;

import br.com.arndroid.monitormobile.R;

public class DashboardUtils {


    public static final int DASHBOARD_TYPE_FLAG = 0;
    public static final int DASHBOARD_TYPE_CLOCK = 1;
    public static final int DASHBOARD_TYPE_FLAG_AND_CLOCK = 2;

    // Utility:
    private DashboardUtils() {}

    public static int getImageResourceIdForClosed() {
        return R.drawable.check;
    }

    public static int getImageResourceIdForFlagTypeAndClockType(int flagType, int clockType, boolean fullSize) {
        return fullSize ? flagAndClockImageFull[flagType][clockType] : flagAndClockImage[flagType][clockType];
    }

    private static int[][] flagAndClockImage = {
            {R.drawable.flag_black_and_clock_black, R.drawable.flag_black_and_clock_red, R.drawable.flag_black_and_clock_yellow, R.drawable.flag_black_and_clock_blue},
            {R.drawable.flag_red_and_clock_black, R.drawable.flag_red_and_clock_red, R.drawable.flag_red_and_clock_yellow, R.drawable.flag_red_and_clock_blue},
            {R.drawable.flag_yellow_and_clock_black, R.drawable.flag_yellow_and_clock_red, R.drawable.flag_yellow_and_clock_yellow, R.drawable.flag_yellow_and_clock_blue},
            {R.drawable.flag_blue_and_clock_black, R.drawable.flag_blue_and_clock_red, R.drawable.flag_blue_and_clock_yellow, R.drawable.flag_blue_and_clock_blue}
    };

    private static int[][] flagAndClockImageFull = {
            {R.drawable.flag_black_and_clock_black_full, R.drawable.flag_black_and_clock_red_full, R.drawable.flag_black_and_clock_yellow_full, R.drawable.flag_black_and_clock_blue_full},
            {R.drawable.flag_red_and_clock_black_full, R.drawable.flag_red_and_clock_red_full, R.drawable.flag_red_and_clock_yellow_full, R.drawable.flag_red_and_clock_blue_full},
            {R.drawable.flag_yellow_and_clock_black_full, R.drawable.flag_yellow_and_clock_red_full, R.drawable.flag_yellow_and_clock_yellow_full, R.drawable.flag_yellow_and_clock_blue_full},
            {R.drawable.flag_blue_and_clock_black_full, R.drawable.flag_blue_and_clock_red_full, R.drawable.flag_blue_and_clock_yellow_full, R.drawable.flag_blue_and_clock_blue_full}
    };

    public static int getImageResourceIdForFlagType(int flag) {
        return flagImage[flag];
    }

    private static int [] flagImage = {R.drawable.flag_black, R.drawable.flag_red, R.drawable.flag_yellow, R.drawable.flag_blue};

    public static int getImageResourceIdForClockType(int clock) {
        return clockImage[clock];
    }

    private static int [] clockImage = {R.drawable.clock_black, R.drawable.clock_red, R.drawable.clock_yellow, R.drawable.clock_blue};
}
