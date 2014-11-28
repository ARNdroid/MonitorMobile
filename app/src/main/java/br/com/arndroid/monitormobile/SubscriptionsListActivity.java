package br.com.arndroid.monitormobile;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import br.com.arndroid.monitormobile.adapter.SubscriptionsAdapter;
import br.com.arndroid.monitormobile.dialog.StringListDialog;
import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.subscriptions.SubscriptionsEntity;
import br.com.arndroid.monitormobile.provider.subscriptions.SubscriptionsManager;
import br.com.arndroid.monitormobile.provider.users.UsersEntity;
import br.com.arndroid.monitormobile.provider.users.UsersManager;

public class SubscriptionsListActivity extends ListActivity
        implements StringListDialog.OnStringSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private SubscriptionsAdapter mAdapter;
    private UsersEntity mCurrentUser;
    private Long mIdInDialog;

    private static final int SUBSCRIPTIONS_LOADER_ID = 1;
    private static final String SUBSCRIPTION_MODE_DIALOG = "SUBSCRIPTION_MODE_DIALOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.subscriptions_list_activity);

        final ActionBar actionBar = getActionBar();
        //noinspection ConstantConditions
        actionBar.setDisplayHomeAsUpEnabled(true);

        mCurrentUser = new UsersManager(this).currentUser();
        mAdapter = new SubscriptionsAdapter(this);
        setListAdapter(mAdapter);
        getLoaderManager().restartLoader(SUBSCRIPTIONS_LOADER_ID, null, this);
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        mIdInDialog = id;
        final SubscriptionsManager manager = new SubscriptionsManager(this);
        final SubscriptionsEntity subscription = manager.subscriptionFromId(mIdInDialog);
        final StringListDialog dialog = new StringListDialog();
        dialog.setTitle(subscription.getAcronymId());
        dialog.setStringListId(R.array.subscription_mode_list);
        dialog.setInitialIndex(subscription.getModeType());
        dialog.show(getFragmentManager(), SUBSCRIPTION_MODE_DIALOG);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case SUBSCRIPTIONS_LOADER_ID:
                return new CursorLoader(this, Contract.Subscriptions.CONTENT_URI, null,
                        Contract.Subscriptions.SUBSCRIBER_ID_SELECTION,
                        new String[]{mCurrentUser.getId().toString()}, Contract.Subscriptions.ACRONYM_ID_ASC);

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

    @Override
    public void onStringSelected(String tag, int chosenIndex) {
        final SubscriptionsManager manager = new SubscriptionsManager(this);
        final SubscriptionsEntity entity = manager.subscriptionFromId(mIdInDialog);
        entity.setModeType(chosenIndex);
        manager.refresh(entity);
    }
}
