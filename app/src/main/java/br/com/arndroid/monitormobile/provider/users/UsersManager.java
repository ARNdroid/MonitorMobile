package br.com.arndroid.monitormobile.provider.users;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.subscriptions.SubscriptionsEntity;
import br.com.arndroid.monitormobile.provider.subscriptions.SubscriptionsManager;
import br.com.arndroid.monitormobile.provider.systems.SystemsEntity;
import br.com.arndroid.monitormobile.provider.systems.SystemsManager;
import br.com.arndroid.monitormobile.utils.PreferencesUtils;

public class UsersManager {
    private static final Logger LOG = LoggerFactory.getLogger(UsersManager.class);

    final private Context mContext;

    public UsersManager(Context context) {
        mContext = context;
    }

    public UsersEntity userFromId(Long id) {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(ContentUris.withAppendedId(
                    Contract.Users.CONTENT_URI, id), null, null, null, null);
            if(c.getCount() > 0) {
                c.moveToFirst();
                return UsersEntity.fromCursor(c);
            } else {
                return null;
            }
        } finally {
            if (c != null) c.close();
        }
    }

    public UsersEntity userFromShortName(String shortName) {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(Contract.Users.CONTENT_URI, null,
                    Contract.Users.SHORT_NAME_SELECTION, new String[]{shortName}, null);
            if(c.getCount() > 0) {
                c.moveToFirst();
                return UsersEntity.fromCursor(c);
            } else {
                return null;
            }
        } finally {
            if (c != null) c.close();
        }
    }

    public UsersEntity getCurrentUser() {
        UsersEntity result = null;
        if (PreferencesUtils.isCurrentUserRegistered(mContext)) {
            result = userFromShortName(PreferencesUtils.getCurrentUserShortName(mContext));
        }
        return result;
    }

    public List<String> getAllUsersShortName(List<Long> excludedIdList) {
        List<String> result = new ArrayList<String>();
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(Contract.Users.CONTENT_URI, null, null, null, Contract.Users.SHORT_NAME_ASC);
            if(c.moveToFirst()) {
                do {
                    final UsersEntity entity = UsersEntity.fromCursor(c);
                    boolean include = true;
                    for (Long id : excludedIdList) {
                        if (entity.getId().equals(id)) {
                            include = false;
                            break;
                        }
                    }
                    if (include) {
                        result.add(entity.getShortName());
                    }
                } while (c.moveToNext());
            }
        } finally {
            if (c != null) c.close();
        }
        return result;
    }

    public void refresh(UsersEntity entity) {

        entity.validateOrThrow();

        final Long id = entity.getId();
        if(id == null) {
            LOG.trace("About to insert entity={} with Uri={}", entity, Contract.Users.CONTENT_URI);
            final Uri resultUri = mContext.getContentResolver().insert(Contract.Users.CONTENT_URI,
                    entity.toContentValues());
            entity.setId(Long.parseLong(resultUri.getLastPathSegment()));
            LOG.trace("Entity inserted with id={}", entity.getId());
        } else {
            mContext.getContentResolver().update(ContentUris.withAppendedId(Contract.Users.CONTENT_URI, id),
                    entity.toContentValues(), null, null);
            LOG.trace("Entity updated: {}", entity);
        }
    }

    public void remove(Long id) {
        mContext.getContentResolver().delete(ContentUris.withAppendedId(Contract.Users.CONTENT_URI,
                id), null, null);
    }

    public boolean entityWillCauseConstraintViolation(UsersEntity entity) {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(Contract.Users.CONTENT_URI,
                    Contract.Users.ID_PROJECTION, Contract.Users.SHORT_NAME_SELECTION,
                    new String[]{entity.getShortName()}, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                final UsersEntity resultEntity = UsersEntity.fromCursor(c);
                return !resultEntity.getId().equals(entity.getId());
            }
        } finally {
            if (c != null) c.close();
        }
        return false;
    }

    public void insertCurrentUserAndRelationships(UsersEntity currentUser) {
        // Insert:
        refresh(currentUser);

        // Subscriptions for all systems:
        final SubscriptionsManager subscriptionsManager = new SubscriptionsManager(mContext);
        final SystemsManager systemsManager = new SystemsManager(mContext);
        List<SystemsEntity> allSystems = systemsManager.allSystems();
        for (SystemsEntity system : allSystems) {
            final int subscriptionMode;
            final String acronymId = system.getAcronymId();
            if ("SIXYZ".equals(acronymId)) {
                subscriptionMode = 1;
            } else if ("SICLI".equals(acronymId)) {
                subscriptionMode = 2;
            } else if ("SICID".equals(acronymId)) {
                subscriptionMode = 1;
            } else if ("SIBEC".equals(acronymId)) {
                subscriptionMode = 1;
            } else if ("SICPF".equals(acronymId)) {
                subscriptionMode = 0;
            } else if ("SIISO".equals(acronymId)) {
                subscriptionMode = 2;
            } else {
                // For all 'new' systems:
                subscriptionMode = 0;
            }
            subscriptionsManager.refresh(new SubscriptionsEntity(null, subscriptionMode, acronymId,
                    currentUser.getId()));
        }
    }

    public UsersEntity getUserFromShortName(String shortName) {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(Contract.Users.CONTENT_URI, null,
                    Contract.Users.SHORT_NAME_SELECTION, new String[] {shortName}, null);
            if(c.getCount() > 0) {
                c.moveToFirst();
                return UsersEntity.fromCursor(c);
            } else {
                return null;
            }
        } finally {
            if (c != null) c.close();
        }
    }
}