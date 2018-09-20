package com.example.alexl.sl.thread;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.example.alexl.sl.bean.Category;
import com.example.alexl.sl.bean.ItemObject;
import com.example.alexl.sl.constants.DBConstants;
import com.example.alexl.sl.db.DBModel;
import com.example.alexl.sl.dialog.OrderCartDialogFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Лоадер категорий с сортировкой в алфавитном порядке
 */

public class CategoriesLoader extends AsyncTaskLoader<ArrayList<ItemObject>> {

    public CategoriesLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<ItemObject> loadInBackground() {
        ArrayList<ItemObject> categories = new ArrayList<>();
        Cursor cursor = DBModel.openDatabase(getContext()).getCursorCategory();
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                long categoryId = cursor.getLong(cursor.getColumnIndex(DBConstants.ID));
                String categoryName = cursor.getString(cursor.getColumnIndex(DBConstants.COL_NAME_CATEGORY));
                categories.add(new Category(categoryId, categoryName));
            }
        }
        Collections.sort(categories, new Comparator<ItemObject>() {
            @Override
            public int compare(ItemObject categories1, ItemObject categories2) {
                return categories1.getName().compareTo(categories2.getName());
            }
        });
        categories.add(new Category(OrderCartDialogFragment.ADD_CATEGORY, "Добавить"));
        return categories;
    }
}
