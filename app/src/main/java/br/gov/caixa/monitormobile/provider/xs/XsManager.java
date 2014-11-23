package br.gov.caixa.monitormobile.provider.xs;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.caixa.monitormobile.provider.Contract;

public class XsManager {

    private static final Logger LOG = LoggerFactory.getLogger(XsManager.class);

    final private Context mContext;

    public XsManager(Context context) {
        mContext = context;
    }

    public XsEntity xsFromId(Long id) {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(ContentUris.withAppendedId(
                    Contract.Xs.CONTENT_URI, id), null, null, null, null);
            if(c.getCount() > 0) {
                c.moveToFirst();
                return XsEntity.fromCursor(c);
            } else {
                return null;
            }
        } finally {
            if (c != null) c.close();
        }
    }

    public void refresh(XsEntity entity) {

        entity.validateOrThrow();

        final Long id = entity.getId();
        if(id == null) {
            LOG.trace("About to insert entity={} with Uri={}", entity, Contract.Xs.CONTENT_URI);
            final Uri resultUri = mContext.getContentResolver().insert(Contract.Xs.CONTENT_URI,
                    entity.toContentValues());
            entity.setId(Long.parseLong(resultUri.getLastPathSegment()));
            LOG.trace("Entity inserted with id={}", entity.getId());
        } else {
            mContext.getContentResolver().update(ContentUris.withAppendedId(Contract.Xs.CONTENT_URI, id),
                    entity.toContentValues(), null, null);
            LOG.trace("Entity updated: {}", entity);
        }
    }

    public void remove(Long id) {
        mContext.getContentResolver().delete(ContentUris.withAppendedId(Contract.Xs.CONTENT_URI,
                id), null, null);
    }

    public boolean entityWillCauseConstraintViolation(XsEntity entity) {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(Contract.Xs.CONTENT_URI,
                    Contract.Xs.ID_PROJECTION, Contract.Xs.ACTION_ID_AND_LIKER_ID_SELECTION,
                    new String[]{entity.getActionId().toString(), entity.getLikerId().toString()}, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                final XsEntity resultEntity = XsEntity.fromCursor(c);
                return !resultEntity.getId().equals(entity.getId());
            }
        } finally {
            if (c != null) c.close();
        }
        return false;
    }
}