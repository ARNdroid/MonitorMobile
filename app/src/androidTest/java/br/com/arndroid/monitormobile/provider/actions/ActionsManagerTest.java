package br.com.arndroid.monitormobile.provider.actions;

import android.database.Cursor;
import android.test.ProviderTestCase2;

import java.util.Date;

import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.Provider;
import br.com.arndroid.monitormobile.utils.TimeStampUtils;

public class ActionsManagerTest extends ProviderTestCase2<Provider> {

    private ActionsManager mManager;

    public ActionsManagerTest() {
        super(Provider.class, Contract.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        // Important: calling the base class implementation of this method
        // where the "magic" of isolation is set up:
        super.setUp();

        // Gets the manager for this test.
        mManager = new ActionsManager(getMockContext());
    }

    public void testRemoveMustDelete() {
        final ActionsEntity entity = new ActionsEntity(null, 1L,
                TimeStampUtils.dateToTimeStamp(new Date()), "Some summary", "Some description", 1L);
        mManager.refresh(entity);
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Actions.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        mManager.remove(entity.getId());

        try {
            c = getMockContentResolver().query(Contract.Actions.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords - 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }
    }

    public void testRefreshMustInsertOrUpdate() {
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Actions.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        final ActionsEntity entity = new ActionsEntity(null, 1L,
                TimeStampUtils.dateToTimeStamp(new Date()), "Some summary", "Some description", 1L);
        mManager.refresh(entity);

        try {
            c = getMockContentResolver().query(Contract.Actions.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords + 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }

        entity.setDescription(entity.getDescription() + " updated");
        mManager.refresh(entity);
        ActionsEntity entityUpdated = mManager.actionsFromId(entity.getId());
        assertEquals(entity, entityUpdated);
    }

    public void testEntityWithNullForNotNullableColumnMustThrow() {
        boolean exceptionThrew = false;
        ActionsEntity entity = new ActionsEntity(null, null,
                TimeStampUtils.dateToTimeStamp(new Date()), "Some summary", "Some description", 1L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new ActionsEntity(null, 1L, null, "Some summary",
                "Some description", 1L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new ActionsEntity(null, 1L,
                TimeStampUtils.dateToTimeStamp(new Date()), null, "Some description", 1L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new ActionsEntity(null, 1L,
                TimeStampUtils.dateToTimeStamp(new Date()), "Some summary", null, 1L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new ActionsEntity(null, 1L,
                TimeStampUtils.dateToTimeStamp(new Date()), "Some summary", "Some description", null);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);
    }
}