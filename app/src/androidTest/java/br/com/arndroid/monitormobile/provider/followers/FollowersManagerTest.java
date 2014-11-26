package br.com.arndroid.monitormobile.provider.followers;

import android.database.Cursor;
import android.test.ProviderTestCase2;

import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.Provider;

public class FollowersManagerTest extends ProviderTestCase2<Provider> {

    private FollowersManager mManager;

    public FollowersManagerTest() {
        super(Provider.class, Contract.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        // Important: calling the base class implementation of this method
        // where the "magic" of isolation is set up:
        super.setUp();

        // Gets the manager for this test.
        mManager = new FollowersManager(getMockContext());
    }

    public void testRemoveMustDelete() {
        final FollowersEntity entity = new FollowersEntity(null, 9999L, 9999L);
        mManager.refresh(entity);
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Followers.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        mManager.remove(entity.getId());

        try {
            c = getMockContentResolver().query(Contract.Followers.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords - 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }
    }

    public void testRefreshMustInsertOrUpdate() {
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Followers.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        final FollowersEntity entity = new FollowersEntity(null, 9999L, 9999L);
        mManager.refresh(entity);

        try {
            c = getMockContentResolver().query(Contract.Followers.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords + 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }

        entity.setFollowerId(2L);
        mManager.refresh(entity);
        FollowersEntity entityUpdated = mManager.followersFromId(entity.getId());
        assertEquals(entity, entityUpdated);
    }

    public void testEntityWillCauseConstraintViolationMustReturnsCorrectValue() {
        final Long issueId = 9L;
        final Long followerId = 7L;

        FollowersEntity entity = new FollowersEntity(null, issueId, followerId);
        assertFalse(mManager.entityWillCauseConstraintViolation(entity));

        FollowersEntity entityInDatabase = new FollowersEntity(entity);
        mManager.refresh(entityInDatabase);

        assertTrue(mManager.entityWillCauseConstraintViolation(entity));
        assertFalse(mManager.entityWillCauseConstraintViolation(entityInDatabase));

        entity.setFollowerId(followerId + 1L);
        assertFalse(mManager.entityWillCauseConstraintViolation(entity));
    }

    public void testEntityWithNullForNotNullableColumnMustThrow() {
        boolean exceptionThrew = false;
        FollowersEntity entity = new FollowersEntity(null, null, 1L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new FollowersEntity(null, 1L, null);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);
    }
}