package com.example.alexl.sl.thread;

import android.content.Context;

import com.example.alexl.sl.OrderCartActivity;
import com.example.alexl.sl.bean.Category;
import com.example.alexl.sl.bean.OrderCart;
import com.example.alexl.sl.bean.Product;
import com.example.alexl.sl.constants.OperationsConstants;
import com.example.alexl.sl.db.DBModel;


/**
 * Класс для работы с базой данных в отдельном потоке.
 * Работает с объектом OrderCart
 */

public class OperationsWithOrderCartImpl extends CallbackWithList implements IOperations {

    private OrderCart orderCart = null;
    private Product product = null;
    private Category category = null;
    private boolean allBean = false;

    public OperationsWithOrderCartImpl(Context context) {
        super(context);
    }

    @Override
    protected Void doInBackground(Object... params) {

        allBean = params.length == 4;

        //принимает значение операции, которую необходимо сделать с обьектами в базе данных
        int operation = (int) params[0];
        orderCart = (OrderCart) params[1];

        if (allBean) {
            product = (Product) params[2];
            category = (Category) params[3];
        }

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
        FillAndReceiveData();
        DBModel.openDatabase(context).createOrderCart(orderCart);
    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {
        if (allBean) {
            FillAndReceiveData();
            DBModel.openDatabase(context).updateOrderCart(orderCart);
        } else {
            DBModel.openDatabase(context).updateOrderCartStatus(orderCart);
        }

    }

    private void FillAndReceiveData() {
        boolean categoryExists = DBModel.openDatabase(context).getCategoryExists(category.getName());
        long idCategory = (categoryExists ? DBModel.openDatabase(context).getCategoryId(category.getName()) : DBModel.openDatabase(context).createCategory(category));
        product.setIdCategory(idCategory);

        boolean productExists = DBModel.openDatabase(context).getProductExists(product.getName());
        long idProduct = (productExists ? DBModel.openDatabase(context).getProductId(product.getName()) :  DBModel.openDatabase(context).createProduct(product));
        product.setId(idProduct);
        DBModel.openDatabase(context).updateProduct(product);

        orderCart.setIdProduct(idProduct);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        DBModel.closeDatabase();
        callback.updateListView(OrderCartActivity.LOADER_ORDER_CART);
    }
}
