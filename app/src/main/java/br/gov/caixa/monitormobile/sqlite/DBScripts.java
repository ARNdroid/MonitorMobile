package br.gov.caixa.monitormobile.sqlite;

import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;
import java.util.Date;

public class DBScripts {

    public static void scriptV00ToV01(SQLiteDatabase db) {
        // Tables Creation
        db.beginTransaction();
        try {
            // Days:
//            db.execSQL("CREATE TABLE " + Contract.Days.TABLE_NAME + " ("
//                    + Contract.Days._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
//                    + Contract.Days.DATE_ID + " TEXT NOT NULL, "
//                    + Contract.Days.ALLOWED + " REAL NOT NULL, "
//                    + Contract.Days.BREAKFAST_START_TIME + " INTEGER NOT NULL, "
//                    + Contract.Days.BREAKFAST_END_TIME + " INTEGER NOT NULL, "
//                    + Contract.Days.BREAKFAST_GOAL + " REAL NOT NULL, "
//                    + Contract.Days.BRUNCH_START_TIME + " INTEGER NOT NULL, "
//                    + Contract.Days.BRUNCH_END_TIME + " INTEGER NOT NULL, "
//                    + Contract.Days.BRUNCH_GOAL + " REAL NOT NULL, "
//                    + Contract.Days.LUNCH_START_TIME + " INTEGER NOT NULL, "
//                    + Contract.Days.LUNCH_END_TIME + " INTEGER NOT NULL, "
//                    + Contract.Days.LUNCH_GOAL + " REAL NOT NULL, "
//                    + Contract.Days.SNACK_START_TIME + " INTEGER NOT NULL, "
//                    + Contract.Days.SNACK_END_TIME + " INTEGER NOT NULL, "
//                    + Contract.Days.SNACK_GOAL + " REAL NOT NULL, "
//                    + Contract.Days.DINNER_START_TIME + " INTEGER NOT NULL, "
//                    + Contract.Days.DINNER_END_TIME + " INTEGER NOT NULL, "
//                    + Contract.Days.DINNER_GOAL + " REAL NOT NULL, "
//                    + Contract.Days.SUPPER_START_TIME + " INTEGER NOT NULL, "
//                    + Contract.Days.SUPPER_END_TIME + " INTEGER NOT NULL, "
//                    + Contract.Days.SUPPER_GOAL + " REAL NOT NULL, "
//                    + Contract.Days.EXERCISE_GOAL + " REAL NOT NULL, "
//                    + Contract.Days.LIQUID_DONE + " INTEGER NOT NULL, "
//                    + Contract.Days.LIQUID_GOAL + " INTEGER NOT NULL, "
//                    + Contract.Days.OIL_DONE + " INTEGER NOT NULL, "
//                    + Contract.Days.OIL_GOAL + " INTEGER NOT NULL, "
//                    + Contract.Days.SUPPLEMENT_DONE + " INTEGER NOT NULL, "
//                    + Contract.Days.SUPPLEMENT_GOAL + " INTEGER NOT NULL, "
//                    + Contract.Days.NOTE + " TEXT);");
//            db.execSQL("CREATE UNIQUE INDEX " + Contract.Days.TABLE_NAME + "_"
//                    + Contract.Days.DATE_ID + "_idx "
//                    + "ON " + Contract.Days.TABLE_NAME + " (" + Contract.Days.DATE_ID + ");");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        // Data Population
        db.beginTransaction();
        try {
//            weekdayParametersEntity.setId((long) Calendar.SATURDAY);
//            db.insert(Contract.WeekdayParameters.TABLE_NAME, null,
//                    weekdayParametersEntity.toContentValuesIgnoreNulls());

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }

    }
}