package br.gov.caixa.monitormobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import br.gov.caixa.monitormobile.provider.users.UsersEntity;
import br.gov.caixa.monitormobile.provider.users.UsersManager;
import br.gov.caixa.monitormobile.utils.PreferencesUtils;

public class WelcomeActivity extends Activity {

    EditText mEdtUserShortName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        bindScreen();
    }

    public void dbRegister(View view) {
        if (!TextUtils.isEmpty(mEdtUserShortName.getText())) {
            final UsersEntity user = new UsersEntity(null, mEdtUserShortName.getText().toString());
            final UsersManager manager = new UsersManager(this);
            if (manager.entityWillCauseConstraintViolation(user)) {
                new AlertDialog.Builder(this)
                        .setTitle("Conflito de Nome")
                        .setMessage("Já temos este nome na rede. Por favor escolha outro.")
                        .create()
                        .show();
            } else {
                manager.refresh(user);
                PreferencesUtils.setUserShortName(this, user.getShortName());
                NavUtils.navigateUpFromSameTask(this);
            }
        }
    }

    private void bindScreen() {
        mEdtUserShortName = (EditText) findViewById(R.id.edtUserShortName);
    }

}
