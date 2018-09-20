package com.example.alexl.sl.thread;

import android.database.Cursor;
import android.os.AsyncTask;

import com.example.alexl.sl.adapter.ProductCursorAdapter;
import com.example.alexl.sl.bean.OrderCart;
import com.example.alexl.sl.constants.DBConstants;
import com.example.alexl.sl.constants.OperationsConstants;
import com.example.alexl.sl.db.DBModel;
import com.example.alexl.sl.dialog.OrderCartDialogFragment;

/**
 * Класс получет курсор OrderCard по его id и передает его значения в диалог
 */

public class FillDialogOrderCart extends AsyncTask<Object, Void, Void> {

    private OrderCartDialogFragment dialog = null;
    private OrderCart orderCart = null;
    private int operation;

    public FillDialogOrderCart(OrderCartDialogFragment orderCartDialogFragment) {
        this.dialog = orderCartDialogFragment;
    }

    @Override
    protected Void doInBackground(Object... params) {

        operation = (int) params[0];
        Cursor cursor;
        long idCategory;
        String nameOrderCart;

        switch (operation) {
            case OperationsConstants.EXIST:
                long idOrderCard = (long) params[1];
                cursor = DBModel.openDatabase(dialog.getActivity()).getCursorForOrderCartDialogFragmentAllFields(idOrderCard);
                cursor.moveToFirst();
                nameOrderCart = cursor.getString(cursor.getColumnIndex(DBConstants.COL_NAME_PRODUCT));
                int countOrderCart = cursor.getInt(cursor.getColumnIndex(DBConstants.COL_COUNT_ORDER_CART));
                int priceOrderCart = cursor.getInt(cursor.getColumnIndex(DBConstants.COL_PRICE_ORDER_CART));
                String commentOrderCart = cursor.getString(cursor.getColumnIndex(DBConstants.COL_COMMENT_ORDER_CART));
                int mustBayOrderCart = cursor.getInt(cursor.getColumnIndex(DBConstants.COL_MUST_BAY_ORDER_CART));
                idCategory = cursor.getLong(cursor.getColumnIndex(DBConstants.COL_ID_CATEGORY_PRODUCT));
                cursor.close();
                orderCart = new OrderCart(nameOrderCart, countOrderCart, priceOrderCart, commentOrderCart, mustBayOrderCart, idCategory);
                break;
            case OperationsConstants.SELECTED_WORD:
                String nameProduct = (String) params[1];
                cursor = DBModel.openDatabase(dialog.getActivity()).getCursorForOrderCartDialogFragmentGategoryField(nameProduct);
                cursor.moveToFirst();
                idCategory = cursor.getLong(cursor.getColumnIndex(DBConstants.COL_ID_CATEGORY_PRODUCT));
                cursor.close();
                orderCart = new OrderCart(idCategory);
                break;
            case OperationsConstants.NEW:
                break;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        Cursor productsCursor = DBModel.openDatabase(dialog.getActivity()).getCursorProduct();
        ProductCursorAdapter adapterProducts = new ProductCursorAdapter(dialog.getActivity(), productsCursor, 0);
        dialog.updateAdapterProductName(adapterProducts);

        if (orderCart != null) {
            dialog.updateFields(operation, orderCart);
        }
    }
}

