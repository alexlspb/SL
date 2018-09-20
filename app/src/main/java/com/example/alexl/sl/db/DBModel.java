package com.example.alexl.sl.db;

import android.content.Context;

/**
 * Single tone database
 */

public class DBModel {

    private static DBSource dbSource;

    public static DBSource openDatabase(Context context) {
        if(dbSource == null) {
            dbSource = new DBSource(context);
        }
        dbSource.openDatabase();
        return dbSource;
    }

    public static void closeDatabase() {
        if(dbSource != null) {
            dbSource.closeDatabase();
        }
    }


}
