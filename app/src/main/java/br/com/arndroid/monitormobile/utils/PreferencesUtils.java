package br.com.arndroid.monitormobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {

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
}
