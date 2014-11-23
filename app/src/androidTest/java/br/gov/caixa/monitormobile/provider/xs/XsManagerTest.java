package br.gov.caixa.monitormobile.provider.xs;

import android.database.Cursor;
import android.test.ProviderTestCase2;

import br.gov.caixa.monitormobile.provider.Contract;
import br.gov.caixa.monitormobile.provider.Provider;

public class XsManagerTest extends ProviderTestCase2<Provider> {

    private XsManager mManager;

    public XsManagerTest() {
        super(Provider.class, Contract.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        // Important: calling the base class implementation of this method
        // where the "magic" of isolation is set up:
        super.setUp();

        // Gets the manager for this test.
        mManager = new XsManager(getMockContext());
    }

    public void testRemoveMustDelete() {
        final XsEntity entity = new XsEntity(null, 7L, 7L);
        mManager.refresh(entity);
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Xs.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        mManager.remove(entity.getId());

        try {
            c = getMockContentResolver().query(Contract.Xs.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords - 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }
    }

    public void testRefreshMustInsertOrUpdate() {
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Xs.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        final XsEntity entity = new XsEntity(null, 7L, 7L);
        mManager.refresh(entity);

        try {
            c = getMockContentResolver().query(Contract.Xs.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords + 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }

        entity.setLikerId(2L);
        mManager.refresh(entity);
        XsEntity entityUpdated = mManager.xsFromId(entity.getId());
        assertEquals(entity, entityUpdated);
    }

    public void testEntityWillCauseConstraintViolationMustReturnsCorrectValue() {
        final Long actionId = 9L;
        final Long likerId = 7L;

        XsEntity entity = new XsEntity(null, actionId, likerId);
        assertFalse(mManager.entityWillCauseConstraintViolation(entity));

        XsEntity entityInDatabase = new XsEntity(entity);
        mManager.refresh(entityInDatabase);

        assertTrue(mManager.entityWillCauseConstraintViolation(entity));
        assertFalse(mManager.entityWillCauseConstraintViolation(entityInDatabase));

        entity.setLikerId(likerId + 1L);
        assertFalse(mManager.entityWillCauseConstraintViolation(entity));
    }

    public void testEntityWithNullForNotNullableColumnMustThrow() {
        boolean exceptionThrew = false;
        XsEntity entity = new XsEntity(null, null, 1L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new XsEntity(null, 1L, null);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);
    }
}