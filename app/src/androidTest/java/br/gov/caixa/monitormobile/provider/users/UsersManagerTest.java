package br.gov.caixa.monitormobile.provider.users;

import android.database.Cursor;
import android.test.ProviderTestCase2;

import br.gov.caixa.monitormobile.provider.Contract;
import br.gov.caixa.monitormobile.provider.Provider;

public class UsersManagerTest extends ProviderTestCase2<Provider> {

    private UsersManager mManager;

    public UsersManagerTest() {
        super(Provider.class, Contract.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        // Important: calling the base class implementation of this method
        // where the "magic" of isolation is set up:
        super.setUp();

        // Gets the manager for this test.
        mManager = new UsersManager(getMockContext());
    }

    public void testRemoveMustDelete() {
        final String shortName = "Short Name";
        final UsersEntity entity = new UsersEntity(null, shortName);
        mManager.refresh(entity);
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Users.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        mManager.remove(entity.getId());

        try {
            c = getMockContentResolver().query(Contract.Users.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords - 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }
    }

    public void testRefreshMustInsertOrUpdate() {
        final String shortName = "A Short Name";
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Users.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        final UsersEntity entity = new UsersEntity(null, shortName);
        mManager.refresh(entity);

        try {
            c = getMockContentResolver().query(Contract.Users.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords + 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }

        entity.setShortName(entity.getShortName() + " Updated");
        mManager.refresh(entity);
        UsersEntity entityUpdated = mManager.userFromId(entity.getId());
        assertEquals(entity, entityUpdated);
    }
}