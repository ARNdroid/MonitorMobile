package br.com.arndroid.monitormobile.provider.systems;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.arndroid.monitormobile.provider.Contract;

public class SystemsManager {
    private static final Logger LOG = LoggerFactory.getLogger(SystemsManager.class);

    final private Context mContext;

    public SystemsManager(Context context) {
        mContext = context;
    }

    public SystemsEntity systemFromId(Long id) {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(ContentUris.withAppendedId(
                    Contract.Systems.CONTENT_URI, id), null, null, null, null);
            if(c.getCount() > 0) {
                c.moveToFirst();
                return SystemsEntity.fromCursor(c);
            } else {
                return null;
            }
        } finally {
            if (c != null) c.close();
        }
    }

    public void refresh(SystemsEntity entity) {

        entity.validateOrThrow();

        final Long id = entity.getId();
        if(id == null) {
            LOG.trace("About to insert entity={} with Uri={}", entity, Contract.Systems.CONTENT_URI);
            final Uri resultUri = mContext.getContentResolver().insert(Contract.Systems.CONTENT_URI,
                    entity.toContentValues());
            entity.setId(Long.parseLong(resultUri.getLastPathSegment()));
            LOG.trace("Entity inserted with id={}", entity.getId());
        } else {
            mContext.getContentResolver().update(ContentUris.withAppendedId(Contract.Systems.CONTENT_URI, id),
                    entity.toContentValues(), null, null);
            LOG.trace("Entity updated: {}", entity);
        }
    }

    public void remove(Long id) {
        mContext.getContentResolver().delete(ContentUris.withAppendedId(Contract.Systems.CONTENT_URI,
                id), null, null);
    }

    public boolean entityWillCauseConstraintViolation(SystemsEntity entity) {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(Contract.Systems.CONTENT_URI,
                    Contract.Systems.ID_PROJECTION, Contract.Systems.ACRONYM_ID_SELECTION,
                    new String[]{entity.getAcronymId()}, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                final SystemsEntity resultEntity = SystemsEntity.fromCursor(c);
                return !resultEntity.getId().equals(entity.getId());
            }
        } finally {
            if (c != null) c.close();
        }
        return false;
    }
}