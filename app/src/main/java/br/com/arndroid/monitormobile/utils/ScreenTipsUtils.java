package br.com.arndroid.monitormobile.utils;

import android.content.Context;

import br.com.arndroid.monitormobile.R;

public class ScreenTipsUtils {

    // Utility:
    private ScreenTipsUtils() {
    }

    public static String getScreenTipText(Context context, String screenTipKey) {
        final int stringResourceId;
        if (PreferencesUtils.SCREEN_TIP_DASHBOARD_KEY.equals(screenTipKey)) {
            stringResourceId = R.string.text_screen_tip_dashboard;
        } else if (PreferencesUtils.SCREEN_TIP_ISSUES_LIST_KEY.equals(screenTipKey)) {
            stringResourceId = R.string.text_screen_tip_issues_list;
        } else if (PreferencesUtils.SCREEN_TIP_ISSUE_KEY.equals(screenTipKey)) {
            stringResourceId = R.string.text_screen_tip_issue;
        // Mind the gap: new screen tips here.
        } else {
            throw new IllegalArgumentException("Invalid screenTipKey=" + screenTipKey);
        }
        return context.getString(stringResourceId);
    }
}
