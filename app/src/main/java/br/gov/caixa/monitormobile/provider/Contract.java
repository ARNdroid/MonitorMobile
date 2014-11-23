package br.gov.caixa.monitormobile.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import java.io.Serializable;

import br.gov.caixa.monitormobile.BuildConfig;

public class Contract {

    // This utility class cannot be instantiated.
    private Contract() {}

	/*
	 * Global Definitions
	 */

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";

    public static final int FIELD_TYPE_INTEGER = 1;
    public static final int FIELD_TYPE_LONG = 2;
    public static final int FIELD_TYPE_FLOAT = 3;
    public static final int FIELD_TYPE_STRING = 4;

	/*
	 * Exceptions
	 */

    /*
     * We use exception to error handling. We don't like but this is the providers way.
     * This class doesn't extend Exception because Providers API doesn't throw any checked exception.
     */

    public static class TargetException extends RuntimeException {

        // A descriptor for fields with problem:
        public static final class FieldDescriptor implements Serializable {

            final private String mTableName;
            final private String mColumnName;
            final private Object mValue;

            @SuppressWarnings("SameParameterValue")
            public FieldDescriptor(String tableName, String columnName, Object value) {
                mTableName = tableName;
                mColumnName = columnName;
                mValue = value;
            }

            public String getTableName() {
                return mTableName;
            }

            public String getColumnName() {
                return mColumnName;
            }

            public Object getValue() {
                return mValue;
            }

            private static final long serialVersionUID = 1L;
        }

        @SuppressWarnings("UnusedDeclaration")
        public static final int UNEXPECTED_ERROR = 0;
        public static final int NULL_VALUE = 1;
        @SuppressWarnings("UnusedDeclaration")
        public static final int INVALID_VALUE = 2;
        @SuppressWarnings("UnusedDeclaration")
        public static final int ID_UPDATED = 3;
        @SuppressWarnings("UnusedDeclaration")
        public static final int DUPLICATED_DATA = 4;

        private final int mErrorCode;
        private final FieldDescriptor[] mFieldDescriptorArray;
        private String mDetailMessage;

        @SuppressWarnings("SameParameterValue")
        public TargetException(int errorCode, FieldDescriptor[] fieldDescriptorArray, Throwable cause) {
            super(cause);
            mErrorCode = errorCode;
            mFieldDescriptorArray = fieldDescriptorArray;
        }

        public int getErrorCode() {
            return mErrorCode;
        }

        @SuppressWarnings("UnusedDeclaration")
        public FieldDescriptor[] getFieldDescriptorArray() {
            // OK... we are returning the internal array and clients will have access
            // to our array and to fields descriptors. But we are not scared about
            // attacks here.
            return mFieldDescriptorArray;
        }

        @Override
        public String getMessage() {
            if (mDetailMessage == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Error code: ").append(getErrorCode()).append('\n');
                for (int i = 0; i < mFieldDescriptorArray.length; i++) {
                    sb.append("Field descriptor at position ").append(i).append(":\n")
                            .append("Table = '").append(mFieldDescriptorArray[i].getTableName())
                            .append("', column = '").append(mFieldDescriptorArray[i].getColumnName())
                            .append("', value = '").append(mFieldDescriptorArray[i].getValue() == null ? null : mFieldDescriptorArray[i].getValue().toString()).append("'\n");
                }
                mDetailMessage = sb.toString();
            }
            return mDetailMessage;
        }

        private static final long serialVersionUID = 1L;
    }

	/*
	 * Users Definitions
	 */

    protected interface UsersColumns {

        // Table:
        public static final String TABLE_NAME = "users";

        // Columns:
        public static final String SHORT_NAME = "short_name";
    }

    public static final class Users implements BaseColumns, UsersColumns {

        // This utility class cannot be instantiated:
        private Users() {}

		/*
		 * URI's
		 */

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        /*
            Projections
         */
        @SuppressWarnings("UnusedDeclaration")
        public static final String[] ID_PROJECTION = {_ID};

