package br.gov.caixa.monitormobile.provider.systems;

import android.content.ContentValues;
import android.database.Cursor;

import br.gov.caixa.monitormobile.provider.AbstractEntity;
import br.gov.caixa.monitormobile.provider.Contract;

public class SystemsEntity extends AbstractEntity {
    private Long id;
    private String acronymId;
    private String description;

	/*
	 * Implementation
	 */

    public SystemsEntity(Long id, String acronymId, String description) {
        this.id = id;
        this.acronymId = acronymId;
        this.description = description;
    }

    public SystemsEntity(SystemsEntity toClone) {
        id = toClone.id;
        acronymId = toClone.acronymId;
        description = toClone.description;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Contract.Systems._ID, id);
        cv.put(Contract.Systems.ACRONYM_ID, acronymId);
        cv.put(Contract.Systems.DESCRIPTION, description);
        return cv;
    }

    @Override
    public ContentValues toContentValuesIgnoreNulls() {
        ContentValues cv = new ContentValues();
        if (id != null) {
            cv.put(Contract.Systems._ID, id);
        }
        if (acronymId != null) {
            cv.put(Contract.Systems.ACRONYM_ID, acronymId);
        }
        if (description != null) {
            cv.put(Contract.Systems.DESCRIPTION, description);
        }
        return cv;
    }

    @Override
    public void validateOrThrow() {

        if (acronymId == null) {
            throwNullValueException(Contract.Systems.ACRONYM_ID);
        }
        if (description == null) {
            throwNullValueException(Contract.Systems.DESCRIPTION);
        }
    }

    @Override
    public String getTableName() {
        return Contract.Systems.TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return Contract.Systems._ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;

        } else if (obj instanceof SystemsEntity) {
            SystemsEntity temp = (SystemsEntity) obj;

            // id:
            if (this.id != null) {
                if (!this.id.equals(temp.id))
                    return false;
            } else {
                if (temp.id != null)
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

            // description:
            if (this.description != null) {
                if (!this.description.equals(temp.description))
                    return false;
            } else {
                if (temp.description != null)
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
        result += acronymId == null? 0: acronymId.hashCode();
        result += description == null? 0: description.hashCode();
        return result;
    }

    @Override
    public String toString() {

        return "["
                + Contract.Systems._ID + "=" + id + ", "
                + Contract.Systems.ACRONYM_ID + "=" + acronymId + ", "
                + Contract.Systems.DESCRIPTION + "=" + description
                + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronymId() {
        return acronymId;
    }

    public void setAcronymId(String acronymId) {
        this.acronymId = acronymId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*
     * Factories
     */
    public static SystemsEntity fromCursor(Cursor cursor) {
        if(cursor == null) {
            throw new IllegalArgumentException("Cursor must be not null.");
        }

        return new SystemsEntity(
                cursor.getColumnIndex(Contract.Systems._ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Systems._ID)),
                cursor.getColumnIndex(Contract.Systems.ACRONYM_ID) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Systems.ACRONYM_ID)),
                cursor.getColumnIndex(Contract.Systems.DESCRIPTION) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Systems.DESCRIPTION)));
    }

    @SuppressWarnings("UnusedDeclaration")
    public static SystemsEntity fromJoinInContentValues(ContentValues principal, ContentValues complement) {
        if (principal == null || complement == null) {
            throw new IllegalArgumentException("Principal and complement must be not null.");
        }

        SystemsEntity result = new SystemsEntity(null, null, null);

        // Id:
        if (principal.getAsLong(Contract.Systems._ID) != null) {
            result.setId(principal.getAsLong(Contract.Systems._ID));
        } else {
            result.setId(complement.getAsLong(Contract.Systems._ID));
        }

        // Acronym:
        if (principal.getAsString(Contract.Systems.ACRONYM_ID) != null) {
            result.setAcronymId(principal.getAsString(Contract.Systems.ACRONYM_ID));
        } else {
            result.setAcronymId(complement.getAsString(Contract.Systems.ACRONYM_ID));
        }

        // Description:
        if (principal.getAsString(Contract.Systems.DESCRIPTION) != null) {
            result.setAcronymId(principal.getAsString(Contract.Systems.DESCRIPTION));
        } else {
            result.setAcronymId(complement.getAsString(Contract.Systems.DESCRIPTION));
        }

        return result;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static SystemsEntity fromContentValues(ContentValues values) {
        if (values == null) {
            throw new IllegalArgumentException("Values must be not null.");
        }

        return new SystemsEntity(
                values.getAsLong(Contract.Systems._ID),
                values.getAsString(Contract.Systems.ACRONYM_ID),
                values.getAsString(Contract.Systems.DESCRIPTION));
    }
}
