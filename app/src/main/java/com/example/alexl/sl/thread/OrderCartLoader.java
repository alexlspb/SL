package com.example.alexl.sl.thread;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.example.alexl.sl.bean.Category;
import com.example.alexl.sl.bean.ItemObject;
import com.example.alexl.sl.bean.OrderCart;
import com.example.alexl.sl.constants.DBConstants;
import com.example.alexl.sl.db.DBModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Класс асинхронно обновляет данные в адаптере OrderCart получая данные из БД
 */

public class OrderCartLoader extends AsyncTaskLoader<ArrayList<ItemObject>> {

    private long idShoppingList;

    public OrderCartLoader(Context context, long idShoppingList) {
        super(context);
        this.idShoppingList = idShoppingList;
    }

    @Override
    public ArrayList<ItemObject> loadInBackground() {

        Map<String, ArrayList<OrderCart>> orderCartInCategory = new TreeMap<>();

        Cursor cursor = DBModel.openDatabase(getContext()).getCursorForItemOrderCart(idShoppingList, 0);

        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                orderCartInCategory.put(cursor.getString(cursor.getColumnIndex("category_name")), new ArrayList<OrderCart>());
            }
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String categoryName = cursor.getString(cursor.getColumnIndex("category_name"));
                long idProduct = cursor.getLong(cursor.getColumnIndex(DBConstants.ID));
                String nameProduct = cursor.getString(cursor.getColumnIndex(DBConstants.COL_NAME_PRODUCT));
                int countProduct = cursor.getInt(cursor.getColumnIndex(DBConstants.COL_COUNT_ORDER_CART));
                int priceProduct = cursor.getInt(cursor.getColumnIndex(DBConstants.COL_PRICE_ORDER_CART));
                int statusProduct = cursor.getInt(cursor.getColumnIndex(DBConstants.COL_STATUS_ORDER_CART));

                orderCartInCategory.get(categoryName).add(new OrderCart(idProduct, nameProduct, countProduct, priceProduct, statusProduct));
            }
        }

        cursor = DBModel.openDatabase(getContext()).getCursorForItemOrderCart(idShoppingList, 1);

        if (cursor.getCount() != 0) {
            orderCartInCategory.put("цВ корзине", new ArrayList<OrderCart>());
            ArrayList<OrderCart> orderCartsInBasket = orderCartInCategory.get("цВ корзине");
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                long idProduct = cursor.getLong(cursor.getColumnIndex(DBConstants.ID));
                String nameProduct = cursor.getString(cursor.getColumnIndex(DBConstants.COL_NAME_PRODUCT));
                int countProduct = cursor.getInt(cursor.getColumnIndex(DBConstants.COL_COUNT_ORDER_CART));
                int priceProduct = cursor.getInt(cursor.getColumnIndex(DBConstants.COL_PRICE_ORDER_CART));
                int statusProduct = cursor.getInt(cursor.getColumnIndex(DBConstants.COL_STATUS_ORDER_CART));
                orderCartsInBasket.add(new OrderCart(idProduct, nameProduct, countProduct, priceProduct, statusProduct));
            }
        }

        ArrayList<ItemObject> categoriesAndOrderCarts = new ArrayList<>();
        for (String s : orderCartInCategory.keySet()) {
            categoriesAndOrderCarts.add(new Category(s));
            for (OrderCart orderCart : orderCartInCategory.get(s)) {
                categoriesAndOrderCarts.add(orderCart);
            }
        }

        return categoriesAndOrderCarts;
    }
}
