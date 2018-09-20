package com.example.alexl.sl.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alexl.sl.assistans.StringAssistant;
import com.example.alexl.sl.bean.Category;
import com.example.alexl.sl.bean.OrderCart;
import com.example.alexl.sl.bean.Product;
import com.example.alexl.sl.bean.ShoppingList;
import com.example.alexl.sl.constants.DBConstants;

import static com.example.alexl.sl.constants.DBConstants.COL_COLOR_CATEGORY;
import static com.example.alexl.sl.constants.DBConstants.COL_COUNT_ORDER_CART;
import static com.example.alexl.sl.constants.DBConstants.COL_DATE_CREATED_SHOPPING_LIST;
import static com.example.alexl.sl.constants.DBConstants.COL_ID_CATEGORY_PRODUCT;
import static com.example.alexl.sl.constants.DBConstants.COL_ID_PRODUCT_ORDER_CART;
import static com.example.alexl.sl.constants.DBConstants.COL_ID_SHOPPING_LIST_ORDER_CART;
import static com.example.alexl.sl.constants.DBConstants.COL_NAME_CATEGORY;
import static com.example.alexl.sl.constants.DBConstants.COL_NAME_PRODUCT;
import static com.example.alexl.sl.constants.DBConstants.COL_NAME_SHOPPING_LIST;
import static com.example.alexl.sl.constants.DBConstants.COL_PRICE_ORDER_CART;
import static com.example.alexl.sl.constants.DBConstants.COL_PRICE_PRODUCT;
import static com.example.alexl.sl.constants.DBConstants.COL_STATUS_ORDER_CART;
import static com.example.alexl.sl.constants.DBConstants.COL_STATUS_SHOPPING_LIST;
import static com.example.alexl.sl.constants.DBConstants.COL_UNIT_PRODUCT;
import static com.example.alexl.sl.constants.DBConstants.ID;
import static com.example.alexl.sl.constants.DBConstants.TABLE_CATEGORY;
import static com.example.alexl.sl.constants.DBConstants.TABLE_ORDER_CART;
import static com.example.alexl.sl.constants.DBConstants.COL_COMMENT_ORDER_CART;
import static com.example.alexl.sl.constants.DBConstants.COL_MUST_BAY_ORDER_CART;
import static com.example.alexl.sl.constants.DBConstants.TABLE_PRODUCT;
import static com.example.alexl.sl.constants.DBConstants.TABLE_SHOPPING_LIST;

/**
 * Data base management control
 */

public class DBSource {

    private SQLiteDatabase database;

    private SQLiteOpenHelper dbHelper;

    DBSource (Context context) {
        this.dbHelper = new DBHelper(context);
    }

    void openDatabase() {
        this.database = this.dbHelper.getWritableDatabase();
    }

    void closeDatabase() {
        this.database.close();
    }

    public long createShoppingList(ShoppingList shoppingList) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME_SHOPPING_LIST, shoppingList.getName());
        cv.put(COL_DATE_CREATED_SHOPPING_LIST, shoppingList.getDateCreated());
        cv.put(COL_STATUS_SHOPPING_LIST, shoppingList.getStatus());
        return database.insert(
                TABLE_SHOPPING_LIST,
                null,
                cv);
    }

    public long removeShoppingList(ShoppingList shoppingList) {
        return database.delete(
                TABLE_SHOPPING_LIST,
                StringAssistant.getString(ID, "=", String.valueOf(shoppingList.getId())),
                null);
    }

    public void updateShoppingList(ShoppingList shoppingList) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME_SHOPPING_LIST, shoppingList.getName());
        database.update(
                TABLE_SHOPPING_LIST,
                cv,
                StringAssistant.getString(ID, "=", String.valueOf(shoppingList.getId())),
                null);
    }

    public Cursor getCursorShoppingList() {
        return database.query(
                TABLE_SHOPPING_LIST,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public long createOrderCart(OrderCart orderCart) {
        ContentValues cv = new ContentValues();
        cv.put(COL_ID_PRODUCT_ORDER_CART, orderCart.getIdProduct());
        cv.put(COL_ID_SHOPPING_LIST_ORDER_CART, orderCart.getIdShoppingList());
        cv.put(COL_COUNT_ORDER_CART, orderCart.getCount());
        cv.put(COL_PRICE_ORDER_CART, orderCart.getPrice());
        cv.put(COL_MUST_BAY_ORDER_CART, orderCart.getMustBay());
        cv.put(COL_COMMENT_ORDER_CART, orderCart.getComment());
        cv.put(COL_STATUS_ORDER_CART, orderCart.getStatus());
        return database.insert(
                TABLE_ORDER_CART,
                null,
                cv);
    }

    public long removeOrderCart(ShoppingList shoppingList) {
        return database.delete(
                TABLE_ORDER_CART,
                StringAssistant.getString(COL_ID_SHOPPING_LIST_ORDER_CART, "=", String.valueOf(shoppingList.getId())),
                null);
    }

    public void updateOrderCart(OrderCart orderCart) {
        ContentValues cv = new ContentValues();
        cv.put(COL_COUNT_ORDER_CART, orderCart.getCount());
        cv.put(COL_PRICE_ORDER_CART, orderCart.getPrice());
        cv.put(COL_MUST_BAY_ORDER_CART, orderCart.getMustBay());
        cv.put(COL_COMMENT_ORDER_CART, orderCart.getComment());
        database.update(
                TABLE_ORDER_CART,
                cv,
                StringAssistant.getString(ID, "=", String.valueOf(orderCart.getId())),
                null);
    }

    public void updateOrderCartStatus(OrderCart orderCart) {
        ContentValues cv = new ContentValues();
        cv.put(COL_STATUS_ORDER_CART, orderCart.getStatus());
        database.update(
                TABLE_ORDER_CART,
                cv,
                StringAssistant.getString(ID, "=", String.valueOf(orderCart.getId())),
                null);
    }

    public Cursor getCursorForItemOrderCart(long idShoppingList, int status) {
    return database.rawQuery("SELECT order_cart._id, order_cart.count, order_cart.price, order_cart.status, product.id_category, product.name, category.name AS category_name FROM order_cart INNER JOIN product ON order_cart.id_product = product._id INNER JOIN category ON product.id_category = category._id WHERE order_cart.id_shopping_list = " + idShoppingList + " AND order_cart.status = " + status + " ORDER BY product.name", null);
    }

    public Cursor getCursorProduct(String nameProduct) {
        return database.rawQuery("SELECT " + "*" + " FROM " + TABLE_PRODUCT + " WHERE " + COL_NAME_PRODUCT + " = " + nameProduct, null);
    }

    public long createProduct(Product product) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME_PRODUCT, product.getName());
        cv.put(COL_UNIT_PRODUCT, "шт");
        cv.put(COL_ID_CATEGORY_PRODUCT, product.getIdCategory());
        cv.put(COL_PRICE_PRODUCT, product.getPrice());
        return database.insert(
                TABLE_PRODUCT,
                null,
                cv);
    }

