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

    // TODO: change this
	/*
	 * Days Definitions
	 */

//    protected interface DaysColumns {
//
//        // Table:
//        public static final String TABLE_NAME = "days";
//
//        // Columns:
//        public static final String DATE_ID = "date_id";
//        public static final String ALLOWED = "allowed";
//        public static final String BREAKFAST_START_TIME = "breakfast_start_time";
//        public static final String BREAKFAST_END_TIME = "breakfast_end_time";
//        public static final String BREAKFAST_GOAL = "breakfast_goal";
//        public static final String BRUNCH_START_TIME = "brunch_start_time";
//        public static final String BRUNCH_END_TIME = "brunch_end_time";
//        public static final String BRUNCH_GOAL = "brunch_goal";
//        public static final String LUNCH_START_TIME = "lunch_start_time";
//        public static final String LUNCH_END_TIME = "lunch_end_time";
//        public static final String LUNCH_GOAL = "lunch_goal";
//        public static final String SNACK_START_TIME = "snack_start_time";
//        public static final String SNACK_END_TIME = "snack_end_time";
//        public static final String SNACK_GOAL = "snack_goal";
//        public static final String DINNER_START_TIME = "dinner_start_time";
//        public static final String DINNER_END_TIME = "dinner_end_time";
//        public static final String DINNER_GOAL = "dinner_goal";
//        public static final String SUPPER_START_TIME = "supper_start_time";
//        public static final String SUPPER_END_TIME = "supper_end_time";
//        public static final String SUPPER_GOAL = "supper_goal";
//        public static final String EXERCISE_GOAL = "exercise_goal";
//        public static final String LIQUID_DONE = "liquid_done";
//        public static final String LIQUID_GOAL = "liquid_goal";
//        public static final String OIL_DONE = "oil_done";
//        public static final String OIL_GOAL = "oil_goal";
//        public static final String SUPPLEMENT_DONE = "supplement_done";
//        public static final String SUPPLEMENT_GOAL = "supplement_goal";
//        public static final String NOTE = "note";
//    }
//
//    public static final class Days implements BaseColumns, DaysColumns {
//
//        // This utility class cannot be instantiated:
//        private Days() {}
//
//		/*
//		 * URI's
//		 */
//
//        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
//        public static final Uri DATE_ID_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +
//                TABLE_NAME + "/date_id");
//
//        /*
//            Projections
//         */
//        @SuppressWarnings("UnusedDeclaration")
//        public static final String[] ID_PROJECTION = {_ID};
//
//		/*
//		 * MIME types
//		 */
//
//        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_NAME;
//        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_NAME;
//
//        /*
//		 * Selection
//		 */
//
//        public static final String ID_SELECTION = _ID + "=?";
//        public static final String DATE_ID_SELECTION = DATE_ID + "=?";
//
//		/*
//		 * Sort order
//		 */
//
//        @SuppressWarnings("UnusedDeclaration")
//        public static final String DATE_ID_ASC = DATE_ID + " ASC";
//
//		/*
//		 * Utility methods
//		 */
//
//        @SuppressWarnings("UnusedDeclaration")
//        public static int fieldTypeForColumn(String columnName) {
//            return fieldTypeForTableAndColumn(TABLE_NAME, columnName);
//        }
//    }

    private static int fieldTypeForTableAndColumn(String tableName, String columnName) {

    // TODO: implements
//        if(FoodsUsage.TABLE_NAME.equals(tableName)) {
//            if (FoodsUsage._ID.equals(columnName)) {
//                return FIELD_TYPE_LONG;
//            } else if (FoodsUsage.DATE_ID.equals(columnName)) {
//                return FIELD_TYPE_STRING;
//            } else if (FoodsUsage.MEAL.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (FoodsUsage.TIME.equals(columnName)) {
//                return FIELD_TYPE_LONG;
//            } else if (FoodsUsage.DESCRIPTION.equals(columnName)) {
//                return FIELD_TYPE_STRING;
//            } else if (FoodsUsage.VALUE.equals(columnName)) {
//                return FIELD_TYPE_FLOAT;
//            } else {
//                throw new IllegalArgumentException("Unknown column " + columnName + " for table " + tableName);
//            }
//
//        } else if(WeekdayParameters.TABLE_NAME.equals(tableName)) {
//            if (WeekdayParameters._ID.equals(columnName)) {
//                return FIELD_TYPE_LONG;
//            } else if (WeekdayParameters.BREAKFAST_START_TIME.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.BREAKFAST_END_TIME.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.BREAKFAST_GOAL.equals(columnName)) {
//                return FIELD_TYPE_FLOAT;
//            } else if (WeekdayParameters.BRUNCH_START_TIME.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.BRUNCH_END_TIME.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.BRUNCH_GOAL.equals(columnName)) {
//                return FIELD_TYPE_FLOAT;
//            } else if (WeekdayParameters.LUNCH_START_TIME.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.LUNCH_END_TIME.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.LUNCH_GOAL.equals(columnName)) {
//                return FIELD_TYPE_FLOAT;
//            } else if (WeekdayParameters.SNACK_START_TIME.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.SNACK_END_TIME.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.SNACK_GOAL.equals(columnName)) {
//                return FIELD_TYPE_FLOAT;
//            } else if (WeekdayParameters.DINNER_START_TIME.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.DINNER_END_TIME.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.DINNER_GOAL.equals(columnName)) {
//                return FIELD_TYPE_FLOAT;
//            } else if (WeekdayParameters.SUPPER_START_TIME.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.SUPPER_END_TIME.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.SUPPER_GOAL.equals(columnName)) {
//                return FIELD_TYPE_FLOAT;
//            } else if (WeekdayParameters.EXERCISE_GOAL.equals(columnName)) {
//                return FIELD_TYPE_FLOAT;
//            } else if (WeekdayParameters.LIQUID_GOAL.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.OIL_GOAL.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else if (WeekdayParameters.SUPPLEMENT_GOAL.equals(columnName)) {
//                return FIELD_TYPE_INTEGER;
//            } else {
//                throw new IllegalArgumentException("Unknown column " + columnName + " for table " + tableName);
//            }
//
//        }
        // TODO: remove
        return 0;
    }
}
