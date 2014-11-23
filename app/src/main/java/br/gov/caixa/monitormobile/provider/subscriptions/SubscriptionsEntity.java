package br.gov.caixa.monitormobile.provider.subscriptions;

import android.content.ContentValues;
import android.database.Cursor;

import br.gov.caixa.monitormobile.provider.AbstractEntity;
import br.gov.caixa.monitormobile.provider.Contract;

public class SubscriptionsEntity extends AbstractEntity {

    private Long id;
    private Integer modeType;
    private String acronymId;
    private Long subscriberId;

	/*
	 * Implementation
	 */

    public SubscriptionsEntity(Long id, Integer modeType, String acronymId, Long subscriberId) {
        this.id = id;
        this.modeType = modeType;
        this.acronymId = acronymId;
        this.subscriberId = subscriberId;
    }

    public SubscriptionsEntity(SubscriptionsEntity toClone) {
        id = toClone.id;
        modeType = toClone.modeType;
        acronymId = toClone.acronymId;
        subscriberId = toClone.subscriberId;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Contract.Subscriptions._ID, id);
        cv.put(Contract.Subscriptions.MODE_TYPE, modeType);
        cv.put(Contract.Subscriptions.ACRONYM_ID, acronymId);
        cv.put(Contract.Subscriptions.SUBSCRIBER_ID, subscriberId);
        return cv;
    }

    @Override
    public ContentValues toContentValuesIgnoreNulls() {
        ContentValues cv = new ContentValues();
        if (id != null) {
            cv.put(Contract.Subscriptions._ID, id);
        }
        if (modeType != null){
            cv.put(Contract.Subscriptions.MODE_TYPE, modeType);
        }
        if (acronymId != null){
            cv.put(Contract.Subscriptions.ACRONYM_ID, acronymId);
        }
        if (subscriberId != null){
            cv.put(Contract.Subscriptions.SUBSCRIBER_ID, subscriberId);
        }
        return cv;
    }

    @Override
    public void validateOrThrow() {
        if (modeType == null) {
            throwNullValueException(Contract.Subscriptions.MODE_TYPE);
        }
        if (acronymId == null) {
            throwNullValueException(Contract.Subscriptions.ACRONYM_ID);
        }
        if (subscriberId == null) {
            throwNullValueException(Contract.Subscriptions.SUBSCRIBER_ID);
        }
    }

    @Override
    public String getTableName() {
        return Contract.Subscriptions.TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return Contract.Subscriptions._ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;

        } else if (obj instanceof SubscriptionsEntity) {
            SubscriptionsEntity temp = (SubscriptionsEntity) obj;

            // id:
            if (this.id != null) {
                if (!this.id.equals(temp.id))
                    return false;
            } else {
                if (temp.id != null)
                    return false;
            }

            // modeType:
            if (this.modeType != null) {
                if (!this.modeType.equals(temp.modeType))
                    return false;
            } else {
                if (temp.modeType != null)
                    return false;
            }

            // acronymId:
            if (this.acronymId != null) {
                if (!this.acronymId.equals(temp.acronymId))
                    return false;
            } else {
                if (temp.acronymId != null)
                    return false;
            }


            // subscriberId:
            if (this.subscriberId != null) {
                if (!this.subscriberId.equals(temp.subscriberId))
                    return false;
            } else {
                if (temp.subscriberId != null)
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
        result += modeType == null? 0: modeType.hashCode();
        result += acronymId == null? 0: acronymId.hashCode();
        result += subscriberId == null? 0: subscriberId.hashCode();
        return result;
    }

    @Override
    public String toString() {

        return "["
                + Contract.Subscriptions._ID + "=" + id + ", "
                + Contract.Subscriptions.MODE_TYPE + "=" + modeType + ", "
                + Contract.Subscriptions.ACRONYM_ID + "=" + acronymId + ", "
                + Contract.Subscriptions.SUBSCRIBER_ID + "=" + subscriberId
                + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getModeType() {
        return modeType;
    }

    public void setModeType(Integer modeType) {
        this.modeType = modeType;
    }

    public String getAcronymId() {
        return acronymId;
    }

    public void setAcronymId(String acronymId) {
        this.acronymId = acronymId;
    }

    public Long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
    }

    /*
     * Factories
     */
    public static SubscriptionsEntity fromCursor(Cursor cursor) {
        if(cursor == null) {
            throw new IllegalArgumentException("Cursor must be not null.");
        }

        return new SubscriptionsEntity(
                cursor.getColumnIndex(Contract.Subscriptions._ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Subscriptions._ID)),
                cursor.getColumnIndex(Contract.Subscriptions.MODE_TYPE) == -1 ?
                        null : cursor.getInt(cursor.getColumnIndex(Contract.Subscriptions.MODE_TYPE)),
                cursor.getColumnIndex(Contract.Subscriptions.ACRONYM_ID) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Subscriptions.ACRONYM_ID)),
                cursor.getColumnIndex(Contract.Subscriptions.SUBSCRIBER_ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Subscriptions.SUBSCRIBER_ID)));
    }

    @SuppressWarnings("UnusedDeclaration")
    public static SubscriptionsEntity fromJoinInContentValues(ContentValues principal, ContentValues complement) {
        if (principal == null || complement == null) {
            throw new IllegalArgumentException("Principal and complement must be not null.");
        }

        SubscriptionsEntity result = new SubscriptionsEntity(null, null, null, null);

        // Id:
        if (principal.getAsLong(Contract.Subscriptions._ID) != null) {
            result.setId(principal.getAsLong(Contract.Subscriptions._ID));
        } else {
            result.setId(complement.getAsLong(Contract.Subscriptions._ID));
        }

        // Mode type:
        if (principal.getAsInteger(Contract.Subscriptions.MODE_TYPE) != null) {
            result.setModeType(principal.getAsInteger(Contract.Subscriptions.MODE_TYPE));
        } else {
            result.setModeType(complement.getAsInteger(Contract.Subscriptions.MODE_TYPE));
        }

        // Acronym:
        if (principal.getAsString(Contract.Subscriptions.ACRONYM_ID) != null) {
            result.setAcronymId(principal.getAsString(Contract.Subscriptions.ACRONYM_ID));
        } else {
            result.setAcronymId(complement.getAsString(Contract.Subscriptions.ACRONYM_ID));
        }

        // Subscriber id:
        if (principal.getAsLong(Contract.Subscriptions.SUBSCRIBER_ID) != null) {
            result.setSubscriberId(principal.getAsLong(Contract.Subscriptions.SUBSCRIBER_ID));
        } else {
            result.setSubscriberId(complement.getAsLong(Contract.Subscriptions.SUBSCRIBER_ID));
        }

        return result;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static SubscriptionsEntity fromContentValues(ContentValues values) {
        if (values == null) {
            throw new IllegalArgumentException("Values must be not null.");
        }

        return new SubscriptionsEntity(
                values.getAsLong(Contract.Subscriptions._ID),
                values.getAsInteger(Contract.Subscriptions.MODE_TYPE),
                values.getAsString(Contract.Subscriptions.ACRONYM_ID),
                values.getAsLong(Contract.Subscriptions.SUBSCRIBER_ID));
    }
}