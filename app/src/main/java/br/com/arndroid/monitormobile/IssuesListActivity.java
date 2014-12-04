package br.com.arndroid.monitormobile;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import br.com.arndroid.monitormobile.adapter.IssuesAdapter;
import br.com.arndroid.monitormobile.provider.Contract;

public class IssuesListActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ACRONYM_ID_KEY = "ACRONYM_ID_KEY";

    private IssuesAdapter mAdapter;
    private String mAcronymId;

    private static final int ISSUES_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.subscriptions_list_activity);

        if (savedInstanceState != null) {
            mAcronymId = savedInstanceState.getString(ACRONYM_ID_KEY);
        } else {
            mAcronymId = getIntent().getExtras().getString(ACRONYM_ID_KEY);
        }

        final ActionBar actionBar = getActionBar();
        //noinspection ConstantConditions
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mAcronymId);

        mAdapter = new IssuesAdapter(this);
        setListAdapter(mAdapter);
        getLoaderManager().restartLoader(ISSUES_LOADER_ID, null, this);
    }

    @Override
    protected void onSaveInstanceState(@SuppressWarnings("NullableProblems") Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ACRONYM_ID_KEY, mAcronymId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.issues_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();

        switch (itemId) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_add_issue:
                Intent intent = new Intent(this, NewIssueActivity.class);
                intent.putExtra(NewIssueActivity.ACRONYM_ID_KEY, mAcronymId);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, IssueActivity.class);
        intent.putExtra(IssueActivity.ISSUE_ID_KEY, id);
        startActivity(intent);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ISSUES_LOADER_ID:
                return new CursorLoader(this, Contract.Issues.CONTENT_URI, null,
                        Contract.Issues.ACRONYM_ID_SELECTION,
                        new String[]{mAcronymId}, Contract.Issues.STATE_ASC_FLAG_ASC_AND_CLOCK_ASC);

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
}