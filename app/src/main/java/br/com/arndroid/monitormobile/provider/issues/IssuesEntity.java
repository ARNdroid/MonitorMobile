package br.com.arndroid.monitormobile.provider.issues;

import android.content.ContentValues;
import android.database.Cursor;

import br.com.arndroid.monitormobile.provider.AbstractEntity;
import br.com.arndroid.monitormobile.provider.Contract;

public class IssuesEntity extends AbstractEntity {

    private Long id;
    private String acronymId;
    private String timeStamp;
    private Integer state;
    private Integer flagType;
    private Integer clockType;
    private String summary;
    private String description;
    private Long reporterId;
    private Long ownerId;

	/*
	 * Implementation
	 */

    public IssuesEntity(Long id, String acronymId, String timeStamp, Integer state, Integer flagType,
                        Integer clockType, String summary, String description, Long reporterId,
                        Long ownerId) {
        this.id = id;
        this.acronymId = acronymId;
        this.timeStamp = timeStamp;
        this.state = state;
        this.flagType = flagType;
        this.clockType = clockType;
        this.summary = summary;
        this.description = description;
        this.reporterId = reporterId;
        this.ownerId = ownerId;
    }

    public IssuesEntity(IssuesEntity toClone) {
        id = toClone.id;
        acronymId = toClone.acronymId;
        timeStamp = toClone.timeStamp;
        state = toClone.state;
        flagType = toClone.flagType;
        clockType = toClone.clockType;
        summary = toClone.summary;
        description = toClone.description;
        reporterId = toClone.reporterId;
        ownerId = toClone.ownerId;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Contract.Issues._ID, id);
        cv.put(Contract.Issues.ACRONYM_ID, acronymId);
        cv.put(Contract.Issues.TIME_STAMP, timeStamp);
        cv.put(Contract.Issues.STATE, state);
        cv.put(Contract.Issues.FLAG_TYPE, flagType);
        cv.put(Contract.Issues.CLOCK_TYPE, clockType);
        cv.put(Contract.Issues.SUMMARY, summary);
        cv.put(Contract.Issues.DESCRIPTION, description);
        cv.put(Contract.Issues.REPORTER_ID, reporterId);
        cv.put(Contract.Issues.OWNER_ID, ownerId);
        return cv;
    }

    @Override
    public ContentValues toContentValuesIgnoreNulls() {
        ContentValues cv = new ContentValues();
        if (id != null) {
            cv.put(Contract.Issues._ID, id);
        }
        if (acronymId != null){
            cv.put(Contract.Issues.ACRONYM_ID, acronymId);
        }
        if (timeStamp != null){
            cv.put(Contract.Issues.TIME_STAMP, timeStamp);
        }
        if (state != null){
            cv.put(Contract.Issues.STATE, state);
        }
        if (flagType != null){
            cv.put(Contract.Issues.FLAG_TYPE, flagType);
        }
        if (clockType != null){
            cv.put(Contract.Issues.CLOCK_TYPE, clockType);
        }
        if (summary != null){
            cv.put(Contract.Issues.SUMMARY, summary);
        }
        if (description != null){
            cv.put(Contract.Issues.DESCRIPTION, description);
        }
        if (reporterId != null){
            cv.put(Contract.Issues.REPORTER_ID, reporterId);
        }
        if (ownerId != null){
            cv.put(Contract.Issues.OWNER_ID, ownerId);
        }
        return cv;
    }

    @Override
    public void validateOrThrow() {
        if (acronymId == null) {
            throwNullValueException(Contract.Issues.ACRONYM_ID);
        }
        if (timeStamp == null) {
            throwNullValueException(Contract.Issues.TIME_STAMP);
        }
        if (state == null) {
            throwNullValueException(Contract.Issues.STATE);
        }
        if (flagType == null) {
            throwNullValueException(Contract.Issues.FLAG_TYPE);
        }
        if (clockType == null) {
            throwNullValueException(Contract.Issues.CLOCK_TYPE);
        }
        if (summary == null) {
            throwNullValueException(Contract.Issues.SUMMARY);
        }
        if (description == null) {
            throwNullValueException(Contract.Issues.DESCRIPTION);
        }
        if (reporterId == null) {
            throwNullValueException(Contract.Issues.REPORTER_ID);
        }
        if (ownerId == null) {
            throwNullValueException(Contract.Issues.OWNER_ID);
        }
    }

    @Override
    public String getTableName() {
        return Contract.Issues.TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return Contract.Issues._ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;

        } else if (obj instanceof IssuesEntity) {
            IssuesEntity temp = (IssuesEntity) obj;

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

            // timeStamp:
            if (this.timeStamp != null) {
                if (!this.timeStamp.equals(temp.timeStamp))
                    return false;
            } else {
                if (temp.timeStamp != null)
                    return false;
            }

            // state:
            if (this.state != null) {
                if (!this.state.equals(temp.state))
                    return false;
            } else {
                if (temp.state != null)
                    return false;
            }

            // flagType:
            if (this.flagType != null) {
                if (!this.flagType.equals(temp.flagType))
                    return false;
            } else {
                if (temp.flagType != null)
                    return false;
            }

            // clockType:
            if (this.clockType != null) {
                if (!this.clockType.equals(temp.clockType))
                    return false;
            } else {
                if (temp.clockType != null)
                    return false;
            }

            // summary:
            if (this.summary != null) {
                if (!this.summary.equals(temp.summary))
                    return false;
            } else {
                if (temp.summary != null)
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

            // reporterId:
            if (this.reporterId != null) {
                if (!this.reporterId.equals(temp.reporterId))
                    return false;
            } else {
                if (temp.reporterId != null)
                    return false;
            }

            // ownerId:
            if (this.ownerId != null) {
                if (!this.ownerId.equals(temp.ownerId))
                    return false;
            } else {
                if (temp.ownerId != null)
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
        result += timeStamp == null? 0: timeStamp.hashCode();
        result += state == null? 0: state.hashCode();
        result += flagType == null? 0: flagType.hashCode();
        result += clockType == null? 0: clockType.hashCode();
        result += summary == null? 0: summary.hashCode();
        result += description == null? 0: description.hashCode();
        result += reporterId == null? 0: reporterId.hashCode();
        result += ownerId == null? 0: ownerId.hashCode();
        return result;
    }

    @Override
    public String toString() {

        return "["
                + Contract.Issues._ID + "=" + id + ", "
                + Contract.Issues.ACRONYM_ID + "=" + acronymId + ", "
                + Contract.Issues.TIME_STAMP + "=" + timeStamp + ", "
                + Contract.Issues.STATE + "=" + state + ", "
                + Contract.Issues.FLAG_TYPE + "=" + flagType + ", "
                + Contract.Issues.CLOCK_TYPE + "=" + clockType + ", "
                + Contract.Issues.SUMMARY + "=" + summary + ", "
                + Contract.Issues.DESCRIPTION + "=" + description + ", "
                + Contract.Issues.REPORTER_ID + "=" + reporterId + ", "                
                + Contract.Issues.OWNER_ID + "=" + ownerId
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getFlagType() {
        return flagType;
    }

    public void setFlagType(Integer flagType) {
        this.flagType = flagType;
    }

    public Integer getClockType() {
        return clockType;
    }

    public void setClockType(Integer clockType) {
        this.clockType = clockType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /*
     * Factories
     */
    public static IssuesEntity fromCursor(Cursor cursor) {
        if(cursor == null) {
            throw new IllegalArgumentException("Cursor must be not null.");
        }

        return new IssuesEntity(
                cursor.getColumnIndex(Contract.Issues._ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Issues._ID)),
                cursor.getColumnIndex(Contract.Issues.ACRONYM_ID) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Issues.ACRONYM_ID)),
                cursor.getColumnIndex(Contract.Issues.TIME_STAMP) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Issues.TIME_STAMP)),
                cursor.getColumnIndex(Contract.Issues.STATE) == -1 ?
                        null : cursor.getInt(cursor.getColumnIndex(Contract.Issues.STATE)),
                cursor.getColumnIndex(Contract.Issues.FLAG_TYPE) == -1 ?
                        null : cursor.getInt(cursor.getColumnIndex(Contract.Issues.FLAG_TYPE)),
                cursor.getColumnIndex(Contract.Issues.CLOCK_TYPE) == -1 ?
                        null : cursor.getInt(cursor.getColumnIndex(Contract.Issues.CLOCK_TYPE)),
                cursor.getColumnIndex(Contract.Issues.SUMMARY) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Issues.SUMMARY)),
                cursor.getColumnIndex(Contract.Issues.DESCRIPTION) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Issues.DESCRIPTION)),
                cursor.getColumnIndex(Contract.Issues.REPORTER_ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Issues.REPORTER_ID)),
                cursor.getColumnIndex(Contract.Issues.OWNER_ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Issues.OWNER_ID)));
    }

    @SuppressWarnings("UnusedDeclaration")
    public static IssuesEntity fromJoinInContentValues(ContentValues principal, ContentValues complement) {
        if (principal == null || complement == null) {
            throw new IllegalArgumentException("Principal and complement must be not null.");
        }

        IssuesEntity result = new IssuesEntity(null, null, null, null, null, null, null, null, null, null);

        // Id:
        if (principal.getAsLong(Contract.Issues._ID) != null) {
            result.setId(principal.getAsLong(Contract.Issues._ID));
        } else {
            result.setId(complement.getAsLong(Contract.Issues._ID));
        }

        // Acronym:
        if (principal.getAsString(Contract.Issues.ACRONYM_ID) != null) {
            result.setAcronymId(principal.getAsString(Contract.Issues.ACRONYM_ID));
        } else {
            result.setAcronymId(complement.getAsString(Contract.Issues.ACRONYM_ID));
        }

        // Timestamp:
        if (principal.getAsString(Contract.Issues.TIME_STAMP) != null) {
            result.setTimeStamp(principal.getAsString(Contract.Issues.TIME_STAMP));
        } else {
            result.setTimeStamp(complement.getAsString(Contract.Issues.TIME_STAMP));
        }

        // State:
        if (principal.getAsInteger(Contract.Issues.STATE) != null) {
            result.setState(principal.getAsInteger(Contract.Issues.STATE));
        } else {
            result.setState(complement.getAsInteger(Contract.Issues.STATE));
        }

        // Flag type:
        if (principal.getAsInteger(Contract.Issues.FLAG_TYPE) != null) {
            result.setFlagType(principal.getAsInteger(Contract.Issues.FLAG_TYPE));
        } else {
            result.setFlagType(complement.getAsInteger(Contract.Issues.FLAG_TYPE));
        }

        // Clock type:
        if (principal.getAsInteger(Contract.Issues.CLOCK_TYPE) != null) {
            result.setClockType(principal.getAsInteger(Contract.Issues.CLOCK_TYPE));
        } else {
            result.setClockType(complement.getAsInteger(Contract.Issues.CLOCK_TYPE));
        }

        // Summary:
        if (principal.getAsString(Contract.Issues.SUMMARY) != null) {
            result.setSummary(principal.getAsString(Contract.Issues.SUMMARY));
        } else {
            result.setSummary(complement.getAsString(Contract.Issues.SUMMARY));
        }
        
        // Description:
        if (principal.getAsString(Contract.Issues.DESCRIPTION) != null) {
            result.setDescription(principal.getAsString(Contract.Issues.DESCRIPTION));
        } else {
            result.setDescription(complement.getAsString(Contract.Issues.DESCRIPTION));
        }

        // Reporter id:
        if (principal.getAsLong(Contract.Issues.REPORTER_ID) != null) {
            result.setReporterId(principal.getAsLong(Contract.Issues.REPORTER_ID));
        } else {
            result.setReporterId(complement.getAsLong(Contract.Issues.REPORTER_ID));
        }

        // Owner id:
        if (principal.getAsLong(Contract.Issues.OWNER_ID) != null) {
            result.setOwnerId(principal.getAsLong(Contract.Issues.OWNER_ID));
        } else {
            result.setOwnerId(complement.getAsLong(Contract.Issues.OWNER_ID));
        }

        return result;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static IssuesEntity fromContentValues(ContentValues values) {
        if (values == null) {
            throw new IllegalArgumentException("Values must be not null.");
        }

        return new IssuesEntity(
                values.getAsLong(Contract.Issues._ID),
                values.getAsString(Contract.Issues.ACRONYM_ID),
                values.getAsString(Contract.Issues.TIME_STAMP),
                values.getAsInteger(Contract.Issues.STATE),
                values.getAsInteger(Contract.Issues.FLAG_TYPE),
                values.getAsInteger(Contract.Issues.CLOCK_TYPE),
                values.getAsString(Contract.Issues.SUMMARY),
                values.getAsString(Contract.Issues.DESCRIPTION),
                values.getAsLong(Contract.Issues.REPORTER_ID),
                values.getAsLong(Contract.Issues.OWNER_ID));
    }
}