package br.gov.caixa.monitormobile.provider.issues;

import android.database.Cursor;
import android.test.ProviderTestCase2;

import br.gov.caixa.monitormobile.provider.Contract;
import br.gov.caixa.monitormobile.provider.Provider;

public class IssuesManagerTest extends ProviderTestCase2<Provider> {

    private IssuesManager mManager;

    public IssuesManagerTest() {
        super(Provider.class, Contract.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        // Important: calling the base class implementation of this method
        // where the "magic" of isolation is set up:
        super.setUp();

        // Gets the manager for this test.
        mManager = new IssuesManager(getMockContext());
    }

    public void testRemoveMustDelete() {
        final IssuesEntity entity = new IssuesEntity(null, "SIXYZ", "201411221716", 0, 0, 0,
                "Some summary", "Some description", 0L, 0L);
        mManager.refresh(entity);
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Issues.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        mManager.remove(entity.getId());

        try {
            c = getMockContentResolver().query(Contract.Issues.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords - 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }
    }

    public void testRefreshMustInsertOrUpdate() {
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Issues.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        final IssuesEntity entity = new IssuesEntity(null, "SIXYZ", "201411221716", 0, 0, 0,
                "Some summary", "Some description", 0L, 0L);
        mManager.refresh(entity);

        try {
            c = getMockContentResolver().query(Contract.Issues.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords + 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }

        entity.setDescription(entity.getDescription() + " updated");
        mManager.refresh(entity);
        IssuesEntity entityUpdated = mManager.issuesFromId(entity.getId());
        assertEquals(entity, entityUpdated);
    }

    public void testEntityWithNullForNotNullableColumnMustThrow() {
        boolean exceptionThrew = false;
        IssuesEntity entity = new IssuesEntity(null, null, "201411221716", 0, 0, 0,
                "Some summary", "Some description", 0L, 0L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new IssuesEntity(null, "SIXYZ", null, 0, 0, 0,
                "Some summary", "Some description", 0L, 0L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new IssuesEntity(null, "SIXYZ", "201411221716", null, 0, 0,
                "Some summary", "Some description", 0L, 0L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new IssuesEntity(null, "SIXYZ", "201411221716", 0, null, 0,
                "Some summary", "Some description", 0L, 0L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new IssuesEntity(null, "SIXYZ", "201411221716", 0, 0, null,
                "Some summary", "Some description", 0L, 0L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new IssuesEntity(null, "SIXYZ", "201411221716", 0, 0, 0,
                null, "Some description", 0L, 0L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new IssuesEntity(null, "SIXYZ", "201411221716", 0, 0, 0,
                "Some summary", null, 0L, 0L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new IssuesEntity(null, "SIXYZ", "201411221716", 0, 0, 0,
                "Some summary", "Some description", null, 0L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new IssuesEntity(null, "SIXYZ", "201411221716", 0, 0, 0,
                "Some summary", "Some description", 0L, null);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);
    }
}