package br.com.arndroid.monitormobile.provider.users;

import android.content.ContentValues;
import android.database.Cursor;

import br.com.arndroid.monitormobile.provider.AbstractEntity;
import br.com.arndroid.monitormobile.provider.Contract;

public class UsersEntity extends AbstractEntity {
    private Long id;
    private String shortName;

	/*
	 * Implementation
	 */

    public UsersEntity(Long id, String shortName) {
        this.id = id;
        this.shortName = shortName;
    }

    public UsersEntity(UsersEntity toClone) {
        id = toClone.id;
        shortName = toClone.shortName;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Contract.Users._ID, id);
        cv.put(Contract.Users.SHORT_NAME, shortName);
        return cv;
    }

    @Override
    public ContentValues toContentValuesIgnoreNulls() {
        ContentValues cv = new ContentValues();
        if (id != null) {
            cv.put(Contract.Users._ID, id);
        }
        if (shortName != null) {
            cv.put(Contract.Users.SHORT_NAME, shortName);
        }
        return cv;
    }

    @Override
    public void validateOrThrow() {

        if (shortName == null) {
            throwNullValueException(Contract.Users.SHORT_NAME);
        }
    }

    @Override
    public String getTableName() {
        return Contract.Users.TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return Contract.Users._ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;

        } else if (obj instanceof UsersEntity) {
            UsersEntity temp = (UsersEntity) obj;

            // id:
            if (this.id != null) {
                if (!this.id.equals(temp.id))
                    return false;
            } else {
                if (temp.id != null)
                    return false;
            }

            // shortName:
            if (this.shortName != null) {
                if (!this.shortName.equals(temp.shortName))
                    return false;
            } else {
                if (temp.shortName != null)
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
        result += shortName == null? 0: shortName.hashCode();
        return result;
    }

    @Override
    public String toString() {

        return "["
                + Contract.Users._ID + "=" + id + ", "
                + Contract.Users.SHORT_NAME + "=" + shortName
                + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /*
     * Factories
     */
    public static UsersEntity fromCursor(Cursor cursor) {
        if(cursor == null) {
            throw new IllegalArgumentException("Cursor must be not null.");
        }

        return new UsersEntity(
                cursor.getColumnIndex(Contract.Users._ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Users._ID)),
                cursor.getColumnIndex(Contract.Users.SHORT_NAME) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Users.SHORT_NAME)));
    }

    @SuppressWarnings("UnusedDeclaration")
    public static UsersEntity fromJoinInContentValues(ContentValues principal, ContentValues complement) {
        if (principal == null || complement == null) {
            throw new IllegalArgumentException("Principal and complement must be not null.");
        }

        UsersEntity result = new UsersEntity(null, null);

        // Id:
        if (principal.getAsLong(Contract.Users._ID) != null) {
            result.setId(principal.getAsLong(Contract.Users._ID));
        } else {
            result.setId(complement.getAsLong(Contract.Users._ID));
        }

        // Short name:
        if (principal.getAsString(Contract.Users.SHORT_NAME) != null) {
            result.setShortName(principal.getAsString(Contract.Users.SHORT_NAME));
        } else {
            result.setShortName(complement.getAsString(Contract.Users.SHORT_NAME));
        }

        return result;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static UsersEntity fromContentValues(ContentValues values) {
        if (values == null) {
            throw new IllegalArgumentException("Values must be not null.");
        }

        return new UsersEntity(
                values.getAsLong(Contract.Users._ID),
                values.getAsString(Contract.Users.SHORT_NAME));
    }
}
