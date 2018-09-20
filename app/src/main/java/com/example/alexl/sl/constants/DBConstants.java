package com.example.alexl.sl.constants;

/**
 * Константы для работы с базой данных
 */

public class DBConstants {

    // Таблицы

    public static final String DATABASE_NAME = "SL.db";

    public static final String TABLE_SHOPPING_LIST = "shopping_list";

    public static final String TABLE_CATEGORY = "category";

    public static final String TABLE_PRODUCT = "product";

    public static final String TABLE_ORDER_CART = "order_cart";

    // Столбцы в таблице shopping_list

    public static final String COL_NAME_SHOPPING_LIST = "name";

    public static final String COL_DATE_CREATED_SHOPPING_LIST = "date_created";

    public static final String COL_STATUS_SHOPPING_LIST = "status";

    // Столбцы в таблице product

    public static final String COL_NAME_PRODUCT = "name";

    public static final String COL_UNIT_PRODUCT = "unit";

    public static final String COL_ID_CATEGORY_PRODUCT = "id_category";

    public static final String COL_PRICE_PRODUCT = "price";

    // Столбцы в таблице order_cart

    public static final String COL_ID_SHOPPING_LIST_ORDER_CART = "id_shopping_list";

    public static final String COL_ID_PRODUCT_ORDER_CART = "id_product";

    public static final String COL_COUNT_ORDER_CART = "count";

    public static final String COL_PRICE_ORDER_CART = "price";

    public static final String COL_MUST_BAY_ORDER_CART = "must_bay";

    public static final String COL_COMMENT_ORDER_CART = "comment";

    public static final String COL_STATUS_ORDER_CART = "status";

    // Столбцы в таблице category

    public static final String COL_NAME_CATEGORY = "name";

    public static final String COL_COLOR_CATEGORY = "color";

    // Столбец ID называется одинакого во всех таблицах

    public static final String ID = "_id";
}
