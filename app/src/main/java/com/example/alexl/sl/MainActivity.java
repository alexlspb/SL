package com.example.alexl.sl;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.alexl.sl.adapter.ShoppingListCursorAdapter;
import com.example.alexl.sl.constants.OperationsConstants;
import com.example.alexl.sl.db.DBModel;
import com.example.alexl.sl.dialog.ShoppingListDialogFragment;
import com.example.alexl.sl.thread.IUpdateAdapter;
import com.example.alexl.sl.thread.ShoppingListCursorLoader;
import com.melnykov.fab.FloatingActionButton;

public class MainActivity extends AbstractBaseActivity implements View.OnClickListener, IUpdateAdapter, android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_SHOPPING_LIST = 0;
    private FloatingActionButton fab;
    private ListView listView;
    private ShoppingListCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        adapter = new ShoppingListCursorAdapter(
                this,
                null,
                ShoppingListCursorAdapter.FLAG_TO_DO_NOTHING);
        listView.setAdapter(adapter);
        fab.attachToListView(listView);
        fab.setOnClickListener(this);
        getSupportLoaderManager().initLoader(LOADER_SHOPPING_LIST, null, this);
    }

    private void initUI() {
        initToolbarAndNavigationDrawer();
        listView = (ListView) findViewById(R.id.lv_items_bays);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    @Override
    public void onClick(View view) {
        if (view == fab) {
            ShoppingListDialogFragment dialog = new ShoppingListDialogFragment();
            Bundle args = new Bundle();
            args.putInt(OperationsConstants.DIALOG_OPERATION, OperationsConstants.CREATE);
            dialog.setArguments(args);
            dialog.show(getFragmentManager(), null);
        }
    }

    @Override
    public void updateListView(int idLoader) {
        getSupportLoaderManager().restartLoader(idLoader, null, this);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new ShoppingListCursorLoader(this);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
        DBModel.closeDatabase();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

}
