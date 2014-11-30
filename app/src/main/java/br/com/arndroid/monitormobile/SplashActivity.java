package br.com.arndroid.monitormobile;

import android.app.Activity;
import android.content.Intent;

import br.com.arndroid.monitormobile.app.MonitorMobileApp;
import br.com.arndroid.monitormobile.utils.PreferencesUtils;

public class SplashActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();

        final MonitorMobileApp app = (MonitorMobileApp) getApplication();
        if (app.isFinishing()) {
            app.setFinishing(false);
            finish();
        } else {
            final Intent intent;
            if (!PreferencesUtils.isCurrentUserRegistered(this)) {
                intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
            } else {
                intent = new Intent(this, DashboardListActivity.class);
                startActivity(intent);
            }
        }
    }
}
