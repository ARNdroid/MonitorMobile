package br.gov.caixa.monitormobile.sqlite;

import android.database.sqlite.SQLiteDatabase;

import br.gov.caixa.monitormobile.issue.Issue;
import br.gov.caixa.monitormobile.provider.Contract;
import br.gov.caixa.monitormobile.provider.issues.IssuesEntity;
import br.gov.caixa.monitormobile.provider.systems.SystemsEntity;
import br.gov.caixa.monitormobile.provider.users.UsersEntity;

public class DBScripts {

    public static void scriptV00ToV01(SQLiteDatabase db) {
        // Tables Creation

        db.beginTransaction();
        try {
            // Users:
            db.execSQL("CREATE TABLE " + Contract.Users.TABLE_NAME + " ("
                    + Contract.Users._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + Contract.Users.SHORT_NAME + " TEXT NOT NULL);");
            db.execSQL("CREATE UNIQUE INDEX " + Contract.Users.TABLE_NAME + "_"
                    + Contract.Users.SHORT_NAME + "_idx "
                    + "ON " + Contract.Users.TABLE_NAME + " (" + Contract.Users.SHORT_NAME + ");");

            // Systems:
            db.execSQL("CREATE TABLE " + Contract.Systems.TABLE_NAME + " ("
                    + Contract.Systems._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + Contract.Systems.ACRONYM_ID + " TEXT NOT NULL, "
                    + Contract.Systems.DESCRIPTION + " TEXT NOT NULL);");
            db.execSQL("CREATE UNIQUE INDEX " + Contract.Systems.TABLE_NAME + "_"
                    + Contract.Systems.ACRONYM_ID + "_idx "
                    + "ON " + Contract.Systems.TABLE_NAME + " (" + Contract.Systems.ACRONYM_ID + ");");

            // Issues:
            db.execSQL("CREATE TABLE " + Contract.Issues.TABLE_NAME + " ("
                    + Contract.Issues._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + Contract.Issues.ACRONYM_ID + " TEXT NOT NULL, "
                    + Contract.Issues.TIME_STAMP + " TEXT NOT NULL, "
                    + Contract.Issues.STATE + " INTEGER NOT NULL, "
                    + Contract.Issues.FLAG_TYPE + " INTEGER NOT NULL, "
                    + Contract.Issues.CLOCK_TYPE + " INTEGER NOT NULL, "
                    + Contract.Issues.SUMMARY + " TEXT NOT NULL, "
                    + Contract.Issues.DESCRIPTION + " TEXT NOT NULL, "
                    + Contract.Issues.REPORTER_ID + " INTEGER NOT NULL, "
                    + Contract.Issues.OWNER_ID + " INTEGER NOT NULL);");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        // Data Population

        db.beginTransaction();
        try {
            final UsersEntity usersEntity = new UsersEntity(null, null);
            usersEntity.setShortName("User 1");
            db.insert(Contract.Users.TABLE_NAME, null,
                    usersEntity.toContentValuesIgnoreNulls());
            usersEntity.setShortName("User 2");
            db.insert(Contract.Users.TABLE_NAME, null,
                    usersEntity.toContentValuesIgnoreNulls());
            usersEntity.setShortName("User 3");
            db.insert(Contract.Users.TABLE_NAME, null,
                    usersEntity.toContentValuesIgnoreNulls());

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }

        db.beginTransaction();
        try {
            final SystemsEntity systemsEntity = new SystemsEntity(null, null, null);
            systemsEntity.setAcronymId("SI001");
            systemsEntity.setDescription("Description for SI001");
            db.insert(Contract.Systems.TABLE_NAME, null,
                    systemsEntity.toContentValuesIgnoreNulls());
            systemsEntity.setAcronymId("SI002");
            systemsEntity.setDescription("Description for SI002");
            db.insert(Contract.Systems.TABLE_NAME, null,
                    systemsEntity.toContentValuesIgnoreNulls());
            systemsEntity.setAcronymId("SI003");
            systemsEntity.setDescription("Description for SI003");
            db.insert(Contract.Systems.TABLE_NAME, null,
                    systemsEntity.toContentValuesIgnoreNulls());

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }

        db.beginTransaction();
        try {
            IssuesEntity issuesEntity = new IssuesEntity(null, "SI001", "201411221734",
                    Issue.STATE_OPEN, Issue.FLAG_BLACK, Issue.CLOCK_BLUE, "Summary SI001",
                    "Description SI001", 1L, 2L);
            db.insert(Contract.Issues.TABLE_NAME, null,
                    issuesEntity.toContentValuesIgnoreNulls());
            issuesEntity = new IssuesEntity(null, "SI002", "201411231734",
                    Issue.STATE_OPEN, Issue.FLAG_BLUE, Issue.CLOCK_RED, "Summary SI002",
                    "Description SI002", 2L, 2L);
            db.insert(Contract.Issues.TABLE_NAME, null,
                    issuesEntity.toContentValuesIgnoreNulls());
            issuesEntity = new IssuesEntity(null, "SI003", "20141124734",
                    Issue.STATE_OPEN, Issue.FLAG_RED, Issue.CLOCK_YELLOW, "Summary SI003",
                    "Description SI003", 3L, 2L);
            db.insert(Contract.Systems.TABLE_NAME, null,
                    issuesEntity.toContentValuesIgnoreNulls());
            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }
}