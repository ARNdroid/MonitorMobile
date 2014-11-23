package br.gov.caixa.monitormobile.provider.actions;

import android.content.ContentValues;
import android.database.Cursor;

import br.gov.caixa.monitormobile.provider.AbstractEntity;
import br.gov.caixa.monitormobile.provider.Contract;

public class ActionsEntity  extends AbstractEntity {

    private Long id;
    private Long issueId;
    private String timeStamp;
    private String summary;
    private String description;
    private Long agentId;

	/*
	 * Implementation
	 */

    public ActionsEntity(Long id, Long issueId, String timeStamp, String summary,
                         String description, Long agentId) {
        this.id = id;
        this.issueId = issueId;
        this.timeStamp = timeStamp;
        this.summary = summary;
        this.description = description;
        this.agentId = agentId;
    }

    public ActionsEntity(ActionsEntity toClone) {
        id = toClone.id;
        issueId = toClone.issueId;
        timeStamp = toClone.timeStamp;
        summary = toClone.summary;
        description = toClone.description;
        agentId = toClone.agentId;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Contract.Actions._ID, id);
        cv.put(Contract.Actions.ISSUE_ID, issueId);
        cv.put(Contract.Actions.TIME_STAMP, timeStamp);
        cv.put(Contract.Actions.SUMMARY, summary);
        cv.put(Contract.Actions.DESCRIPTION, description);
        cv.put(Contract.Actions.AGENT_ID, agentId);
        return cv;
    }

    @Override
    public ContentValues toContentValuesIgnoreNulls() {
        ContentValues cv = new ContentValues();
        if (id != null) {
            cv.put(Contract.Actions._ID, id);
        }
        if (issueId != null){
            cv.put(Contract.Actions.ISSUE_ID, issueId);
        }
        if (timeStamp != null){
            cv.put(Contract.Actions.TIME_STAMP, timeStamp);
        }
        if (summary != null){
            cv.put(Contract.Actions.SUMMARY, summary);
        }
        if (description != null){
            cv.put(Contract.Actions.DESCRIPTION, description);
        }
        if (agentId != null){
            cv.put(Contract.Actions.AGENT_ID, agentId);
        }
        return cv;
    }

    @Override
    public void validateOrThrow() {
        if (issueId == null) {
            throwNullValueException(Contract.Actions.ISSUE_ID);
        }
        if (timeStamp == null) {
            throwNullValueException(Contract.Actions.TIME_STAMP);
        }
        if (summary == null) {
            throwNullValueException(Contract.Actions.SUMMARY);
        }
        if (description == null) {
            throwNullValueException(Contract.Actions.DESCRIPTION);
        }
        if (agentId == null) {
            throwNullValueException(Contract.Actions.AGENT_ID);
        }
    }

    @Override
    public String getTableName() {
        return Contract.Actions.TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return Contract.Actions._ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;

        } else if (obj instanceof ActionsEntity) {
            ActionsEntity temp = (ActionsEntity) obj;

            // id:
            if (this.id != null) {
                if (!this.id.equals(temp.id))
                    return false;
            } else {
                if (temp.id != null)
                    return false;
            }

            // issueId:
            if (this.issueId != null) {
                if (!this.issueId.equals(temp.issueId))
                    return false;
            } else {
                if (temp.issueId != null)
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

            // agentId:
            if (this.agentId != null) {
                if (!this.agentId.equals(temp.agentId))
                    return false;
            } else {
                if (temp.agentId != null)
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
        result += issueId == null? 0: issueId.hashCode();
        result += timeStamp == null? 0: timeStamp.hashCode();
        result += summary == null? 0: summary.hashCode();
        result += description == null? 0: description.hashCode();
        result += agentId == null? 0: agentId.hashCode();
        return result;
    }

    @Override
    public String toString() {

        return "["
                + Contract.Actions._ID + "=" + id + ", "
                + Contract.Actions.ISSUE_ID + "=" + issueId + ", "
                + Contract.Actions.TIME_STAMP + "=" + timeStamp + ", "
                + Contract.Actions.SUMMARY + "=" + summary + ", "
                + Contract.Actions.DESCRIPTION + "=" + description + ", "
                + Contract.Actions.AGENT_ID + "=" + agentId
                + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
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

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    /*
     * Factories
     */
    public static ActionsEntity fromCursor(Cursor cursor) {
        if(cursor == null) {
            throw new IllegalArgumentException("Cursor must be not null.");
        }

        return new ActionsEntity(
                cursor.getColumnIndex(Contract.Actions._ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Actions._ID)),
                cursor.getColumnIndex(Contract.Actions.ISSUE_ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Actions.ISSUE_ID)),
                cursor.getColumnIndex(Contract.Actions.TIME_STAMP) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Actions.TIME_STAMP)),
                cursor.getColumnIndex(Contract.Actions.SUMMARY) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Actions.SUMMARY)),
                cursor.getColumnIndex(Contract.Actions.DESCRIPTION) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Actions.DESCRIPTION)),
                cursor.getColumnIndex(Contract.Actions.AGENT_ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Actions.AGENT_ID)));
    }

    @SuppressWarnings("UnusedDeclaration")
    public static ActionsEntity fromJoinInContentValues(ContentValues principal, ContentValues complement) {
        if (principal == null || complement == null) {
            throw new IllegalArgumentException("Principal and complement must be not null.");
        }

        ActionsEntity result = new ActionsEntity(null, null, null, null, null, null);

        // Id:
        if (principal.getAsLong(Contract.Actions._ID) != null) {
            result.setId(principal.getAsLong(Contract.Actions._ID));
        } else {
            result.setId(complement.getAsLong(Contract.Actions._ID));
        }

        // Issue id:
        if (principal.getAsLong(Contract.Actions.ISSUE_ID) != null) {
            result.setIssueId(principal.getAsLong(Contract.Actions.ISSUE_ID));
        } else {
            result.setIssueId(complement.getAsLong(Contract.Actions.ISSUE_ID));
        }

        // Timestamp:
        if (principal.getAsString(Contract.Actions.TIME_STAMP) != null) {
            result.setTimeStamp(principal.getAsString(Contract.Actions.TIME_STAMP));
        } else {
            result.setTimeStamp(complement.getAsString(Contract.Actions.TIME_STAMP));
        }

        // Summary:
        if (principal.getAsString(Contract.Actions.SUMMARY) != null) {
            result.setSummary(principal.getAsString(Contract.Actions.SUMMARY));
        } else {
            result.setSummary(complement.getAsString(Contract.Actions.SUMMARY));
        }

        // Description:
        if (principal.getAsString(Contract.Actions.DESCRIPTION) != null) {
            result.setDescription(principal.getAsString(Contract.Actions.DESCRIPTION));
        } else {
            result.setDescription(complement.getAsString(Contract.Actions.DESCRIPTION));
        }

        // Agent id:
        if (principal.getAsLong(Contract.Actions.AGENT_ID) != null) {
            result.setAgentId(principal.getAsLong(Contract.Actions.AGENT_ID));
        } else {
            result.setAgentId(complement.getAsLong(Contract.Actions.AGENT_ID));
        }

        return result;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static ActionsEntity fromContentValues(ContentValues values) {
        if (values == null) {
            throw new IllegalArgumentException("Values must be not null.");
        }

        return new ActionsEntity(
                values.getAsLong(Contract.Actions._ID),
                values.getAsLong(Contract.Actions.ISSUE_ID),
                values.getAsString(Contract.Actions.TIME_STAMP),
                values.getAsString(Contract.Actions.SUMMARY),
                values.getAsString(Contract.Actions.DESCRIPTION),
                values.getAsLong(Contract.Actions.AGENT_ID));
    }
}