package com.example.alexl.sl.thread;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.example.alexl.sl.db.DBModel;

/**
 * Класс асинхронно обновляет данные в адаптере ShoppingList получая данные из БД
 */

public class ShoppingListCursorLoader extends CursorLoader {

    public ShoppingListCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return DBModel.openDatabase(getContext()).getCursorShoppingList();
    }
}
