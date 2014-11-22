package br.gov.caixa.monitormobile.provider.systems;

import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.test.ProviderTestCase2;

import br.gov.caixa.monitormobile.provider.Contract;
import br.gov.caixa.monitormobile.provider.Provider;
import br.gov.caixa.monitormobile.provider.systems.SystemsEntity;
import br.gov.caixa.monitormobile.provider.users.UsersEntity;

public class SystemsManagerTest extends ProviderTestCase2<Provider> {

    private SystemsManager mManager;

    public SystemsManagerTest() {
        super(Provider.class, Contract.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        // Important: calling the base class implementation of this method
        // where the "magic" of isolation is set up:
        super.setUp();

        // Gets the manager for this test.
        mManager = new SystemsManager(getMockContext());
    }

    public void testRemoveMustDelete() {
        final String acronymId = "SIXYZ";
        final String description = "Description for SIXYZ";
        final SystemsEntity entity = new SystemsEntity(null, acronymId, description);
        mManager.refresh(entity);
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Systems.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        mManager.remove(entity.getId());

        try {
            c = getMockContentResolver().query(Contract.Systems.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords - 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }
    }

    public void testRefreshMustInsertOrUpdate() {
        final String acronymId = "SIXYZ";
        final String description = "Description for SIXYZ";

        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Systems.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        final SystemsEntity entity = new SystemsEntity(null, acronymId, description);
        mManager.refresh(entity);

        try {
            c = getMockContentResolver().query(Contract.Systems.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords + 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }

        entity.setDescription(entity.getDescription() + " updated");
        mManager.refresh(entity);
        SystemsEntity entityUpdated = mManager.systemFromId(entity.getId());
        assertEquals(entity, entityUpdated);
    }

    public void testEntityDuplicatedAcronymIdMustThrow() {
        final String acronymId = "SIXYZ";
        final String description = "Some description for SIXYZ";

        SystemsEntity entityInDatabase = new SystemsEntity(null, acronymId, description);
        mManager.refresh(entityInDatabase);

        SystemsEntity duplicatedShortName = new SystemsEntity(null, acronymId, description);
        try {
            mManager.refresh(duplicatedShortName);
        } catch (SQLiteConstraintException e) {
            assertTrue(true);
            return;
        }
        assertTrue("We expected a exception here.", false);
    }

    public void testEntityWillCauseConstraintViolationMustReturnsCorrectValue() {
        final String acronymId = "SIXYZ";
        final String description = "Some description for SIXYZ";

        SystemsEntity entity = new SystemsEntity(null, acronymId, description);
        assertFalse(mManager.entityWillCauseConstraintViolation(entity));

        SystemsEntity entityInDatabase = new SystemsEntity(entity);
        mManager.refresh(entityInDatabase);

        assertTrue(mManager.entityWillCauseConstraintViolation(entity));
        assertFalse(mManager.entityWillCauseConstraintViolation(entityInDatabase));

        entity.setAcronymId(entity.getAcronymId() + " Updated");
        assertFalse(mManager.entityWillCauseConstraintViolation(entity));
    }

    public void testEntityWithNullForNotNullableColumnMustThrow() {
        boolean exceptionThrew = false;
        SystemsEntity entity = new SystemsEntity(null, null, "Some description");
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new SystemsEntity(null, "Some acronym id", null);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);
    }
}
