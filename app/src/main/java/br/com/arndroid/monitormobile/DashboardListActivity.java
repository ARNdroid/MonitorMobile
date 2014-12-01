package br.com.arndroid.monitormobile;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import br.com.arndroid.monitormobile.app.MonitorMobileApp;
import br.com.arndroid.monitormobile.dashboard.DashboardAdapter;
import br.com.arndroid.monitormobile.dashboard.DashboardPanel;
import br.com.arndroid.monitormobile.dialog.StringListDialog;
import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.users.UsersManager;
import br.com.arndroid.monitormobile.utils.DashboardUtils;
import br.com.arndroid.monitormobile.utils.PreferencesUtils;

public class DashboardListActivity extends ListActivity implements
        StringListDialog.OnStringSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String DASHBOARD_TYPE_DIALOG = "DASHBOARD_TYPE_DIALOG";

    private DashboardAdapter mAdapter;
    private MenuItem mDashboardTypeMenuItem;

    private static final int DASHBOARD_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_list_activity);

        mAdapter = new DashboardAdapter(this);
        mAdapter.setDashboardType(PreferencesUtils.getLastDashboardType(this));
        setListAdapter(mAdapter);

        getLoaderManager().restartLoader(DASHBOARD_LOADER_ID, null, this);
    }

    @Override
    public void onBackPressed() {
        final MonitorMobileApp app = (MonitorMobileApp) getApplication();
        app.setFinishing(true);
        final Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO: implement
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        mDashboardTypeMenuItem = menu.findItem(R.id.action_dashboard_type);
        updateMenuIcons();
        return true;
    }

    private void updateMenuIcons() {
        final int dashboardType = PreferencesUtils.getLastDashboardType(this);
        switch (dashboardType) {
            case DashboardUtils.DASHBOARD_TYPE_FLAG:
                mDashboardTypeMenuItem.setIcon(R.drawable.flag_action);
                break;
            case DashboardUtils.DASHBOARD_TYPE_CLOCK:
                mDashboardTypeMenuItem.setIcon(R.drawable.clock_action);
                break;
            default:
                mDashboardTypeMenuItem.setIcon(R.drawable.flag_and_clock_action);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();

        final Intent intent;

        switch (itemId) {
            case R.id.action_dashboard_type:
                final StringListDialog dialog = new StringListDialog();
                dialog.setTitle("Visualizar por");
                dialog.setStringListId(R.array.dashboard_type_list);
                dialog.setInitialIndex(PreferencesUtils.getLastDashboardType(this));
                dialog.show(getFragmentManager(), DASHBOARD_TYPE_DIALOG);

                return true;

            case R.id.action_subscriptions:
                intent = new Intent(this, SubscriptionsListActivity.class);
                startActivity(intent);
                return true;

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case DASHBOARD_LOADER_ID:
                final UsersManager manager = new UsersManager(this);
                return new CursorLoader(this, Contract.Issues.DASHBOARD_URI, Contract.Issues.DASHBOARD_PROJECTION,
                        Contract.Issues.DASHBOARD_SELECTION, new String[]{manager.getCurrentUser().getId().toString()},
                        null);

            default:
                throw new IllegalArgumentException("Invalid loader id=" + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapDashboardPanel(new DashboardPanel(data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapDashboardPanel(null);
    }

    @Override
    public void onStringSelected(String tag, int chosenIndex) {
        PreferencesUtils.setLastDashboardType(this, chosenIndex);
        updateMenuIcons();
        mAdapter.setDashboardType(chosenIndex);
    }
}
