package br.com.arndroid.monitormobile.provider.comments;

import android.database.Cursor;
import android.test.ProviderTestCase2;

import java.util.Date;

import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.Provider;
import br.com.arndroid.monitormobile.utils.TimeStampUtils;

public class CommentsManagerTest extends ProviderTestCase2<Provider> {

    private CommentsManager mManager;

    public CommentsManagerTest() {
        super(Provider.class, Contract.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        // Important: calling the base class implementation of this method
        // where the "magic" of isolation is set up:
        super.setUp();

        // Gets the manager for this test.
        mManager = new CommentsManager(getMockContext());
    }

    public void testRemoveMustDelete() {
        final CommentsEntity entity = new CommentsEntity(null, 1L,
                TimeStampUtils.dateToTimeStamp(new Date()), "Some description", 1L);
        mManager.refresh(entity);
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Comments.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        mManager.remove(entity.getId());

        try {
            c = getMockContentResolver().query(Contract.Comments.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords - 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }
    }

    public void testRefreshMustInsertOrUpdate() {
        int totalRecords = -1;
        Cursor c = null;
        try {
            c = getMockContentResolver().query(Contract.Comments.CONTENT_URI, null, null, null, null);
            totalRecords = c.getCount();
        } finally {
            if (c != null) c.close();
        }

        final CommentsEntity entity = new CommentsEntity(null, 1L,
                TimeStampUtils.dateToTimeStamp(new Date()), "Some description", 1L);
        mManager.refresh(entity);

        try {
            c = getMockContentResolver().query(Contract.Comments.CONTENT_URI, null, null, null, null);
            assertEquals(totalRecords + 1, c.getCount());
        } finally {
            if (c != null) c.close();
        }

        entity.setDescription(entity.getDescription() + " updated");
        mManager.refresh(entity);
        CommentsEntity entityUpdated = mManager.commentsFromId(entity.getId());
        assertEquals(entity, entityUpdated);
    }

    public void testEntityWithNullForNotNullableColumnMustThrow() {
        boolean exceptionThrew = false;
        CommentsEntity entity = new CommentsEntity(null, null,
                TimeStampUtils.dateToTimeStamp(new Date()), "Some description", 1L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new CommentsEntity(null, 1L, null, "Some description", 1L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new CommentsEntity(null, 1L,
                TimeStampUtils.dateToTimeStamp(new Date()), null, 1L);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);

        exceptionThrew = false;
        entity = new CommentsEntity(null, 1L,
                TimeStampUtils.dateToTimeStamp(new Date()), "Some description", null);
        try {
            mManager.refresh(entity);
        } catch (Contract.TargetException e) {
            exceptionThrew = true;
        }
        assertTrue("We expected a exception here.", exceptionThrew);
    }
}