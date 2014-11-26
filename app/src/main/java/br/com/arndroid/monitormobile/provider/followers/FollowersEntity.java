package br.com.arndroid.monitormobile.provider.followers;

import android.content.ContentValues;
import android.database.Cursor;

import br.com.arndroid.monitormobile.provider.AbstractEntity;
import br.com.arndroid.monitormobile.provider.Contract;

public class FollowersEntity extends AbstractEntity {

    private Long id;
    private Long issueId;
    private Long followerId;

	/*
	 * Implementation
	 */

    public FollowersEntity(Long id, Long issueId, Long followerId) {
        this.id = id;
        this.issueId = issueId;
        this.followerId = followerId;
    }

    public FollowersEntity(FollowersEntity toClone) {
        id = toClone.id;
        issueId = toClone.issueId;
        followerId = toClone.followerId;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Contract.Followers._ID, id);
        cv.put(Contract.Followers.ISSUE_ID, issueId);
        cv.put(Contract.Followers.FOLLOWER_ID, followerId);
        return cv;
    }

    @Override
    public ContentValues toContentValuesIgnoreNulls() {
        ContentValues cv = new ContentValues();
        if (id != null) {
            cv.put(Contract.Followers._ID, id);
        }
        if (issueId != null){
            cv.put(Contract.Followers.ISSUE_ID, issueId);
        }
        if (followerId != null){
            cv.put(Contract.Followers.FOLLOWER_ID, followerId);
        }
        return cv;
    }

    @Override
    public void validateOrThrow() {
        if (issueId == null) {
            throwNullValueException(Contract.Followers.ISSUE_ID);
        }
        if (followerId == null) {
            throwNullValueException(Contract.Followers.FOLLOWER_ID);
        }
    }

    @Override
    public String getTableName() {
        return Contract.Followers.TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return Contract.Followers._ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;

        } else if (obj instanceof FollowersEntity) {
            FollowersEntity temp = (FollowersEntity) obj;

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


            // followerId:
            if (this.followerId != null) {
                if (!this.followerId.equals(temp.followerId))
                    return false;
            } else {
                if (temp.followerId != null)
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
        result += followerId == null? 0: followerId.hashCode();
        return result;
    }

    @Override
    public String toString() {

        return "["
                + Contract.Followers._ID + "=" + id + ", "
                + Contract.Followers.ISSUE_ID + "=" + issueId + ", "
                + Contract.Followers.FOLLOWER_ID + "=" + followerId
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

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    /*
     * Factories
     */
    public static FollowersEntity fromCursor(Cursor cursor) {
        if(cursor == null) {
            throw new IllegalArgumentException("Cursor must be not null.");
        }

        return new FollowersEntity(
                cursor.getColumnIndex(Contract.Followers._ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Followers._ID)),
                cursor.getColumnIndex(Contract.Followers.ISSUE_ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Followers.ISSUE_ID)),
                cursor.getColumnIndex(Contract.Followers.FOLLOWER_ID) == -1 ?
                        null : cursor.getLong(cursor.getColumnIndex(Contract.Followers.FOLLOWER_ID)));
    }

    @SuppressWarnings("UnusedDeclaration")
    public static FollowersEntity fromJoinInContentValues(ContentValues principal, ContentValues complement) {
        if (principal == null || complement == null) {
            throw new IllegalArgumentException("Principal and complement must be not null.");
        }

        FollowersEntity result = new FollowersEntity(null, null, null);

        // Id:
        if (principal.getAsLong(Contract.Followers._ID) != null) {
            result.setId(principal.getAsLong(Contract.Followers._ID));
        } else {
            result.setId(complement.getAsLong(Contract.Followers._ID));
        }

        // Issue id:
        if (principal.getAsLong(Contract.Followers.ISSUE_ID) != null) {
            result.setIssueId(principal.getAsLong(Contract.Followers.ISSUE_ID));
        } else {
            result.setIssueId(complement.getAsLong(Contract.Followers.ISSUE_ID));
        }

        // Follower id:
        if (principal.getAsLong(Contract.Followers.FOLLOWER_ID) != null) {
            result.setFollowerId(principal.getAsLong(Contract.Followers.FOLLOWER_ID));
        } else {
            result.setFollowerId(complement.getAsLong(Contract.Followers.FOLLOWER_ID));
        }

        return result;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static FollowersEntity fromContentValues(ContentValues values) {
        if (values == null) {
            throw new IllegalArgumentException("Values must be not null.");
        }

        return new FollowersEntity(
                values.getAsLong(Contract.Followers._ID),
                values.getAsLong(Contract.Followers.ISSUE_ID),
                values.getAsLong(Contract.Followers.FOLLOWER_ID));
    }
}