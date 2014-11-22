package br.gov.caixa.monitormobile.sqlite;

import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;
import java.util.Date;

import br.gov.caixa.monitormobile.provider.Contract;
import br.gov.caixa.monitormobile.provider.users.UsersEntity;

public class DBScripts {

    public static void scriptV00ToV01(SQLiteDatabase db) {
        // Tables Creation
        db.beginTransaction();
        try {
            // Users:
            db.execSQL("CREATE TABLE " + Contract.Users.TABLE_NAME + " ("
                    + Contract.Users._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + Contract.Users.SHORT_NAME + " TEXT);");
            db.execSQL("CREATE UNIQUE INDEX " + Contract.Users.TABLE_NAME + "_"
                    + Contract.Users.SHORT_NAME + "_idx "
                    + "ON " + Contract.Users.TABLE_NAME + " (" + Contract.Users.SHORT_NAME + ");");
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

    }
}