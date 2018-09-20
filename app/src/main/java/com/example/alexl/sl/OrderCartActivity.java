package com.example.alexl.sl;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.example.alexl.sl.adapter.CategoriesAdapter;
import com.example.alexl.sl.adapter.OrderCartAdapter;
import com.example.alexl.sl.bean.ItemObject;
import com.example.alexl.sl.bean.ShoppingList;
import com.example.alexl.sl.constants.OperationsConstants;
import com.example.alexl.sl.db.DBModel;
import com.example.alexl.sl.dialog.OrderCartDialogFragment;
import com.example.alexl.sl.thread.CategoriesLoader;
import com.example.alexl.sl.thread.IUpdateAdapter;
import com.example.alexl.sl.thread.OrderCartLoader;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


/**
 * Активити отображает продукты добавленные в корзину
 */

public class OrderCartActivity extends AbstractBaseActivity implements View.OnClickListener, IUpdateAdapter, LoaderManager.LoaderCallbacks<ArrayList<ItemObject>> {

    public static final int LOADER_ORDER_CART = 1;
    public static final int LOADER_CATEGORIES = 2;
    private ShoppingList shoppingList;
    private CategoriesAdapter categoriesAdapter;
    private OrderCartAdapter orderCartAdapter;
    private ListView listView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_carts);
        initIntentExtra();
        initUI();
        orderCartAdapter = new OrderCartAdapter(this, null);
        categoriesAdapter = new CategoriesAdapter(this, null);
        listView.setAdapter(orderCartAdapter);
        fab.setOnClickListener(this);
        fab.attachToListView(listView);
        getSupportLoaderManager().initLoader(LOADER_ORDER_CART, null, this).forceLoad();
    }

    private void initUI() {
        initToolbarAndNavigationDrawer();
        listView = (ListView) findViewById(R.id.lv_items_products);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initIntentExtra() {
        shoppingList = (ShoppingList) getIntent().getSerializableExtra(OperationsConstants.BEAN_SHOPPING_LIST);
    }

    @Override
    public void onClick(View view) {
        if(view == fab) {
            OrderCartDialogFragment dialog = new OrderCartDialogFragment();
            Bundle args = new Bundle();
            args.putInt(OperationsConstants.DIALOG_OPERATION, OperationsConstants.CREATE);
            dialog.setArguments(args);
            dialog.show(getFragmentManager(), null);
        }
    }

    @Override
    public void updateListView(int idLoader) {
        getSupportLoaderManager().restartLoader(idLoader, null, this).forceLoad();
    }

    @Override
    public Loader<ArrayList<ItemObject>> onCreateLoader(int id, Bundle args) {
        Loader<ArrayList<ItemObject>> loader = null;
        if (id == LOADER_ORDER_CART) {
            loader = new OrderCartLoader(this, this.shoppingList.getId());
        }
        if (id == LOADER_CATEGORIES) {
            loader = new CategoriesLoader(this);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ItemObject>> loader, ArrayList<ItemObject> data) {
        if (loader.getId() == LOADER_ORDER_CART) {
            orderCartAdapter.refreshData(data);
            orderCartAdapter.notifyDataSetChanged();
        }
        if (loader.getId() == LOADER_CATEGORIES) {
            categoriesAdapter.refreshData(data);
            categoriesAdapter.notifyDataSetChanged();
        }
        DBModel.closeDatabase();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ItemObject>> loader) {
        if (loader.getId() == LOADER_ORDER_CART) {
            orderCartAdapter.refreshData(null);
            orderCartAdapter.notifyDataSetChanged();
        }
        if (loader.getId() == LOADER_CATEGORIES) {
            categoriesAdapter.refreshData(null);
            categoriesAdapter.notifyDataSetChanged();
        }
    }

    public ShoppingList getShoppingList() {
        return this.shoppingList;
    }

    public CategoriesAdapter getCategoriesAdapter() {
        return this.categoriesAdapter;
    }

}
