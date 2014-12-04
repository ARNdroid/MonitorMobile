package br.com.arndroid.monitormobile.dashboard;

import android.database.Cursor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;

import br.com.arndroid.monitormobile.provider.issues.IssuesEntity;
import br.com.arndroid.monitormobile.utils.IssueUtils;

public class DashboardPanel {

    private static final Logger LOG = LoggerFactory.getLogger(DashboardPanel.class);

    private String[] acronymIdKeySet = null;
    private HashMap<String, DashboardItem> map = null;

    public DashboardPanel(Cursor cursor) {

        LOG.trace("Inside constructor: cursor.getCount()={}", cursor.getCount());

        if (cursor.moveToFirst()) {
            map = new HashMap<String, DashboardItem>();
            do {

                final IssuesEntity entity = IssuesEntity.fromCursor(cursor);
                final String acronymId = entity.getAcronymId();
                final DashboardItem dashboardItem;

                if (!map.containsKey(acronymId)) {
                    dashboardItem = new DashboardItem();
                    dashboardItem.acronymId = acronymId;
                    // NOT correct (this description comes from systems table but stored in issue description by SQL join.
                    // For now we will go with it.
                    dashboardItem.description = entity.getDescription();
                    map.put(acronymId, dashboardItem);
                } else {
                    dashboardItem = map.get(acronymId);
                }

                if (entity.getState() == IssueUtils.STATE_CLOSED) {
                    dashboardItem.closed++;
                } else {
                    dashboardItem.flagAndClockType[entity.getFlagType()][entity.getClockType()]++;
                }

            } while (cursor.moveToNext());

            acronymIdKeySet = map.keySet().toArray(new String[map.size()]);
            Arrays.sort(acronymIdKeySet);
        }
    }

    public String getAcronymIdForPosition(int position) {
        return acronymIdKeySet[position];
    }

    public int getAcronymIdCount() {
        return acronymIdKeySet.length;
    }

    public DashboardItem getDashboardItemForPosition(int position) {
        final String acronymId = acronymIdKeySet[position];
        return map.get(acronymId);
    }

    public String getDescriptionForPosition(int position) {
        final String acronymId = acronymIdKeySet[position];
        final DashboardItem item = map.get(acronymId);
        return item.description;
    }
}
