package br.com.arndroid.monitormobile.provider.subscriptions;

import android.database.Cursor;
import android.test.ProviderTestCase2;

import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.Provider;
import br.com.arndroid.monitormobile.utils.SubscriptionsUtils;

public class SubscriptionsManagerTest extends ProviderTestCase2<Provider> {

    private SubscriptionsManager mManager;

    public SubscriptionsManagerTest() {
        super(Provider.class, Contract.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        // Important: calling the base class implementation of this method
        // where the "magic" of isolation is set up:
        super.setUp();

        // Gets the manager for this test.
        mManager = new SubscriptionsManager(getMockContext());
    }

    public void testRemoveMustDelete() {
        final SubscriptionsEntity entity = new SubscriptionsEntity(null,
                SubscriptionsUtils.MODE_TYPE_SUBSCRIBE, "SI003", 3L);
        mManager.refresh(entity);
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Subscriptions.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        mManager.remove(entity.getId());

        try {
            c = getMockContentResolver().query(Contract.Subscriptions.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords - 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }
    }

    public void testRefreshMustInsertOrUpdate() {
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Subscriptions.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        final SubscriptionsEntity entity = new SubscriptionsEntity(null,
                SubscriptionsUtils.MODE_TYPE_SUBSCRIBE, "SI003", 1L);
        mManager.refresh(entity);

        try {
            c = getMockContentResolver().query(Contract.Subscriptions.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords + 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }

        entity.setModeType(SubscriptionsUtils.MODE_TYPE_SUBSCRIBE_AND_FOLLOW);
        mManager.refresh(entity);
        SubscriptionsEntity entityUpdated = mManager.subscriptionFromId(entity.getId());
        assertEquals(entity, entityUpdated);
    }

    public void testEntityWillCauseConstraintViolationMustReturnsCorrectValue() {
        final String acronymId = "SIDUP";
        final Long subscriberId = 7L;

        SubscriptionsEntity entity = new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,
                acronymId, subscriberId);
        assertFalse(mManager.entityWillCauseConstraintViolation(entity));

        SubscriptionsEntity entityInDatabase = new SubscriptionsEntity(entity);
        mManager.refresh(entityInDatabase);

        assertTrue(mManager.entityWillCauseConstraintViolation(entity));
        assertFalse(mManager.entityWillCauseConstraintViolation(entityInDatabase));

        entity.setAcronymId(entity.getAcronymId() + "#Updated");
        assertFalse(mManager.entityWillCauseConstraintViolation(entity));
    }
    
    public void testEntityWithNullForNotNullableColumnMustThrow() {
        boolean exceptionThrew = false;
        SubscriptionsEntity entity = new SubscriptionsEntity(null,
                null, "SI001", 1L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new SubscriptionsEntity(null,
                SubscriptionsUtils.MODE_TYPE_SUBSCRIBE, null, 1L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new SubscriptionsEntity(null,
                SubscriptionsUtils.MODE_TYPE_SUBSCRIBE, "SI001", null);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);
    }
}