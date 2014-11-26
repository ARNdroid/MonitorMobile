package br.com.arndroid.monitormobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.arndroid.monitormobile.utils.PreferencesUtils;

public class DashboardActivity extends Activity {

    private String mUserShortName;
    private TextView mTxtUserShortName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        bindScreen();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!PreferencesUtils.isUserRegistered(this)) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        } else {
            mUserShortName = PreferencesUtils.getUserShortName(this);
            refreshScreen();
        }
    }

    private void refreshScreen() {
        mTxtUserShortName.setText(mUserShortName + ",");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();

        final Intent intent;

        switch (itemId) {
            case R.id.action_simulations:
                intent = new Intent(this, SimulationsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void bindScreen() {
        mTxtUserShortName = (TextView) findViewById(R.id.txtUserShortName);
    }
}
