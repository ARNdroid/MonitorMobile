package br.com.arndroid.monitormobile;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.arndroid.monitormobile.adapter.ActionsAdapter;
import br.com.arndroid.monitormobile.dialog.StringListDialog;
import br.com.arndroid.monitormobile.dialog.TextDialog;
import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.actions.ActionsEntity;
import br.com.arndroid.monitormobile.provider.actions.ActionsManager;
import br.com.arndroid.monitormobile.provider.followers.FollowersManager;
import br.com.arndroid.monitormobile.provider.issues.IssuesEntity;
import br.com.arndroid.monitormobile.provider.issues.IssuesManager;
import br.com.arndroid.monitormobile.provider.users.UsersEntity;
import br.com.arndroid.monitormobile.provider.users.UsersManager;
import br.com.arndroid.monitormobile.utils.DashboardUtils;
import br.com.arndroid.monitormobile.utils.IssueUtils;
import br.com.arndroid.monitormobile.utils.TimeStampUtils;

public class IssueActivity extends Activity implements
        StringListDialog.OnStringSelectedListener, TextDialog.OnTextSetListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ISSUE_ID_KEY = "ISSUE_ID_KEY";

    private static final int ACTIONS_LOADER_ID = 1;
    private static final String ACTION_TYPE_DIALOG = "ACTION_TYPE_DIALOG";
    private static final String CHANGE_FLAG_DIALOG = "CHANGE_FLAG_DIALOG";
    private static final String ACTION_UPDATE_TAG = "ACTION_UPDATE_TAG";
    private static final String ACTION_CLOSE_TAG = "ACTION_CLOSE_TAG";

    private ActionsAdapter mAdapter;
    private List<View> mViewList = new ArrayList<View>();

    private IssuesEntity mIssuesEntity;
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
        mIssuesEntity = manager.issuesFromId(id);

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
                mIssuesEntity.getState(), mIssuesEntity.getFlagType(), mIssuesEntity.getClockType(), true));
        mTxtSummary.setText(mIssuesEntity.getSummary());
        mTxtAcronymId.setText(mIssuesEntity.getAcronymId());
        mTxtDescription.setText(mIssuesEntity.getDescription());
        final UsersManager usersManager = new UsersManager(this);
        final String reportPhrase = "por " + usersManager.userFromId(mIssuesEntity.getReporterId()).getShortName()
                + " em " + TimeStampUtils.timeStampToFormattedString(mIssuesEntity.getTimeStamp());
        mTxtReporter.setText(reportPhrase);
        mTxtOwner.setText(mIssuesEntity.getOwnerId() == null ?
                "(não atribuído)" : usersManager.userFromId(mIssuesEntity.getOwnerId()).getShortName());
        final FollowersManager followersManager = new FollowersManager(this);
        mTxtFollowers.setText(followersManager.humanPhraseFromIssueId(mIssuesEntity.getId()));
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
        outState.putLong(ISSUE_ID_KEY, mIssuesEntity.getId());
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
                        new String[]{mIssuesEntity.getId().toString()}, Contract.Actions.TIME_STAMP_DESC);

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
            strTotalActions = "Nenhuma ação";
        } else if (totalActions == 1) {
            strTotalActions = "1 ação";
        } else {
            strTotalActions =  totalActions + " ações";
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
        final StringListDialog dialog = new StringListDialog();
        dialog.setTitle("Nova Ação");
        dialog.setStringListId(R.array.action_type_list);
        dialog.setInitialIndex(0);
        dialog.show(getFragmentManager(), ACTION_TYPE_DIALOG);
    }

    @Override
    public void onStringSelected(String tag, int chosenIndex) {
        if (ACTION_TYPE_DIALOG.equals(tag)) {
            addNewAction(chosenIndex);
        } else if (CHANGE_FLAG_DIALOG.equals(tag)) {
            final IssuesManager issuesManager = new IssuesManager(this);
            final ActionsManager actionsManager = new ActionsManager(this);
            final UsersManager usersManager = new UsersManager(this);
            final int oldFlagType = mIssuesEntity.getFlagType();
            mIssuesEntity.setFlagType(chosenIndex);
            issuesManager.refresh(mIssuesEntity);
            mImgFlagAndClockType.setImageResource(DashboardUtils.getImageResourceIdForStateFlagTypeAndClockType(
                    mIssuesEntity.getState(), mIssuesEntity.getFlagType(), mIssuesEntity.getClockType(), true));

            final String timeStamp = TimeStampUtils.dateToTimeStamp(new Date());
            final String[] flagMeaningArray = getResources().getStringArray(R.array.flags_meaning_list);
            ActionsEntity actionsEntity = new ActionsEntity(null, mIssuesEntity.getId(),
                    timeStamp, "Mudança de importância", "Incidente mudou de '" + flagMeaningArray[oldFlagType]
                    + "' para '" + flagMeaningArray[mIssuesEntity.getFlagType()] + "'", usersManager.getCurrentUser().getId());
            actionsManager.refresh(actionsEntity);
        } else {
            throw new IllegalArgumentException("Invalid tag=" + tag);
        }
    }

    private void addNewAction(int chosenIndex) {
        switch (chosenIndex) {
            case 0:
                addActionUpdate();
                break;
            case 1:
                addActionAssignToMe();
                break;
            case 2:
                addActionAssignToOther();
                break;
            case 3:
                addActionChangeFlag();
                break;
            case 4:
                addActionClose();
                break;
            default:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Estamos trabalhando para disponbilizar esta funcionalidade nas próximas versões! Por favor, aguardem cenas do próximo episódio...")
                        .setTitle("Em construção")
                        .setPositiveButton("Mesmo ansioso compreendo", null);
                builder.create().show();
        }
    }

    private void addActionClose() {
        if (mIssuesEntity.getState() != IssueUtils.STATE_CLOSED) {
            TextDialog textDialog = new TextDialog();
            textDialog.setTitle("Fechar");
            textDialog.setHint("Motivo do fechamento aqui");
            textDialog.show(getFragmentManager(), ACTION_CLOSE_TAG);
        }
    }

    private void addActionChangeFlag() {
        final StringListDialog dialog = new StringListDialog();
        dialog.setTitle("Alterar importância para");
        dialog.setStringListId(R.array.flags_meaning_list);
        dialog.setInitialIndex(mIssuesEntity.getFlagType());
        dialog.show(getFragmentManager(), CHANGE_FLAG_DIALOG);
    }

    private void addActionAssignToOther() {
        final UsersManager usersManager = new UsersManager(this);
        final UsersEntity currentUser = usersManager.getCurrentUser();
        final IssuesManager issuesManager = new IssuesManager(this);
        final ActionsManager actionsManager = new ActionsManager(this);

        List<Long> excludeCandidates = new ArrayList<Long>();
        excludeCandidates.add(currentUser.getId());
        excludeCandidates.add(mIssuesEntity.getOwnerId());
        final List<String> candidates = usersManager.getAllUsersShortName(excludeCandidates);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.addAll(candidates);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atribuir a");
        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final UsersEntity entity = usersManager.getUserFromShortName(candidates.get(which));
                mIssuesEntity.setOwnerId(entity.getId());
                issuesManager.refresh(mIssuesEntity);
                mTxtOwner.setText(candidates.get(which));

                final String timeStamp = TimeStampUtils.dateToTimeStamp(new Date());
                ActionsEntity actionsEntity = new ActionsEntity(null, mIssuesEntity.getId(),
                        timeStamp, "Atribuição", "Incidente atribuído a " + candidates.get(which),
                        currentUser.getId());
                actionsManager.refresh(actionsEntity);

                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void addActionAssignToMe() {
        final UsersManager usersManager = new UsersManager(this);
        final UsersEntity currentUser = usersManager.getCurrentUser();
        if (!currentUser.getId().equals(mIssuesEntity.getOwnerId())) {
            final IssuesManager issuesManager = new IssuesManager(this);
            mIssuesEntity.setOwnerId(currentUser.getId());
            issuesManager.refresh(mIssuesEntity);
            mTxtOwner.setText(currentUser.getShortName());
            final String timeStamp = TimeStampUtils.dateToTimeStamp(new Date());
            ActionsManager actionsManager = new ActionsManager(this);
            ActionsEntity actionsEntity = new ActionsEntity(null, mIssuesEntity.getId(),
                    timeStamp, "Atribuição", currentUser.getShortName() + " assumiu o incidente",
                    currentUser.getId());
            actionsManager.refresh(actionsEntity);
        }
    }

    private void addActionUpdate() {
        TextDialog textDialog = new TextDialog();
        textDialog.setTitle("Atualizar");
        textDialog.setHint("Texto com a atualização aqui");
        textDialog.show(getFragmentManager(), ACTION_UPDATE_TAG);
    }

    @Override
    public void onTextSet(String tag, String actualText) {

        if (ACTION_UPDATE_TAG.equals(tag)) {
            final ActionsManager actionsManager = new ActionsManager(this);
            final String timeStamp = TimeStampUtils.dateToTimeStamp(new Date());
            final UsersManager usersManager = new UsersManager(this);
            final UsersEntity currentUser = usersManager.getCurrentUser();

            ActionsEntity entity = new ActionsEntity(null, mIssuesEntity.getId(), timeStamp, "Atualização", actualText, currentUser.getId());
            actionsManager.refresh(entity);

        } else if (ACTION_CLOSE_TAG.equals(tag)) {
            final IssuesManager issuesManager = new IssuesManager(this);
            final ActionsManager actionsManager = new ActionsManager(this);
            final UsersManager usersManager = new UsersManager(this);
            final UsersEntity currentUser = usersManager.getCurrentUser();

            mIssuesEntity.setState(IssueUtils.STATE_CLOSED);
            issuesManager.refresh(mIssuesEntity);
            mImgFlagAndClockType.setImageResource(DashboardUtils.getImageResourceIdForStateFlagTypeAndClockType(
                    mIssuesEntity.getState(), mIssuesEntity.getFlagType(), mIssuesEntity.getClockType(), true));

            final String timeStamp = TimeStampUtils.dateToTimeStamp(new Date());
            ActionsEntity entity = new ActionsEntity(null, mIssuesEntity.getId(), timeStamp, "Fechamento", actualText, currentUser.getId());
            actionsManager.refresh(entity);

        } else {
            throw new IllegalArgumentException("Invalid tag=" + tag);
        }
    }
}