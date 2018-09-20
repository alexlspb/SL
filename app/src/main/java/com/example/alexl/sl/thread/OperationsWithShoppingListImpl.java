package com.example.alexl.sl.thread;

import android.content.Context;

import com.example.alexl.sl.MainActivity;
import com.example.alexl.sl.bean.ShoppingList;
import com.example.alexl.sl.constants.OperationsConstants;
import com.example.alexl.sl.db.DBModel;

/**
 * Класс для работы с базойданных в отдельном потоке.
 * Работает с объектом ShoppingList
 */

public class OperationsWithShoppingListImpl extends CallbackWithList implements IOperations {

    private ShoppingList shoppingList = null;

    public OperationsWithShoppingListImpl(Context context) {
        super(context);
    }

    @Override
    protected Void doInBackground(Object... params) {

        int operation = (int) params[0];
        shoppingList = (ShoppingList) params[1];

        switch (operation) {
            case OperationsConstants.CREATE:
                create();
                break;
            case OperationsConstants.UPDATE:
                update();
                break;
            case OperationsConstants.DELETE:
                delete();
                break;
        }
        return null;
    }

    @Override
    public void create() {
        DBModel.openDatabase(context).createShoppingList(shoppingList);
    }

    @Override
    public void delete() {
        DBModel.openDatabase(context).removeOrderCart(shoppingList);
        DBModel.openDatabase(context).removeShoppingList(shoppingList);
    }

    @Override
    public void update() {
        DBModel.openDatabase(context).updateShoppingList(shoppingList);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        DBModel.closeDatabase();
        callback.updateListView(MainActivity.LOADER_SHOPPING_LIST);
    }
}
