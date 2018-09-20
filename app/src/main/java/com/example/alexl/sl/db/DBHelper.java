package com.example.alexl.sl.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.alexl.sl.constants.DBConstants;
import com.example.alexl.sl.constants.OperationsConstants;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Create database Shopping List
 */

class DBHelper extends SQLiteAssetHelper {

    DBHelper(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, OperationsConstants.VERSION_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
