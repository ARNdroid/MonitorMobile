package br.gov.caixa.monitormobile.sqlite;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.gov.caixa.monitormobile.provider.actions.ActionsEntity;
import br.gov.caixa.monitormobile.provider.comments.CommentsEntity;
import br.gov.caixa.monitormobile.provider.followers.FollowersEntity;
import br.gov.caixa.monitormobile.provider.subscriptions.SubscriptionsEntity;
import br.gov.caixa.monitormobile.provider.xs.XsEntity;
import br.gov.caixa.monitormobile.utils.IssueUtils;
import br.gov.caixa.monitormobile.provider.Contract;
import br.gov.caixa.monitormobile.provider.issues.IssuesEntity;
import br.gov.caixa.monitormobile.provider.systems.SystemsEntity;
import br.gov.caixa.monitormobile.provider.users.UsersEntity;
import br.gov.caixa.monitormobile.utils.SubscriptionsUtils;
import br.gov.caixa.monitormobile.utils.TimeStampUtils;

public class DBScripts {

    // Utility:
    private DBScripts() {}

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

            // Followers:
            db.execSQL("CREATE TABLE " + Contract.Followers.TABLE_NAME + " ("
                    + Contract.Followers._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + Contract.Followers.ISSUE_ID + " INTEGER NOT NULL, "
                    + Contract.Followers.FOLLOWER_ID + " INTEGER NOT NULL);");
            db.execSQL("CREATE UNIQUE INDEX " + Contract.Followers.TABLE_NAME + "_"
                    + Contract.Followers.ISSUE_ID + "_" + Contract.Followers.FOLLOWER_ID + "_idx "
                    + "ON " + Contract.Followers.TABLE_NAME
                    + " (" + Contract.Followers.ISSUE_ID + ", " + Contract.Followers.FOLLOWER_ID + ");");