//    public Cursor getCursorCategory() {
//        return database.query(
//                TABLE_CATEGORY,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null);
//    }

    public Cursor getCursorCategory() {
        return database.rawQuery("SELECT * FROM category", null);
    }

    public Cursor getCursorForOrderCartDialogFragmentAllFields(long idOrderCart) {
        return database.rawQuery("SELECT order_cart.count, order_cart.price, order_cart.comment, order_cart.must_bay, order_cart._id, order_cart.id_product, product.name, product.id_category, product._id AS tab_product_id, category._id AS tab_category_id FROM order_cart INNER JOIN product ON order_cart.id_product = tab_product_id INNER JOIN category ON product.id_category = tab_category_id WHERE order_cart._id = " + idOrderCart, null);
    }

    public Cursor getCursorForOrderCartDialogFragmentGategoryField(String nameProduct) {
        return database.rawQuery("SELECT product.name, product.id_category, category._id, category.name FROM product INNER JOIN category ON product.id_category = category._id WHERE product.name = '" + nameProduct + "'", null);
    }

    public boolean getProductExists(String productName) {
        return database.rawQuery("SELECT * FROM product WHERE name = '" + productName + "'", null).getCount() == 1;
    }

    public void updateProduct(Product product) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME_PRODUCT, product.getName());
        cv.put(COL_UNIT_PRODUCT, "шт");
        cv.put(COL_ID_CATEGORY_PRODUCT, product.getIdCategory());
        cv.put(COL_PRICE_PRODUCT, product.getPrice());
        database.update(
                TABLE_PRODUCT,
                cv,
                StringAssistant.getString(ID, "=", String.valueOf(product.getId())),
                null);
    }

    public long getProductId(String productName) {
        Cursor cursor = database.rawQuery("SELECT * FROM product WHERE name = '" + productName + "'", null);
        cursor.moveToFirst();
        return cursor.getLong(cursor.getColumnIndex(DBConstants.ID));
    }

    public long createCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME_CATEGORY, category.getName());
        cv.put(COL_COLOR_CATEGORY, category.getColor());
        return database.insert(
                TABLE_CATEGORY,
                null,
                cv);
    }

    public Cursor getCursorProduct() {
        return database.query(
                TABLE_PRODUCT,
                null,
                null,
                null,
                null,
                null,
                StringAssistant.getString(COL_NAME_PRODUCT," ASC"));
    }

    public boolean getCategoryExists(String categoryName) {
        return database.rawQuery("SELECT * FROM category WHERE name = '" + categoryName + "'", null).getCount() == 1;
    }

    public long getCategoryId(String categoryName) {
        Cursor cursor = database.rawQuery("SELECT * FROM category WHERE name = '" + categoryName + "'", null);
        cursor.moveToFirst();
        return cursor.getLong(cursor.getColumnIndex(DBConstants.ID));
    }

    public Cursor getCursorForItemProduct(String constraint) {
        return database.rawQuery("SELECT " + "*" + " FROM " + TABLE_PRODUCT + " WHERE " + COL_NAME_PRODUCT + " LIKE '" + "%" + constraint + "%' ;", null);
    }

}
