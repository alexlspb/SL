package com.example.alexl.sl.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexl.sl.OrderCartActivity;
import com.example.alexl.sl.R;
import com.example.alexl.sl.bean.ShoppingList;
import com.example.alexl.sl.constants.DBConstants;
import com.example.alexl.sl.constants.OperationsConstants;
import com.example.alexl.sl.dialog.ShoppingListDialogFragment;

/**
 * Адаптер для отображения списка покупок
 */

public class ShoppingListCursorAdapter extends CursorAdapter {

    public static final int FLAG_TO_DO_NOTHING = 0;
    private android.app.FragmentManager fm;
    private LayoutInflater cursorInflater;

    public ShoppingListCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.fm = ((Activity) context).getFragmentManager();
        this.cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.item_shopping_list, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        //Ищем элементы на view
        TextView tvShoppingListName = (TextView) view.findViewById(R.id.tvNameList);
        TextView tvShoppingListDateCreate = (TextView) view.findViewById(R.id.tvDateCreated);

        //Получаем значчения из курсора
        final String shoppingListName = cursor.getString(cursor.getColumnIndex(DBConstants.COL_NAME_SHOPPING_LIST));
        String shoppingListDateCreate = cursor.getString(cursor.getColumnIndex(DBConstants.COL_DATE_CREATED_SHOPPING_LIST));
        final int id = cursor.getInt(cursor.getColumnIndex(DBConstants.ID));

        //Передаем значения во view
        tvShoppingListName.setText(shoppingListName);
        tvShoppingListDateCreate.setText(shoppingListDateCreate);

        //вешаем листенеры на view
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ShoppingListDialogFragment dialog = new ShoppingListDialogFragment();
                Bundle args = new Bundle();
                args.putInt(OperationsConstants.DIALOG_OPERATION, OperationsConstants.WHAT_TO_DO);
                args.putSerializable(OperationsConstants.BEAN_SHOPPING_LIST, new ShoppingList(shoppingListName, id));
                dialog.setArguments(args);
                dialog.show(fm, null);
                return true;
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderCartActivity.class);
                intent.putExtra(OperationsConstants.BEAN_SHOPPING_LIST, new ShoppingList(id));
                context.startActivity(intent);
            }
        });
    }
}
