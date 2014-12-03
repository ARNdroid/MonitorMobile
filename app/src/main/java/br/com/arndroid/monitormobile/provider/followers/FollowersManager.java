package br.com.arndroid.monitormobile.provider.followers;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.users.UsersManager;

public class FollowersManager {

    private static final Logger LOG = LoggerFactory.getLogger(FollowersManager.class);

    final private Context mContext;

    public FollowersManager(Context context) {
        mContext = context;
    }

    public FollowersEntity followersFromId(Long id) {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(ContentUris.withAppendedId(
                    Contract.Followers.CONTENT_URI, id), null, null, null, null);
            if(c.getCount() > 0) {
                c.moveToFirst();
                return FollowersEntity.fromCursor(c);
            } else {
                return null;
            }
        } finally {
            if (c != null) c.close();
        }
    }

    public String humanPhraseFromIssueId(Long issueId) {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(Contract.Followers.CONTENT_URI, null,
                    Contract.Followers.ISSUE_ID_SELECTION, new String[] {issueId.toString()}, null);
            final int count = c.getCount();
            if(count > 0) {
                c.moveToFirst();
                final FollowersEntity entity = FollowersEntity.fromCursor(c);
                final UsersManager manager = new UsersManager(mContext);
                String firstFollowerName = manager.userFromId(entity.getFollowerId()).getShortName();
                if (count > 2) {
                    firstFollowerName += " +" + (count - 1) + " pessoas";
                } else if (count > 1) {
                    firstFollowerName += " +1 pessoa";
                }
                return firstFollowerName;
            } else {
                return "(sem seguidores)";
            }
        } finally {
            if (c != null) c.close();
        }
    }

    public void refresh(FollowersEntity entity) {

        entity.validateOrThrow();

        final Long id = entity.getId();
        if(id == null) {
            LOG.trace("About to insert entity={} with Uri={}", entity, Contract.Followers.CONTENT_URI);
            final Uri resultUri = mContext.getContentResolver().insert(Contract.Followers.CONTENT_URI,
                    entity.toContentValues());
            entity.setId(Long.parseLong(resultUri.getLastPathSegment()));
            LOG.trace("Entity inserted with id={}", entity.getId());
        } else {
            mContext.getContentResolver().update(ContentUris.withAppendedId(Contract.Followers.CONTENT_URI, id),
                    entity.toContentValues(), null, null);
            LOG.trace("Entity updated: {}", entity);
        }
    }

    public void remove(Long id) {
        mContext.getContentResolver().delete(ContentUris.withAppendedId(Contract.Followers.CONTENT_URI,
                id), null, null);
    }

    public boolean entityWillCauseConstraintViolation(FollowersEntity entity) {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(Contract.Followers.CONTENT_URI,
                    Contract.Followers.ID_PROJECTION, Contract.Followers.ISSUE_ID_AND_FOLLOWER_ID_SELECTION,
                    new String[]{entity.getIssueId().toString(), entity.getFollowerId().toString()}, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                final FollowersEntity resultEntity = FollowersEntity.fromCursor(c);
                return !resultEntity.getId().equals(entity.getId());
            }
        } finally {
            if (c != null) c.close();
        }
        return false;
    }
}