package br.gov.caixa.monitormobile.provider.xs;

import android.content.ContentValues;
import android.database.Cursor;

import br.gov.caixa.monitormobile.provider.AbstractEntity;
import br.gov.caixa.monitormobile.provider.Contract;

public class XsEntity extends AbstractEntity {

    private Long id;
    private Long actionId;
    private Long likerId;

	/*
	 * Implementation
	 */

    public XsEntity(Long id, Long actionId, Long likerId) {
        this.id = id;
        this.actionId = actionId;
        this.likerId = likerId;
    }

    public XsEntity(XsEntity toClone) {
        id = toClone.id;
        actionId = toClone.actionId;
        likerId = toClone.likerId;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Contract.Xs._ID, id);
        cv.put(Contract.Xs.ACTION_ID, actionId);
        cv.put(Contract.Xs.LIKER_ID, likerId);
        return cv;
    }

    @Override
    public ContentValues toContentValuesIgnoreNulls() {
        ContentValues cv = new ContentValues();
        if (id != null) {
            cv.put(Contract.Xs._ID, id);
        }
        if (actionId != null){
            cv.put(Contract.Xs.ACTION_ID, actionId);
        }
        if (likerId != null){
            cv.put(Contract.Xs.LIKER_ID, likerId);
        }
        return cv;
    }

    @Override
    public void validateOrThrow() {
        if (actionId == null) {
            throwNullValueException(Contract.Xs.ACTION_ID);
        }
        if (likerId == null) {
            throwNullValueException(Contract.Xs.LIKER_ID);
        }
    }

    @Override
    public String getTableName() {
        return Contract.Xs.TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return Contract.Xs._ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;

        } else if (obj instanceof XsEntity) {
            XsEntity temp = (XsEntity) obj;

            // id:
            if (this.id != null) {
                if (!this.id.equals(temp.id))
                    return false;
            } else {
                if (temp.id != null)
                    return false;
            }

            // actionId:
            if (this.actionId != null) {
                if (!this.actionId.equals(temp.actionId))
                    return false;
            } else {
                if (temp.actionId != null)
                    return false;
            }


            // likerId:
            if (this.likerId != null) {
                if (!this.likerId.equals(temp.likerId))
                    return false;
            } else {
                if (temp.likerId != null)
                    return false;
            }

            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result;
        result = id == null? 0 : id.hashCode();
        result += actionId == null? 0: actionId.hashCode();
        result += likerId == null? 0: likerId.hashCode();
        return result;
    }

    @Override
    public String toString() {

        return "["
                + Contract.Xs._ID + "=" + id + ", "
                + Contract.Xs.ACTION_ID + "=" + actionId + ", "
                + Contract.Xs.LIKER_ID + "=" + likerId
                + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getLikerId() {
        return likerId;
    }

    public void setLikerId(Long likerId) {
        this.likerId = likerId;
    }

    /*
     * Factories
     */
    public static XsEntity fromCursor(Cursor cursor) {
        if(cursor == null) {
            throw new IllegalArgumentException("Cursor must be not null.");
        }

        return new XsEntity(
                cursor.getColumnIndex(Contract.Xs._ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Xs._ID)),
                cursor.getColumnIndex(Contract.Xs.ACTION_ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Xs.ACTION_ID)),
                cursor.getColumnIndex(Contract.Xs.LIKER_ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Xs.LIKER_ID)));
    }

    @SuppressWarnings("UnusedDeclaration")
    public static XsEntity fromJoinInContentValues(ContentValues principal, ContentValues complement) {
        if (principal == null || complement == null) {
            throw new IllegalArgumentException("Principal and complement must be not null.");
        }

        XsEntity result = new XsEntity(null, null, null);

        // Id:
        if (principal.getAsLong(Contract.Xs._ID) != null) {
            result.setId(principal.getAsLong(Contract.Xs._ID));
        } else {
            result.setId(complement.getAsLong(Contract.Xs._ID));
        }

        // Action id:
        if (principal.getAsLong(Contract.Xs.ACTION_ID) != null) {
            result.setActionId(principal.getAsLong(Contract.Xs.ACTION_ID));
        } else {
            result.setActionId(complement.getAsLong(Contract.Xs.ACTION_ID));
        }

        // Liker id:
        if (principal.getAsLong(Contract.Xs.LIKER_ID) != null) {
            result.setLikerId(principal.getAsLong(Contract.Xs.LIKER_ID));
        } else {
            result.setLikerId(complement.getAsLong(Contract.Xs.LIKER_ID));
        }

        return result;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static XsEntity fromContentValues(ContentValues values) {
        if (values == null) {
            throw new IllegalArgumentException("Values must be not null.");
        }

        return new XsEntity(
                values.getAsLong(Contract.Xs._ID),
                values.getAsLong(Contract.Xs.ACTION_ID),
                values.getAsLong(Contract.Xs.LIKER_ID));
    }
}