		/*
		 * MIME types
		 */

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_NAME;

        /*
		 * Selection
		 */

        public static final String ID_SELECTION = _ID + "=?";
        public static final String SHORT_NAME_SELECTION = SHORT_NAME + "=?";

		/*
		 * Sort order
		 */

        @SuppressWarnings("UnusedDeclaration")
        public static final String SHORT_NAME_ASC = SHORT_NAME + " ASC";

		/*
		 * Utility methods
		 */

        @SuppressWarnings("UnusedDeclaration")
        public static int fieldTypeForColumn(String columnName) {
            return fieldTypeForTableAndColumn(TABLE_NAME, columnName);
        }
    }

    /*
	 * Systems Definitions
	 */

    protected interface SystemsColumns {

        // Table:
        public static final String TABLE_NAME = "systems";

        // Columns:
        public static final String ACRONYM_ID = "acronym_id";
        public static final String DESCRIPTION = "description";
    }

    public static final class Systems implements BaseColumns, SystemsColumns {

        // This utility class cannot be instantiated:
        private Systems() {
        }

		/*
		 * URI's
		 */

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        /*
            Projections
         */
        @SuppressWarnings("UnusedDeclaration")
        public static final String[] ID_PROJECTION = {_ID};
        public static final String[] ACRONYM_ID_PROJECTION = {ACRONYM_ID};

		/*
		 * MIME types
		 */

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_NAME;

        /*
		 * Selection
		 */

        public static final String ID_SELECTION = _ID + "=?";
        public static final String ACRONYM_ID_SELECTION = ACRONYM_ID + "=?";

		/*
		 * Sort order
		 */

        @SuppressWarnings("UnusedDeclaration")
        public static final String ACRONYM_ID_ASC = ACRONYM_ID + " ASC";
    }

    /*
	 * Issues Definitions
	 */

        protected interface IssuesColumns {

            // Table:
            public static final String TABLE_NAME = "issues";

            // Columns:
            public static final String ACRONYM_ID = "system_id";
            public static final String TIME_STAMP = "time_stamp";
            public static final String STATE = "state";
            public static final String FLAG_TYPE = "flag_type";
            public static final String CLOCK_TYPE = "clock_type";
            public static final String SUMMARY = "summary";
            public static final String DESCRIPTION = "description";
            public static final String REPORTER_ID = "reporter_id";
            public static final String OWNER_ID = "owner_id";
        }

        public static final class Issues implements BaseColumns, IssuesColumns {

            // This utility class cannot be instantiated:
            private Issues() {}

		/*
		 * URI's
		 */

            public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

            /*
                Projections
             */
            @SuppressWarnings("UnusedDeclaration")
            public static final String[] ID_PROJECTION = {_ID};

		/*
		 * MIME types
		 */

            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_NAME;
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_NAME;

        /*
		 * Selection
		 */

            public static final String ID_SELECTION = _ID + "=?";
            public static final String SYSTEM_ID_SELECTION = ACRONYM_ID + "=?";

		/*
		 * Sort order
		 */

            @SuppressWarnings("UnusedDeclaration")
            public static final String TIME_STAMP_DESC = TIME_STAMP + " DESC";

		/*
		 * Utility methods
		 */

        @SuppressWarnings("UnusedDeclaration")
        public static int fieldTypeForColumn(String columnName) {
            return fieldTypeForTableAndColumn(TABLE_NAME, columnName);
        }
    }

    /*
	 * Actions Definitions
	 */

    protected interface ActionsColumns {

        // Table:
        public static final String TABLE_NAME = "actions";

        // Columns:
        public static final String ISSUE_ID = "issue_id";
        public static final String TIME_STAMP = "time_stamp";
        public static final String SUMMARY = "summary";
        public static final String DESCRIPTION = "description";
        public static final String AGENT_ID = "agent_id";
    }

    public static final class Actions implements BaseColumns, ActionsColumns {

        // This utility class cannot be instantiated:
        private Actions() {}

