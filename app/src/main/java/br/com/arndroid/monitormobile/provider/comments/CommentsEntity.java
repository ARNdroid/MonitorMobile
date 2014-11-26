package br.com.arndroid.monitormobile.provider.comments;

import android.content.ContentValues;
import android.database.Cursor;

import br.com.arndroid.monitormobile.provider.AbstractEntity;
import br.com.arndroid.monitormobile.provider.Contract;

public class CommentsEntity extends AbstractEntity {

    private Long id;
    private Long actionId;
    private String timeStamp;
    private String description;
    private Long commenterId;

	/*
	 * Implementation
	 */

    public CommentsEntity(Long id, Long actionId, String timeStamp, String description,
                          Long commenterId) {
        this.id = id;
        this.actionId = actionId;
        this.timeStamp = timeStamp;
        this.description = description;
        this.commenterId = commenterId;
    }

    public CommentsEntity(CommentsEntity toClone) {
        id = toClone.id;
        actionId = toClone.actionId;
        timeStamp = toClone.timeStamp;
        description = toClone.description;
        commenterId = toClone.commenterId;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Contract.Comments._ID, id);
        cv.put(Contract.Comments.ACTION_ID, actionId);
        cv.put(Contract.Comments.TIME_STAMP, timeStamp);
        cv.put(Contract.Comments.DESCRIPTION, description);
        cv.put(Contract.Comments.COMMENTER_ID, commenterId);
        return cv;
    }

    @Override
    public ContentValues toContentValuesIgnoreNulls() {
        ContentValues cv = new ContentValues();
        if (id != null) {
            cv.put(Contract.Comments._ID, id);
        }
        if (actionId != null){
            cv.put(Contract.Comments.ACTION_ID, actionId);
        }
        if (timeStamp != null){
            cv.put(Contract.Comments.TIME_STAMP, timeStamp);
        }
        if (description != null){
            cv.put(Contract.Comments.DESCRIPTION, description);
        }
        if (commenterId != null){
            cv.put(Contract.Comments.COMMENTER_ID, commenterId);
        }
        return cv;
    }

    @Override
    public void validateOrThrow() {
        if (actionId == null) {
            throwNullValueException(Contract.Comments.ACTION_ID);
        }
        if (timeStamp == null) {
            throwNullValueException(Contract.Comments.TIME_STAMP);
        }
        if (description == null) {
            throwNullValueException(Contract.Comments.DESCRIPTION);
        }
        if (commenterId == null) {
            throwNullValueException(Contract.Comments.COMMENTER_ID);
        }
    }

    @Override
    public String getTableName() {
        return Contract.Comments.TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return Contract.Comments._ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;

        } else if (obj instanceof CommentsEntity) {
            CommentsEntity temp = (CommentsEntity) obj;

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

            // timeStamp:
            if (this.timeStamp != null) {
                if (!this.timeStamp.equals(temp.timeStamp))
                    return false;
            } else {
                if (temp.timeStamp != null)
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

            // commenterId:
            if (this.commenterId != null) {
                if (!this.commenterId.equals(temp.commenterId))
                    return false;
            } else {
                if (temp.commenterId != null)
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
        result += timeStamp == null? 0: timeStamp.hashCode();
        result += description == null? 0: description.hashCode();
        result += commenterId == null? 0: commenterId.hashCode();
        return result;
    }

    @Override
    public String toString() {

        return "["
                + Contract.Comments._ID + "=" + id + ", "
                + Contract.Comments.ACTION_ID + "=" + actionId + ", "
                + Contract.Comments.TIME_STAMP + "=" + timeStamp + ", "
                + Contract.Comments.DESCRIPTION + "=" + description + ", "
                + Contract.Comments.COMMENTER_ID + "=" + commenterId
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(Long commenterId) {
        this.commenterId = commenterId;
    }

    /*
     * Factories
     */
    public static CommentsEntity fromCursor(Cursor cursor) {
        if(cursor == null) {
            throw new IllegalArgumentException("Cursor must be not null.");
        }

        return new CommentsEntity(
                cursor.getColumnIndex(Contract.Comments._ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Comments._ID)),
                cursor.getColumnIndex(Contract.Comments.ACTION_ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Comments.ACTION_ID)),
                cursor.getColumnIndex(Contract.Comments.TIME_STAMP) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Comments.TIME_STAMP)),
                cursor.getColumnIndex(Contract.Comments.DESCRIPTION) == -1 ?
                        null : cursor.getString(cursor.getColumnIndex(Contract.Comments.DESCRIPTION)),
                cursor.getColumnIndex(Contract.Comments.COMMENTER_ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Comments.COMMENTER_ID)));
    }

    @SuppressWarnings("UnusedDeclaration")
    public static CommentsEntity fromJoinInContentValues(ContentValues principal, ContentValues complement) {
        if (principal == null || complement == null) {
            throw new IllegalArgumentException("Principal and complement must be not null.");
        }

        CommentsEntity result = new CommentsEntity(null, null, null, null, null);

        // Id:
        if (principal.getAsLong(Contract.Comments._ID) != null) {
            result.setId(principal.getAsLong(Contract.Comments._ID));
        } else {
            result.setId(complement.getAsLong(Contract.Comments._ID));
        }

        // Action id:
        if (principal.getAsLong(Contract.Comments.ACTION_ID) != null) {
            result.setActionId(principal.getAsLong(Contract.Comments.ACTION_ID));
        } else {
            result.setActionId(complement.getAsLong(Contract.Comments.ACTION_ID));
        }

        // Timestamp:
        if (principal.getAsString(Contract.Comments.TIME_STAMP) != null) {
            result.setTimeStamp(principal.getAsString(Contract.Comments.TIME_STAMP));
        } else {
            result.setTimeStamp(complement.getAsString(Contract.Comments.TIME_STAMP));
        }

        // Description:
        if (principal.getAsString(Contract.Comments.DESCRIPTION) != null) {
            result.setDescription(principal.getAsString(Contract.Comments.DESCRIPTION));
        } else {
            result.setDescription(complement.getAsString(Contract.Comments.DESCRIPTION));
        }

        // Commenter id:
        if (principal.getAsLong(Contract.Comments.COMMENTER_ID) != null) {
            result.setCommenterId(principal.getAsLong(Contract.Comments.COMMENTER_ID));
        } else {
            result.setCommenterId(complement.getAsLong(Contract.Comments.COMMENTER_ID));
        }

        return result;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static CommentsEntity fromContentValues(ContentValues values) {
        if (values == null) {
            throw new IllegalArgumentException("Values must be not null.");
        }

        return new CommentsEntity(
                values.getAsLong(Contract.Comments._ID),
                values.getAsLong(Contract.Comments.ACTION_ID),
                values.getAsString(Contract.Comments.TIME_STAMP),
                values.getAsString(Contract.Comments.DESCRIPTION),
                values.getAsLong(Contract.Comments.COMMENTER_ID));
    }
}