package br.com.arndroid.monitormobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Date;

import br.com.arndroid.monitormobile.provider.actions.ActionsEntity;
import br.com.arndroid.monitormobile.provider.actions.ActionsManager;
import br.com.arndroid.monitormobile.provider.issues.IssuesEntity;
import br.com.arndroid.monitormobile.provider.issues.IssuesManager;
import br.com.arndroid.monitormobile.provider.systems.SystemsManager;
import br.com.arndroid.monitormobile.provider.users.UsersManager;
import br.com.arndroid.monitormobile.utils.DashboardUtils;
import br.com.arndroid.monitormobile.utils.IssueUtils;
import br.com.arndroid.monitormobile.utils.TimeStampUtils;

public class NewIssueActivity extends Activity {

    public static final String ACRONYM_ID_KEY = "ACRONYM_ID_KEY";

    private String mAcronymId = null;
    private ImageView mImgFlagAndClockType;
    private EditText mEdtSummary;
    private Spinner mSpnAcronymId;
    private EditText mEdtDescription;
    private ArrayAdapter<String> mAcronymIdAdapter;
    private Long mCurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_issue_activity);

        if (savedInstanceState != null) {
            mAcronymId = savedInstanceState.getString(ACRONYM_ID_KEY, null);
        } else {
            mAcronymId = getIntent().getExtras().getString(ACRONYM_ID_KEY, null);
        }

        final SystemsManager systemsManager = new SystemsManager(this);
        mAcronymIdAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, systemsManager.allAcronymIds());

        final UsersManager usersManager = new UsersManager(this);
        mCurrentUserId = usersManager.getCurrentUser().getId();

        bindScreen();
        setupScreen();
        refreshScreen();
    }

    @Override
    protected void onSaveInstanceState(@SuppressWarnings("NullableProblems") Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ACRONYM_ID_KEY, mAcronymId);
    }

    private void setupScreen() {
        mSpnAcronymId.setAdapter(mAcronymIdAdapter);
        int defaultPosition = 0;
        if (mAcronymId != null) {
            defaultPosition = mAcronymIdAdapter.getPosition(mAcronymId);
        }
        mSpnAcronymId.setSelection(defaultPosition);
    }

    private void refreshScreen() {
        int NOT_CLOSED = -1;
        mImgFlagAndClockType.setImageResource(DashboardUtils.getImageResourceIdForStateFlagTypeAndClockType(
                NOT_CLOSED, IssueUtils.FLAG_BLUE, IssueUtils.CLOCK_BLUE, true));

    }

    private void bindScreen() {
        mImgFlagAndClockType = (ImageView) findViewById(R.id.imgFlagAndClockType);
        mEdtSummary = (EditText) findViewById(R.id.edtSummary);
        mSpnAcronymId = (Spinner) findViewById(R.id.spnAcronymId);
        mEdtDescription = (EditText) findViewById(R.id.edtDescription);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();

        switch (itemId) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void newIssue(View view) {
        if (validateUserEntry()) {
            final Date currentDate = new Date();
            final IssuesEntity newIssueEntity = new IssuesEntity(
                    null,
                    (String) mSpnAcronymId.getSelectedItem(),
                    TimeStampUtils.dateToTimeStamp(currentDate),
                    IssueUtils.STATE_OPEN,
                    IssueUtils.FLAG_BLUE,
                    IssueUtils.CLOCK_BLUE,
                    mEdtSummary.getText().toString().trim(),
                    mEdtDescription.getText().toString().trim(),
                    mCurrentUserId,
                    null);
            final IssuesManager issuesManager = new IssuesManager(this);
            issuesManager.refresh(newIssueEntity);

            final ActionsEntity newActionEntity = new ActionsEntity(null, newIssueEntity.getId(),
                    TimeStampUtils.dateToTimeStamp(currentDate), "Abertura", "Abertura do incidente",
                    mCurrentUserId);
            final ActionsManager actionsManager = new ActionsManager(this);
            actionsManager.refresh(newActionEntity);

            finish();
        }
    }

    private boolean validateUserEntry() {
        if (TextUtils.isEmpty(mEdtSummary.getText())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Favor informar a breve descrição do incidente.")
                    .setTitle("Sumário")
                    .setPositiveButton("OK", null);
            builder.create().show();
            return false;
        }

        if (TextUtils.isEmpty(mEdtDescription.getText())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Favor informar a descrição detalhada do incidente.")
                    .setTitle("Descrição")
                    .setPositiveButton("OK", null);
            builder.create().show();
            return false;
        }

        return true;
    }
}