            // Xs:
            db.execSQL("CREATE TABLE " + Contract.Xs.TABLE_NAME + " ("
                    + Contract.Xs._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + Contract.Xs.ACTION_ID + " INTEGER NOT NULL, "
                    + Contract.Xs.LIKER_ID + " INTEGER NOT NULL);");
            db.execSQL("CREATE UNIQUE INDEX " + Contract.Xs.TABLE_NAME + "_"
                    + Contract.Xs.ACTION_ID + "_" + Contract.Xs.LIKER_ID + "_idx "
                    + "ON " + Contract.Xs.TABLE_NAME
                    + " (" + Contract.Xs.ACTION_ID + ", " + Contract.Xs.LIKER_ID + ");");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        populateDB(db);


    }

    public static void populateDB(SQLiteDatabase db) {

        // Users:
        List<UsersEntity> users = new ArrayList<UsersEntity>(8);
        users.add(new UsersEntity(null, "Alceu Neto"));
        users.add(new UsersEntity(null, "Bruno Maia"));
        users.add(new UsersEntity(null, "Eliane Queiroz"));
        users.add(new UsersEntity(null, "Sergio Medeiros"));
        users.add(new UsersEntity(null, "Diego Sohstein"));
        users.add(new UsersEntity(null, "Thiago Tatagiba"));
        users.add(new UsersEntity(null, "Nubio Revoredo"));
        users.add(new UsersEntity(null, "Marco Gutierrez"));
        db.beginTransaction();
        try {
            for (UsersEntity u : users) {
                final long id = db.insert(Contract.Users.TABLE_NAME, null, u.toContentValuesIgnoreNulls());
                u.setId(id);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        // Systems:
        List<SystemsEntity> systems = new ArrayList<SystemsEntity>(6);
        systems.add(new SystemsEntity(null, "SIXYZ", "Sistema XYZ"));
        systems.add(new SystemsEntity(null, "SICLI", "Sistema Cliente CAIXA"));
        systems.add(new SystemsEntity(null, "SICID", "Sistema Cartão do Cidadão"));
        systems.add(new SystemsEntity(null, "SIBEC", "Sistema de Benefícios Sociais"));
        systems.add(new SystemsEntity(null, "SICPF", "Sistema CPF"));
        systems.add(new SystemsEntity(null, "SIISO", "Sistema de Informações Sociais"));
        db.beginTransaction();
        try {
            for (SystemsEntity s : systems) {
                final long id = db.insert(Contract.Systems.TABLE_NAME, null, s.toContentValuesIgnoreNulls());
                s.setId(id);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        // Subscriptions:
        List<SubscriptionsEntity> subscriptions = new ArrayList<SubscriptionsEntity>(13);
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE_AND_FOLLOW, systems.get(0).getAcronymId(), users.get(0).getId()));
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,            systems.get(1).getAcronymId(), users.get(0).getId()));
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,            systems.get(2).getAcronymId(), users.get(0).getId()));
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,            systems.get(3).getAcronymId(), users.get(0).getId()));
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,            systems.get(4).getAcronymId(), users.get(0).getId()));
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,            systems.get(5).getAcronymId(), users.get(0).getId()));
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,            systems.get(2).getAcronymId(), users.get(1).getId()));
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,            systems.get(3).getAcronymId(), users.get(2).getId()));
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,            systems.get(4).getAcronymId(), users.get(3).getId()));
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,            systems.get(5).getAcronymId(), users.get(4).getId()));
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,            systems.get(0).getAcronymId(), users.get(5).getId()));
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,            systems.get(1).getAcronymId(), users.get(6).getId()));
        subscriptions.add(new SubscriptionsEntity(null, SubscriptionsUtils.MODE_TYPE_SUBSCRIBE,            systems.get(2).getAcronymId(), users.get(7).getId()));
        db.beginTransaction();
        try {
            for (SubscriptionsEntity s : subscriptions) {
                final long id = db.insert(Contract.Subscriptions.TABLE_NAME, null, s.toContentValuesIgnoreNulls());
                s.setId(id);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        // Issues:
        List<IssuesEntity> issues = new ArrayList<IssuesEntity>(30);
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_BLUE,   IssueUtils.CLOCK_BLUE,   "Incidente 1 do sistema XYZ",       "Descrição detalhada do incidente 1 do sistema XYZ.",        users.get(0).getId(), users.get(7).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_BLUE,   IssueUtils.CLOCK_YELLOW, "Incidente 2 do sistema XYZ",       "Descrição detalhada do incidente 2 do sistema XYZ.",        users.get(1).getId(), users.get(6).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_BLUE,   IssueUtils.CLOCK_RED,    "Incidente 3 do sistema XYZ",       "Descrição detalhada do incidente 3 do sistema XYZ.",        users.get(2).getId(), users.get(5).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_BLUE,   IssueUtils.CLOCK_BLACK,  "Incidente 4 do sistema XYZ",       "Descrição detalhada do incidente 4 do sistema XYZ.",        users.get(3).getId(), users.get(4).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_YELLOW, IssueUtils.CLOCK_BLUE,   "Incidente 5 do sistema XYZ",       "Descrição detalhada do incidente 5 do sistema XYZ.",        users.get(4).getId(), users.get(3).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_YELLOW, IssueUtils.CLOCK_YELLOW, "Incidente 6 do sistema XYZ",       "Descrição detalhada do incidente 6 do sistema XYZ.",        users.get(5).getId(), users.get(2).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_YELLOW, IssueUtils.CLOCK_RED,    "Incidente 7 do sistema XYZ",       "Descrição detalhada do incidente 7 do sistema XYZ.",        users.get(6).getId(), users.get(1).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_YELLOW, IssueUtils.CLOCK_BLACK,  "Incidente 8 do sistema XYZ",       "Descrição detalhada do incidente 8 do sistema XYZ.",        users.get(7).getId(), users.get(0).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_RED,    IssueUtils.CLOCK_BLUE,   "Incidente 9 do sistema XYZ",       "Descrição detalhada do incidente 9 do sistema XYZ.",        users.get(0).getId(), users.get(7).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_RED,    IssueUtils.CLOCK_YELLOW, "Incidente 10 do sistema XYZ",      "Descrição detalhada do incidente 10 do sistema XYZ.",       users.get(1).getId(), users.get(6).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_RED,    IssueUtils.CLOCK_RED,    "Incidente 11 do sistema XYZ",      "Descrição detalhada do incidente 11 do sistema XYZ.",       users.get(2).getId(), users.get(5).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_RED,    IssueUtils.CLOCK_BLACK,  "Incidente 12 do sistema XYZ",      "Descrição detalhada do incidente 12 do sistema XYZ.",       users.get(3).getId(), users.get(4).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_BLACK,  IssueUtils.CLOCK_BLUE,   "Incidente 13 do sistema XYZ",      "Descrição detalhada do incidente 13 do sistema XYZ.",       users.get(4).getId(), users.get(3).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_BLACK,  IssueUtils.CLOCK_YELLOW, "Incidente 14 do sistema XYZ",      "Descrição detalhada do incidente 14 do sistema XYZ.",       users.get(5).getId(), users.get(2).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_BLACK,  IssueUtils.CLOCK_RED,    "Incidente 15 do sistema XYZ",      "Descrição detalhada do incidente 15 do sistema XYZ.",       users.get(6).getId(), users.get(1).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_BLACK,  IssueUtils.CLOCK_BLACK,  "Incidente 16 do sistema XYZ",      "Descrição detalhada do incidente 16 do sistema XYZ.",       users.get(7).getId(), users.get(0).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_CLOSED, IssueUtils.FLAG_BLUE,   IssueUtils.CLOCK_BLACK,  "Incidente 17 do sistema XYZ",      "Descrição detalhada do incidente 17 do sistema XYZ.",       users.get(0).getId(), users.get(7).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_CLOSED, IssueUtils.FLAG_YELLOW, IssueUtils.CLOCK_RED,    "Incidente 18 do sistema XYZ",      "Descrição detalhada do incidente 18 do sistema XYZ.",       users.get(1).getId(), users.get(6).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_CLOSED, IssueUtils.FLAG_RED,    IssueUtils.CLOCK_YELLOW, "Incidente 19 do sistema XYZ",      "Descrição detalhada do incidente 19 do sistema XYZ.",       users.get(2).getId(), users.get(5).getId()));
        issues.add(new IssuesEntity(null, systems.get(0).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_CLOSED, IssueUtils.FLAG_BLACK,  IssueUtils.CLOCK_BLUE,   "Incidente 20 do sistema XYZ",      "Descrição detalhada do incidente 20 do sistema XYZ.",       users.get(3).getId(), users.get(4).getId()));
        issues.add(new IssuesEntity(null, systems.get(1).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_CLOSED, IssueUtils.FLAG_BLUE,   IssueUtils.CLOCK_BLACK,  "Um incidente do sistema SICLI",    "Descrição detalhada de um incidente  do sistema SICLI.",    users.get(4).getId(), users.get(5).getId()));
        issues.add(new IssuesEntity(null, systems.get(1).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_YELLOW, IssueUtils.CLOCK_RED,    "Outro incidente do sistema SICLI", "Descrição detalhada do outro incidente  do sistema SICLI.", users.get(5).getId(), users.get(6).getId()));
        issues.add(new IssuesEntity(null, systems.get(2).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_CLOSED, IssueUtils.FLAG_RED,    IssueUtils.CLOCK_YELLOW, "Um incidente do sistema SICID",    "Descrição detalhada de um incidente  do sistema SICID.",    users.get(6).getId(), users.get(7).getId()));
        issues.add(new IssuesEntity(null, systems.get(2).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_BLACK,  IssueUtils.CLOCK_BLUE,   "Outro incidente do sistema SICID", "Descrição detalhada do outro incidente  do sistema SICID.", users.get(7).getId(), users.get(0).getId()));
        issues.add(new IssuesEntity(null, systems.get(3).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_CLOSED, IssueUtils.FLAG_BLUE,   IssueUtils.CLOCK_RED,    "Um incidente do sistema SIBEC",    "Descrição detalhada de um incidente  do sistema SIBEC.",    users.get(0).getId(), users.get(1).getId()));
        issues.add(new IssuesEntity(null, systems.get(3).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_YELLOW, IssueUtils.CLOCK_BLACK,  "Outro incidente do sistema SIBEC", "Descrição detalhada do outro incidente  do sistema SIBEC.", users.get(1).getId(), users.get(2).getId()));
        issues.add(new IssuesEntity(null, systems.get(4).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_CLOSED, IssueUtils.FLAG_RED,    IssueUtils.CLOCK_BLUE,   "Um incidente do sistema SICPF",    "Descrição detalhada de um incidente  do sistema SICPF.",    users.get(2).getId(), users.get(3).getId()));
        issues.add(new IssuesEntity(null, systems.get(4).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_BLACK,  IssueUtils.CLOCK_YELLOW, "Outro incidente do sistema SICPF", "Descrição detalhada do outro incidente  do sistema SICPF.", users.get(3).getId(), users.get(4).getId()));
        issues.add(new IssuesEntity(null, systems.get(5).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_CLOSED, IssueUtils.FLAG_BLUE,   IssueUtils.CLOCK_YELLOW, "Um incidente do sistema SIISO",    "Descrição detalhada de um incidente  do sistema SIISO.",    users.get(4).getId(), users.get(5).getId()));
        issues.add(new IssuesEntity(null, systems.get(5).getAcronymId(), TimeStampUtils.dateToTimeStamp(new Date()), IssueUtils.STATE_OPEN,   IssueUtils.FLAG_YELLOW, IssueUtils.CLOCK_BLUE,   "Outro incidente do sistema SIISO", "Descrição detalhada do outro incidente  do sistema SIISO.", users.get(5).getId(), users.get(6).getId()));
        db.beginTransaction();
        try {
            for (IssuesEntity i : issues) {
                final long id = db.insert(Contract.Issues.TABLE_NAME, null, i.toContentValuesIgnoreNulls());
                i.setId(id);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        // Followers:
        List<FollowersEntity> followers = new ArrayList<FollowersEntity>(50);
        followers.add(new FollowersEntity(null, issues.get(0).getId(),  users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(1).getId(),  users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(2).getId(),  users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(3).getId(),  users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(4).getId(),  users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(5).getId(),  users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(6).getId(),  users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(7).getId(),  users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(8).getId(),  users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(9).getId(),  users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(10).getId(), users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(11).getId(), users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(12).getId(), users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(13).getId(), users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(14).getId(), users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(15).getId(), users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(16).getId(), users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(17).getId(), users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(18).getId(), users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(19).getId(), users.get(0).getId()));
        followers.add(new FollowersEntity(null, issues.get(20).getId(), users.get(1).getId()));
        followers.add(new FollowersEntity(null, issues.get(21).getId(), users.get(2).getId()));
        followers.add(new FollowersEntity(null, issues.get(22).getId(), users.get(3).getId()));
        followers.add(new FollowersEntity(null, issues.get(23).getId(), users.get(4).getId()));
        followers.add(new FollowersEntity(null, issues.get(24).getId(), users.get(5).getId()));
        followers.add(new FollowersEntity(null, issues.get(25).getId(), users.get(6).getId()));
        followers.add(new FollowersEntity(null, issues.get(26).getId(), users.get(7).getId()));
        followers.add(new FollowersEntity(null, issues.get(27).getId(), users.get(1).getId()));
        followers.add(new FollowersEntity(null, issues.get(28).getId(), users.get(2).getId()));
        followers.add(new FollowersEntity(null, issues.get(29).getId(), users.get(3).getId()));
        followers.add(new FollowersEntity(null, issues.get(0).getId(),  users.get(4).getId()));
        followers.add(new FollowersEntity(null, issues.get(1).getId(),  users.get(1).getId()));
        followers.add(new FollowersEntity(null, issues.get(2).getId(),  users.get(2).getId()));
        followers.add(new FollowersEntity(null, issues.get(3).getId(),  users.get(3).getId()));
        followers.add(new FollowersEntity(null, issues.get(4).getId(),  users.get(4).getId()));
        followers.add(new FollowersEntity(null, issues.get(5).getId(),  users.get(5).getId()));
        followers.add(new FollowersEntity(null, issues.get(6).getId(),  users.get(6).getId()));
        followers.add(new FollowersEntity(null, issues.get(7).getId(),  users.get(7).getId()));
        followers.add(new FollowersEntity(null, issues.get(8).getId(),  users.get(1).getId()));
        followers.add(new FollowersEntity(null, issues.get(9).getId(),  users.get(1).getId()));
        followers.add(new FollowersEntity(null, issues.get(0).getId(),  users.get(1).getId()));
        followers.add(new FollowersEntity(null, issues.get(1).getId(),  users.get(2).getId()));
        followers.add(new FollowersEntity(null, issues.get(2).getId(),  users.get(3).getId()));
        followers.add(new FollowersEntity(null, issues.get(3).getId(),  users.get(4).getId()));
        followers.add(new FollowersEntity(null, issues.get(4).getId(),  users.get(5).getId()));
        followers.add(new FollowersEntity(null, issues.get(5).getId(),  users.get(6).getId()));
        followers.add(new FollowersEntity(null, issues.get(6).getId(),  users.get(7).getId()));
        followers.add(new FollowersEntity(null, issues.get(7).getId(),  users.get(1).getId()));
        followers.add(new FollowersEntity(null, issues.get(8).getId(),  users.get(2).getId()));
        followers.add(new FollowersEntity(null, issues.get(9).getId(),  users.get(3).getId()));
        db.beginTransaction();
        try {
            for (FollowersEntity f : followers) {
                final long id = db.insert(Contract.Followers.TABLE_NAME, null, f.toContentValuesIgnoreNulls());
                f.setId(id);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        // Actions:
        List<ActionsEntity> actions = new ArrayList<ActionsEntity>(39);
        actions.add(new ActionsEntity(null, issues.get(0).getId(),  issues.get(0).getTimeStamp(),  "Abertura",   "Abertura do incidente",                                                        issues.get(0).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(1).getId(),  issues.get(1).getTimeStamp(),  "Abertura",   "Abertura do incidente",                                                        issues.get(1).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(2).getId(),  issues.get(2).getTimeStamp(),  "Abertura",   "Abertura do incidente",                                                        issues.get(2).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(3).getId(),  issues.get(3).getTimeStamp(),  "Abertura",   "Abertura do incidente",                                                        issues.get(3).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(4).getId(),  issues.get(4).getTimeStamp(),  "Abertura",   "Abertura do incidente",                                                        issues.get(4).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(5).getId(),  issues.get(5).getTimeStamp(),  "Abertura",   "Abertura do incidente",                                                        issues.get(5).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(6).getId(),  issues.get(6).getTimeStamp(),  "Abertura",   "Abertura do incidente",                                                        issues.get(6).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(7).getId(),  issues.get(7).getTimeStamp(),  "Abertura",   "Abertura do incidente",                                                        issues.get(7).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(8).getId(),  issues.get(8).getTimeStamp(),  "Abertura",   "Abertura do incidente",                                                        issues.get(8).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(9).getId(),  issues.get(9).getTimeStamp(),  "Abertura",   "Abertura do incidente",                                                        issues.get(9).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(10).getId(), issues.get(10).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(10).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(11).getId(), issues.get(11).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(11).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(12).getId(), issues.get(12).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(12).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(13).getId(), issues.get(13).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(13).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(14).getId(), issues.get(14).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(14).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(15).getId(), issues.get(15).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(15).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(16).getId(), issues.get(16).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(16).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(17).getId(), issues.get(17).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(17).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(18).getId(), issues.get(18).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(18).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(19).getId(), issues.get(19).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(19).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(20).getId(), issues.get(20).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(20).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(21).getId(), issues.get(21).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(21).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(22).getId(), issues.get(22).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(22).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(23).getId(), issues.get(23).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(23).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(24).getId(), issues.get(24).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(24).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(25).getId(), issues.get(25).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(25).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(26).getId(), issues.get(26).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(26).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(27).getId(), issues.get(27).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(27).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(28).getId(), issues.get(28).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(28).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(29).getId(), issues.get(29).getTimeStamp(), "Abertura",   "Abertura do incidente",                                                        issues.get(29).getReporterId()));
        actions.add(new ActionsEntity(null, issues.get(16).getId(), issues.get(16).getTimeStamp(), "Fechamento", "Fechamento do incidente pelo motivo [aqui uma descrição detalhada do motivo]", users.get(0).getId()));
        actions.add(new ActionsEntity(null, issues.get(17).getId(), issues.get(17).getTimeStamp(), "Fechamento", "Fechamento do incidente pelo motivo [aqui uma descrição detalhada do motivo]", users.get(1).getId()));
        actions.add(new ActionsEntity(null, issues.get(18).getId(), issues.get(18).getTimeStamp(), "Fechamento", "Fechamento do incidente pelo motivo [aqui uma descrição detalhada do motivo]", users.get(2).getId()));
        actions.add(new ActionsEntity(null, issues.get(19).getId(), issues.get(19).getTimeStamp(), "Fechamento", "Fechamento do incidente pelo motivo [aqui uma descrição detalhada do motivo]", users.get(3).getId()));
        actions.add(new ActionsEntity(null, issues.get(20).getId(), issues.get(20).getTimeStamp(), "Fechamento", "Fechamento do incidente pelo motivo [aqui uma descrição detalhada do motivo]", users.get(4).getId()));
        actions.add(new ActionsEntity(null, issues.get(22).getId(), issues.get(22).getTimeStamp(), "Fechamento", "Fechamento do incidente pelo motivo [aqui uma descrição detalhada do motivo]", users.get(5).getId()));
        actions.add(new ActionsEntity(null, issues.get(24).getId(), issues.get(24).getTimeStamp(), "Fechamento", "Fechamento do incidente pelo motivo [aqui uma descrição detalhada do motivo]", users.get(6).getId()));
        actions.add(new ActionsEntity(null, issues.get(26).getId(), issues.get(26).getTimeStamp(), "Fechamento", "Fechamento do incidente pelo motivo [aqui uma descrição detalhada do motivo]", users.get(7).getId()));
        actions.add(new ActionsEntity(null, issues.get(28).getId(), issues.get(28).getTimeStamp(), "Fechamento", "Fechamento do incidente pelo motivo [aqui uma descrição detalhada do motivo]", users.get(0).getId()));
        db.beginTransaction();
        try {
            for (ActionsEntity a : actions) {
                final long id = db.insert(Contract.Actions.TABLE_NAME, null, a.toContentValuesIgnoreNulls());
                a.setId(id);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        // Comments:
        List<CommentsEntity> comments = new ArrayList<CommentsEntity>(7);
        comments.add(new CommentsEntity(null, actions.get(31).getId(), actions.get(31).getTimeStamp(), "Um exemplo de comentário...", users.get(0).getId()));
        comments.add(new CommentsEntity(null, actions.get(32).getId(), actions.get(32).getTimeStamp(), "Um exemplo de comentário...", users.get(0).getId()));
        comments.add(new CommentsEntity(null, actions.get(33).getId(), actions.get(33).getTimeStamp(), "Um exemplo de comentário...", users.get(0).getId()));
        comments.add(new CommentsEntity(null, actions.get(34).getId(), actions.get(34).getTimeStamp(), "Um exemplo de comentário...", users.get(0).getId()));
        comments.add(new CommentsEntity(null, actions.get(35).getId(), actions.get(35).getTimeStamp(), "Um exemplo de comentário...", users.get(0).getId()));
        comments.add(new CommentsEntity(null, actions.get(36).getId(), actions.get(36).getTimeStamp(), "Um exemplo de comentário...", users.get(0).getId()));
        comments.add(new CommentsEntity(null, actions.get(37).getId(), actions.get(37).getTimeStamp(), "Um exemplo de comentário...", users.get(0).getId()));
        db.beginTransaction();
        try {
            for (CommentsEntity c : comments) {
                final long id = db.insert(Contract.Comments.TABLE_NAME, null, c.toContentValuesIgnoreNulls());
                c.setId(id);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        // Xs:
        List<XsEntity> xs = new ArrayList<XsEntity>(4);
        xs.add(new XsEntity(null, actions.get(31).getId(), users.get(0).getId()));
        xs.add(new XsEntity(null, actions.get(32).getId(), users.get(0).getId()));
        xs.add(new XsEntity(null, actions.get(33).getId(), users.get(0).getId()));
        xs.add(new XsEntity(null, actions.get(34).getId(), users.get(0).getId()));
        db.beginTransaction();
        try {
            for (XsEntity x : xs) {
                final long id = db.insert(Contract.Xs.TABLE_NAME, null, x.toContentValuesIgnoreNulls());
                x.setId(id);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static void cleanDB(SQLiteDatabase db) {
        // Xs:
        db.delete(Contract.Xs.TABLE_NAME, null, null);

        // Comments:
        db.delete(Contract.Comments.TABLE_NAME, null, null);

        // Actions:
        db.delete(Contract.Actions.TABLE_NAME, null, null);

        // Followers:
        db.delete(Contract.Followers.TABLE_NAME, null, null);

        // Issues:
        db.delete(Contract.Issues.TABLE_NAME, null, null);

        // Subscriptions:
        db.delete(Contract.Subscriptions.TABLE_NAME, null, null);

        // Systems:
        db.delete(Contract.Systems.TABLE_NAME, null, null);

        // Users:
        db.delete(Contract.Users.TABLE_NAME, null, null);
    }
}