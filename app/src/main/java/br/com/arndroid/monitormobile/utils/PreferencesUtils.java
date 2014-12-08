package br.com.arndroid.monitormobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {

    public static final String SCREEN_TIP_DASHBOARD_KEY = "SCREEN_TIP_DASHBOARD_KEY";
    public static final String SCREEN_TIP_ISSUES_LIST_KEY = "SCREEN_TIP_ISSUES_LIST_KEY";
    public static final String SCREEN_TIP_ISSUE_KEY = "SCREEN_TIP_ISSUE_KEY";
    // Mind the gap: when adding a new constant above you must update:
    //   PreferencesUtils.setAllScreenTipsShown()
    //   ScreenTipActivity.refreshScreen()
    //   ScreenTipsUtils.getScreenTipText()

    private static final String PREFERENCES_FILE_NAME = "mob_preferences";
    private static final String USER_SHORT_NAME_PREFERENCE_KEY = "USER_SHORT_NAME";
    private static final String LAST_DASHBOARD_TYPE_KEY = "LAST_DASHBOARD_TYPE";

    // Utility:
    private PreferencesUtils() {}

    public static boolean isCurrentUserRegistered(Context context) {
        return getCurrentUserShortName(context) != null;
    }

    public static String getCurrentUserShortName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getString(USER_SHORT_NAME_PREFERENCE_KEY, null);
    }

    public static void setCurrentUserShortName(Context context, String shortName) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_SHORT_NAME_PREFERENCE_KEY, shortName);
        editor.apply();
    }

    public static int getLastDashboardType(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getInt(LAST_DASHBOARD_TYPE_KEY, DashboardUtils.DASHBOARD_TYPE_FLAG_AND_CLOCK);
    }

    public static void setLastDashboardType(Context context, int lastDashboardType) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(LAST_DASHBOARD_TYPE_KEY, lastDashboardType);
        editor.apply();
    }

    public static void setScreenTipShown(Context context, String screenTipKey, boolean shown) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(screenTipKey, shown);
        editor.apply();
    }

    public static void setAllScreenTipsShown(Context context, boolean shown) {
        setScreenTipShown(context, SCREEN_TIP_DASHBOARD_KEY, shown);
        setScreenTipShown(context, SCREEN_TIP_ISSUES_LIST_KEY, shown);
        setScreenTipShown(context, SCREEN_TIP_ISSUE_KEY, shown);
        // Mind the gap: new screen tips here.
    }

    public static boolean isScreenTipShown(Context context, String screenTipKey) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getBoolean(screenTipKey, true);
    }
}
