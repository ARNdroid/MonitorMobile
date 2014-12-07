package br.com.arndroid.monitormobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.com.arndroid.monitormobile.utils.PreferencesUtils;
import br.com.arndroid.monitormobile.utils.ScreenTipsUtils;

public class ScreenTipActivity extends Activity {

    public static final String SCREEN_TIP_KEY = "SCREEN_TIP_KEY";

    private String mScreenTipKey;

    private TextView mTxtTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_tip_activity);

        if (savedInstanceState != null) {
            mScreenTipKey = savedInstanceState.getString(SCREEN_TIP_KEY);
        } else {
            mScreenTipKey = getIntent().getExtras().getString(SCREEN_TIP_KEY);
        }

        bindScreen();
        refreshScreen();

    }

    private void refreshScreen() {
        if (PreferencesUtils.SCREEN_TIP_DASHBOARD_KEY.equals(mScreenTipKey)) {
            mTxtTip.setText(ScreenTipsUtils.getScreenTipText(this, PreferencesUtils.SCREEN_TIP_DASHBOARD_KEY));
        } else {
            throw new IllegalArgumentException("Invalid mScreenTipKey=" + mScreenTipKey);
        }
    }

    private void bindScreen() {
        mTxtTip = (TextView) findViewById(R.id.txtTip);
    }

    @Override
    protected void onSaveInstanceState(@SuppressWarnings("NullableProblems") Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SCREEN_TIP_KEY, mScreenTipKey);
    }

    @Override
    public void onBackPressed() {
        PreferencesUtils.setScreenTipShown(this, mScreenTipKey, false);
        super.onBackPressed();
    }

    public void cmdEntendi(View view) {
        PreferencesUtils.setScreenTipShown(this, mScreenTipKey, false);
        finish();
    }
}