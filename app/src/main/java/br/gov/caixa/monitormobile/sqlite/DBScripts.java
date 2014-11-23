package br.gov.caixa.monitormobile.sqlite;

import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import br.gov.caixa.monitormobile.provider.actions.ActionsEntity;
import br.gov.caixa.monitormobile.provider.comments.CommentsEntity;
import br.gov.caixa.monitormobile.provider.subscriptions.SubscriptionsEntity;
import br.gov.caixa.monitormobile.utils.IssueUtils;
import br.gov.caixa.monitormobile.provider.Contract;
import br.gov.caixa.monitormobile.provider.issues.IssuesEntity;
import br.gov.caixa.monitormobile.provider.systems.SystemsEntity;
import br.gov.caixa.monitormobile.provider.users.UsersEntity;
import br.gov.caixa.monitormobile.utils.SubscriptionsUtils;
import br.gov.caixa.monitormobile.utils.TimeStampUtils;

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

            // Actions:
            db.execSQL("CREATE TABLE " + Contract.Actions.TABLE_NAME + " ("
                    + Contract.Actions._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + Contract.Actions.ISSUE_ID + " INTEGER NOT NULL, "
                    + Contract.Actions.TIME_STAMP + " TEXT NOT NULL, "
                    + Contract.Actions.SUMMARY + " TEXT NOT NULL, "
                    + Contract.Actions.DESCRIPTION + " TEXT NOT NULL, "
                    + Contract.Actions.AGENT_ID + " INTEGER NOT NULL);");

            // Comments:
            db.execSQL("CREATE TABLE " + Contract.Comments.TABLE_NAME + " ("
                    + Contract.Comments._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + Contract.Comments.ACTION_ID + " INTEGER NOT NULL, "
                    + Contract.Comments.TIME_STAMP + " TEXT NOT NULL, "
                    + Contract.Comments.DESCRIPTION + " TEXT NOT NULL, "
                    + Contract.Comments.COMMENTER_ID + " INTEGER NOT NULL);");

            // Subscriptions:
            db.execSQL("CREATE TABLE " + Contract.Subscriptions.TABLE_NAME + " ("
                    + Contract.Subscriptions._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + Contract.Subscriptions.MODE_TYPE + " INTEGER NOT NULL, "
                    + Contract.Subscriptions.ACRONYM_ID + " TEXT NOT NULL, "
                    + Contract.Subscriptions.SUBSCRIBER_ID + " INTEGER NOT NULL);");
            db.execSQL("CREATE UNIQUE INDEX " + Contract.Subscriptions.TABLE_NAME + "_"
                    + Contract.Subscriptions.ACRONYM_ID + "_" + Contract.Subscriptions.SUBSCRIBER_ID + "_idx "
                    + "ON " + Contract.Subscriptions.TABLE_NAME
                    + " (" + Contract.Subscriptions.ACRONYM_ID + ", " + Contract.Subscriptions.SUBSCRIBER_ID + ");");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        // Data Population

        // Users:
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

        // Systems:
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

        // Issues:
        db.beginTransaction();
        try {
            IssuesEntity issuesEntity = new IssuesEntity(null, "SI001", TimeStampUtils.dateToTimeStamp(new Date()),
                    IssueUtils.STATE_OPEN, IssueUtils.FLAG_BLACK, IssueUtils.CLOCK_BLUE, "Summary SI001",
                    "Description SI001", 1L, 2L);
            db.insert(Contract.Issues.TABLE_NAME, null,
                    issuesEntity.toContentValuesIgnoreNulls());
            issuesEntity = new IssuesEntity(null, "SI002", "201411231734",
                    IssueUtils.STATE_OPEN, IssueUtils.FLAG_BLUE, IssueUtils.CLOCK_RED, "Summary SI002",
                    "Description SI002", 2L, 2L);
            db.insert(Contract.Issues.TABLE_NAME, null,
                    issuesEntity.toContentValuesIgnoreNulls());
            issuesEntity = new IssuesEntity(null, "SI003", "20141124734",
                    IssueUtils.STATE_OPEN, IssueUtils.FLAG_RED, IssueUtils.CLOCK_YELLOW, "Summary SI003",
                    "Description SI003", 3L, 2L);
            db.insert(Contract.Issues.TABLE_NAME, null,
                    issuesEntity.toContentValuesIgnoreNulls());
            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }

        // Actions:
        db.beginTransaction();
        try {
            ActionsEntity actionsEntity = new ActionsEntity(null, 1L, TimeStampUtils.dateToTimeStamp(new Date()),
                    "Summary action 001", "Description action 001", 1L);
            db.insert(Contract.Actions.TABLE_NAME, null,
                    actionsEntity.toContentValuesIgnoreNulls());
            actionsEntity = new ActionsEntity(null, 1L, TimeStampUtils.dateToTimeStamp(new Date()),
                    "Summary action 002", "Description action 002", 2L);
            db.insert(Contract.Actions.TABLE_NAME, null,
                    actionsEntity.toContentValuesIgnoreNulls());
            actionsEntity = new ActionsEntity(null, 2L, TimeStampUtils.dateToTimeStamp(new Date()),
                    "Summary action 003", "Description action 003", 3L);
            db.insert(Contract.Actions.TABLE_NAME, null,
                    actionsEntity.toContentValuesIgnoreNulls());
            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }

        // Comments:
        db.beginTransaction();
        try {
            CommentsEntity commentsEntity = new CommentsEntity(null, 1L, TimeStampUtils.dateToTimeStamp(new Date()),
                    "Description comment 001", 1L);
            db.insert(Contract.Comments.TABLE_NAME, null,
                    commentsEntity.toContentValuesIgnoreNulls());
            commentsEntity = new CommentsEntity(null, 1L, TimeStampUtils.dateToTimeStamp(new Date()),
                    "Description comment 002", 2L);
            db.insert(Contract.Comments.TABLE_NAME, null,
                    commentsEntity.toContentValuesIgnoreNulls());
            commentsEntity = new CommentsEntity(null, 2L, TimeStampUtils.dateToTimeStamp(new Date()),
                    "Description comment 003", 3L);
            db.insert(Contract.Comments.TABLE_NAME, null,
                    commentsEntity.toContentValuesIgnoreNulls());
            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }

        // Subscriptions:
        db.beginTransaction();
        try {
            SubscriptionsEntity subscriptionsEntity = new SubscriptionsEntity(null,
                    SubscriptionsUtils.MODE_TYPE_SUBSCRIBE, "SI001", 1L);
            db.insert(Contract.Subscriptions.TABLE_NAME, null,
                    subscriptionsEntity.toContentValuesIgnoreNulls());
            subscriptionsEntity = new SubscriptionsEntity(null,
                    SubscriptionsUtils.MODE_TYPE_SUBSCRIBE, "SI001", 2L);
            db.insert(Contract.Subscriptions.TABLE_NAME, null,
                    subscriptionsEntity.toContentValuesIgnoreNulls());
            subscriptionsEntity = new SubscriptionsEntity(null,
                    SubscriptionsUtils.MODE_TYPE_SUBSCRIBE, "SI002", 1L);
            db.insert(Contract.Subscriptions.TABLE_NAME, null,
                    subscriptionsEntity.toContentValuesIgnoreNulls());
            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }
}