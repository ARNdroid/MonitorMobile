package br.com.arndroid.monitormobile;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.arndroid.monitormobile.adapter.ActionsAdapter;
import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.followers.FollowersManager;
import br.com.arndroid.monitormobile.provider.issues.IssuesEntity;
import br.com.arndroid.monitormobile.provider.issues.IssuesManager;
import br.com.arndroid.monitormobile.provider.users.UsersManager;
import br.com.arndroid.monitormobile.utils.DashboardUtils;
import br.com.arndroid.monitormobile.utils.TimeStampUtils;

public class IssueActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ISSUE_ID_KEY = "ISSUE_ID_KEY";

    private static final int ACTIONS_LOADER_ID = 1;

    private ActionsAdapter mAdapter;
    private List<View> mViewList = new ArrayList<View>();

    private IssuesEntity mEntity;
    private ImageView mImgFlagAndClockType;
    private TextView mTxtSummary;
    private TextView mTxtAcronymId;
    private TextView mTxtDescription;
    private TextView mTxtReporter;
    private TextView mTxtOwner;
    private TextView mTxtFollowers;
    private TextView mTxtTotalActions;
    private LinearLayout mLayInsertionPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issue_activity);

        final Long id;
        if (savedInstanceState != null) {
            id = savedInstanceState.getLong(ISSUE_ID_KEY);
        } else {
            id = getIntent().getExtras().getLong(ISSUE_ID_KEY);
        }
        final IssuesManager manager = new IssuesManager(this);
        mEntity = manager.issuesFromId(id);

        final ActionBar actionBar = getActionBar();
        //noinspection ConstantConditions
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAdapter = new ActionsAdapter(this);
        getLoaderManager().restartLoader(ACTIONS_LOADER_ID, null, this);

        bindScreen();
        refreshScreen();
    }

    private void refreshScreen() {
        mImgFlagAndClockType.setImageResource(DashboardUtils.getImageResourceIdForStateFlagTypeAndClockType(
                mEntity.getState(), mEntity.getFlagType(), mEntity.getClockType(), true));
        mTxtSummary.setText(mEntity.getSummary());
        mTxtAcronymId.setText(mEntity.getAcronymId());
        mTxtDescription.setText(mEntity.getDescription());
        final UsersManager usersManager = new UsersManager(this);
        final String reportPhrase = "por " + usersManager.userFromId(mEntity.getReporterId()).getShortName()
                + " em " + TimeStampUtils.timeStampToFormattedString(mEntity.getTimeStamp());
        mTxtReporter.setText(reportPhrase);
        mTxtOwner.setText(usersManager.userFromId(mEntity.getOwnerId()).getShortName());
        final FollowersManager followersManager = new FollowersManager(this);
        mTxtFollowers.setText(followersManager.humanPhraseFromIssueId(mEntity.getId()));
    }

    private void bindScreen() {
        mImgFlagAndClockType = (ImageView) findViewById(R.id.imgFlagAndClockType);
        mTxtSummary = (TextView) findViewById(R.id.txtSummary);
        mTxtAcronymId = (TextView) findViewById(R.id.txtAcronymId);
        mTxtDescription = (TextView) findViewById(R.id.txtDescription);
        mTxtReporter = (TextView) findViewById(R.id.txtReporter);
        mTxtOwner = (TextView) findViewById(R.id.txtOwner);
        mTxtFollowers = (TextView) findViewById(R.id.txtFollowers);
        mTxtTotalActions = (TextView) findViewById(R.id.txtTotalActions);
        mLayInsertionPoint = (LinearLayout) findViewById(R.id.layInsertionPoint);
    }

    @Override
    protected void onSaveInstanceState(@SuppressWarnings("NullableProblems") Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ISSUE_ID_KEY, mEntity.getId());
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ACTIONS_LOADER_ID:
                return new CursorLoader(this, Contract.Actions.CONTENT_URI, null,
                        Contract.Actions.ISSUE_ID_SELECTION,
                        new String[]{mEntity.getId().toString()}, Contract.Actions.TIME_STAMP_DESC);

            default:
                throw new IllegalArgumentException("Invalid loader id=" + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);

        final int totalActions = mAdapter.getCount();
        final String strTotalActions;
        if (totalActions == 0) {
            strTotalActions = "NENHUMA AÇÃO";
        } else if (totalActions == 1) {
            strTotalActions = "1 AÇÃO";
        } else {
            strTotalActions =  totalActions + " AÇÕES";
        }
        mTxtTotalActions.setText(strTotalActions);
        for (int position = 0; position < totalActions; position++) {
            View convertView = null;
            if (mViewList.size() > position) {
                convertView = mViewList.get(position);
            }
            final View view = mAdapter.getView(position, convertView, mLayInsertionPoint);
            if (convertView == null) {
                mViewList.add(view);
                mLayInsertionPoint.addView(view);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public void newAction(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Estamos trabalhando para disponbilizar esta funcionalidade nas próximas versões! Por favor, aguardem cenas do próximo episódio...")
                .setTitle("Em construção")
                .setPositiveButton("Mesmo ansioso compreendo", null);
        builder.create().show();
    }
}