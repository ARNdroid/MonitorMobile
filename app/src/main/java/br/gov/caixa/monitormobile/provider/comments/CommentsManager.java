package br.gov.caixa.monitormobile.provider.comments;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.caixa.monitormobile.provider.Contract;

public class CommentsManager {

    private static final Logger LOG = LoggerFactory.getLogger(CommentsManager.class);

    final private Context mContext;

    public CommentsManager(Context context) {
        mContext = context;
    }

    public CommentsEntity commentsFromId(Long id) {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(ContentUris.withAppendedId(
                    Contract.Comments.CONTENT_URI, id), null, null, null, null);
            if(c.getCount() > 0) {
                c.moveToFirst();
                return CommentsEntity.fromCursor(c);
            } else {
                return null;
            }
        } finally {
            if (c != null) c.close();
        }
    }

    public void refresh(CommentsEntity entity) {

        entity.validateOrThrow();

        final Long id = entity.getId();
        if(id == null) {
            LOG.trace("About to insert entity={} with Uri={}", entity, Contract.Comments.CONTENT_URI);
            final Uri resultUri = mContext.getContentResolver().insert(Contract.Comments.CONTENT_URI,
                    entity.toContentValues());
            entity.setId(Long.parseLong(resultUri.getLastPathSegment()));
            LOG.trace("Entity inserted with id={}", entity.getId());
        } else {
            mContext.getContentResolver().update(ContentUris.withAppendedId(Contract.Comments.CONTENT_URI, id),
                    entity.toContentValues(), null, null);
            LOG.trace("Entity updated: {}", entity);
        }
    }

    public void remove(Long id) {
        mContext.getContentResolver().delete(ContentUris.withAppendedId(Contract.Comments.CONTENT_URI,
                id), null, null);
    }
}