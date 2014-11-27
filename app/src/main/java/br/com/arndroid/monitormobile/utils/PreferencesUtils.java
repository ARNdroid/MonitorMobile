package br.com.arndroid.monitormobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {

    private static final String PREFERENCES_FILE_NAME = "mob_preferences";
    private static final String USER_SHORT_NAME_PREFERENCE_KEY = "USER_SHORT_NAME";

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
}
