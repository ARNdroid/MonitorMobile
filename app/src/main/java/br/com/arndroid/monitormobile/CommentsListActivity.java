package br.com.arndroid.monitormobile;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import br.com.arndroid.monitormobile.adapter.CommentsAdapter;
import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.actions.ActionsEntity;
import br.com.arndroid.monitormobile.provider.actions.ActionsManager;
import br.com.arndroid.monitormobile.provider.comments.CommentsEntity;
import br.com.arndroid.monitormobile.provider.comments.CommentsManager;
import br.com.arndroid.monitormobile.provider.users.UsersEntity;
import br.com.arndroid.monitormobile.provider.users.UsersManager;
import br.com.arndroid.monitormobile.utils.TimeStampUtils;

public class CommentsListActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private CommentsAdapter mAdapter;
    private UsersEntity mCurrentUser;
    private ActionsEntity mCurrentAction;
    private EditText mEdtNewComment;

    public static final String ACTION_ID_KEY = "ACTION_ID_KEY";
    private static final int COMMENTS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.comments_list_activity);

        if (savedInstanceState != null) {
            mCurrentAction = new ActionsManager(this).actionsFromId(savedInstanceState.getLong(ACTION_ID_KEY));
        } else {
            mCurrentAction = new ActionsManager(this).actionsFromId(getIntent().getExtras().getLong(ACTION_ID_KEY));
        }
        mCurrentUser = new UsersManager(this).getCurrentUser();

        mAdapter = new CommentsAdapter(this);
        setListAdapter(mAdapter);
        getLoaderManager().restartLoader(COMMENTS_LOADER_ID, null, this);

        bindScreen();
    }

    private void bindScreen() {
        mEdtNewComment = (EditText) findViewById(R.id.edtNewComment);
    }

    @Override
    protected void onSaveInstanceState(@SuppressWarnings("NullableProblems") Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ACTION_ID_KEY, mCurrentAction.getId());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case COMMENTS_LOADER_ID:
                return new CursorLoader(this, Contract.Comments.CONTENT_URI, null,
                        Contract.Comments.ACTION_ID_SELECTION,
                        new String[]{mCurrentAction.getId().toString()}, Contract.Comments.TIME_STAMP_ASC);

            default:
                throw new IllegalArgumentException("Invalid loader id=" + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public void newComment(View view) {
        final String newCommentString = mEdtNewComment.getText().toString().trim();
        if (!TextUtils.isEmpty(newCommentString)) {
            CommentsEntity entity = new CommentsEntity(null, mCurrentAction.getId(),
                    TimeStampUtils.dateToTimeStamp(new Date()), newCommentString, mCurrentUser.getId());
            new CommentsManager(this).refresh(entity);
            finish();
        }
    }
}
