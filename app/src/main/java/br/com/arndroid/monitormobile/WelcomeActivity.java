package br.com.arndroid.monitormobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import br.com.arndroid.monitormobile.provider.users.UsersEntity;
import br.com.arndroid.monitormobile.provider.users.UsersManager;
import br.com.arndroid.monitormobile.utils.PreferencesUtils;

public class WelcomeActivity extends Activity {

    EditText mEdtUserShortName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        bindScreen();
    }

    public void dbRegister(View view) {
        if (!TextUtils.isEmpty(mEdtUserShortName.getText())) {
            final UsersEntity user = new UsersEntity(null, mEdtUserShortName.getText().toString().trim());
            final UsersManager manager = new UsersManager(this);
            if (manager.entityWillCauseConstraintViolation(user)) {
                new AlertDialog.Builder(this)
                        .setTitle("Conflito de Nome")
                        .setMessage("Já temos este nome na rede. Por favor escolha outro.")
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
            } else {
                PreferencesUtils.setCurrentUserShortName(this, user.getShortName());
                manager.insertCurrentUserAndRelationships(user);
                NavUtils.navigateUpFromSameTask(this);
            }
        }
    }

    private void bindScreen() {
        mEdtUserShortName = (EditText) findViewById(R.id.edtUserShortName);
    }

}