		/*
		 * URI's
		 */

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        /*
            Projections
         */
        @SuppressWarnings("UnusedDeclaration")
        public static final String[] ID_PROJECTION = {_ID};

		/*
		 * MIME types
		 */

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_NAME;

        /*
		 * Selection
		 */

        public static final String ID_SELECTION = _ID + "=?";
        public static final String ISSUE_ID_SELECTION = ISSUE_ID + "=?";

		/*
		 * Sort order
		 */

        @SuppressWarnings("UnusedDeclaration")
        public static final String TIME_STAMP_DESC = TIME_STAMP + " DESC";

		/*
		 * Utility methods
		 */

        @SuppressWarnings("UnusedDeclaration")
        public static int fieldTypeForColumn(String columnName) {
            return fieldTypeForTableAndColumn(TABLE_NAME, columnName);
        }
    }

    private static int fieldTypeForTableAndColumn(String tableName, String columnName) {

        if(Users.TABLE_NAME.equals(tableName)) {
            if (Users._ID.equals(columnName)) {
                return FIELD_TYPE_LONG;
            } else if (Users.SHORT_NAME.equals(columnName)) {
                return FIELD_TYPE_STRING;
            } else {
                throw new IllegalArgumentException("Unknown column " + columnName + " for table " + tableName);
            }
        } else if(Systems.TABLE_NAME.equals(tableName)) {
            if (Systems._ID.equals(columnName)) {
                return FIELD_TYPE_LONG;
            } else if (Systems.ACRONYM_ID.equals(columnName)) {
                return FIELD_TYPE_STRING;
            } else if (Systems.DESCRIPTION.equals(columnName)) {
                return FIELD_TYPE_STRING;
            } else {
                throw new IllegalArgumentException("Unknown column " + columnName + " for table " + tableName);
            }
        } else if(Issues.TABLE_NAME.equals(tableName)) {
            if (Issues._ID.equals(columnName)) {
                return FIELD_TYPE_LONG;
            } else if (Issues.ACRONYM_ID.equals(columnName)) {
                return FIELD_TYPE_STRING;
            } else if (Issues.TIME_STAMP.equals(columnName)) {
                return FIELD_TYPE_STRING;
            } else if (Issues.STATE.equals(columnName)) {
                return FIELD_TYPE_INTEGER;
            } else if (Issues.FLAG_TYPE.equals(columnName)) {
                return FIELD_TYPE_INTEGER;
            } else if (Issues.CLOCK_TYPE.equals(columnName)) {
                return FIELD_TYPE_INTEGER;
            } else if (Issues.SUMMARY.equals(columnName)) {
                return FIELD_TYPE_STRING;
            } else if (Issues.DESCRIPTION.equals(columnName)) {
                return FIELD_TYPE_STRING;
            } else if (Issues.REPORTER_ID.equals(columnName)) {
                return FIELD_TYPE_LONG;
            } else if (Issues.OWNER_ID.equals(columnName)) {
                return FIELD_TYPE_LONG;
            } else {
                throw new IllegalArgumentException("Unknown column " + columnName + " for table " + tableName);
            }
        } else if(Actions.TABLE_NAME.equals(tableName)) {
            if (Actions._ID.equals(columnName)) {
                return FIELD_TYPE_LONG;
            } else if (Actions.ISSUE_ID.equals(columnName)) {
                return FIELD_TYPE_LONG;
            } else if (Actions.TIME_STAMP.equals(columnName)) {
                return FIELD_TYPE_STRING;
            } else if (Actions.SUMMARY.equals(columnName)) {
                return FIELD_TYPE_STRING;
            } else if (Actions.DESCRIPTION.equals(columnName)) {
                return FIELD_TYPE_STRING;
            } else if (Actions.AGENT_ID.equals(columnName)) {
                return FIELD_TYPE_LONG;
            } else {
                throw new IllegalArgumentException("Unknown column " + columnName + " for table " + tableName);
            }
        } else {
            throw new IllegalArgumentException("Unknown table " + tableName);
        }
    }
}
