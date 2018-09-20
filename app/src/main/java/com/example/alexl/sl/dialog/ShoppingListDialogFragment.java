package com.example.alexl.sl.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alexl.sl.R;
import com.example.alexl.sl.bean.ShoppingList;
import com.example.alexl.sl.constants.OperationsConstants;
import com.example.alexl.sl.thread.OperationsWithShoppingListImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Диалог для создание нового списка покупок
 */

public class ShoppingListDialogFragment extends DialogFragment implements View.OnClickListener {

    private int mOperations;
    private ShoppingList shoppingList;
    private static final int DELETE = 0;
    private static final int RENAME = 1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initArgs();
        return getViewDialog().create();
    }

    private void initArgs() {
        mOperations = getArguments().getInt(OperationsConstants.DIALOG_OPERATION);
        if (mOperations != OperationsConstants.CREATE) {
            shoppingList = (ShoppingList) getArguments().getSerializable(OperationsConstants.BEAN_SHOPPING_LIST);
        }
    }

    private AlertDialog.Builder getViewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup parent = (ViewGroup) getView();

        //диалог создания нового списока покупок
        if (mOperations == OperationsConstants.CREATE) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_shopping_list, parent, false);
            Button create = (Button) v.findViewById(R.id.dialog_add_shopping_list_create_btn);
            Button cancel = (Button) v.findViewById(R.id.dialog_add_shopping_list_cancel_btn);
            create.setOnClickListener(this);
            cancel.setOnClickListener(this);
            builder.setView(v);
        }

        //переименовать существующий диалог
        if (mOperations == OperationsConstants.UPDATE) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_rename_shopping_list, parent, false);
            EditText etName = (EditText) v.findViewById(R.id.dialog_rename_shopping_list_name_et);
            etName.setText(shoppingList.getName());
            Button rename = (Button) v.findViewById(R.id.dialog_rename_shopping_list_rename_btn);
            Button cancel = (Button) v.findViewById(R.id.dialog_rename_shopping_list_cancel_btn);
            rename.setOnClickListener(this);
            cancel.setOnClickListener(this);
            builder.setView(v);
        }

        //диалог с пунктами дейстивий для списка покупок
        if (mOperations == OperationsConstants.WHAT_TO_DO) {
            String[] namesItems = getResources().getStringArray(R.array.names_items);
            builder.setItems(namesItems, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DELETE) {
                        new OperationsWithShoppingListImpl(getActivity()).execute(OperationsConstants.DELETE, shoppingList);
                    }
                    if (which == RENAME) {
                        ShoppingListDialogFragment renameDialog = new ShoppingListDialogFragment();
                        Bundle args = new Bundle();
                        args.putInt(OperationsConstants.DIALOG_OPERATION, OperationsConstants.UPDATE);
                        args.putSerializable(OperationsConstants.BEAN_SHOPPING_LIST, shoppingList);
                        renameDialog.setArguments(args);
                        renameDialog.show(getActivity().getFragmentManager(), null);
                    }
                }
            });
        }
        return builder;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dialog_add_shopping_list_create_btn) {
            View dialogView = (View) view.getParent().getParent();
            EditText etName = (EditText) dialogView.findViewById(R.id.dialog_add_shopping_list_name_et);
            String name = etName.getText().toString();
            Locale locale = new Locale("ru");
            String date = new SimpleDateFormat("dd MMM HH:mm", locale).format(new Date());
            new OperationsWithShoppingListImpl(getActivity()).execute(OperationsConstants.CREATE, new ShoppingList(name, date));
            this.dismiss();
        }

        if (view.getId() == R.id.dialog_add_shopping_list_cancel_btn) {
            this.dismiss();
            Toast.makeText(getActivity(), R.string.shopping_list_not_created, Toast.LENGTH_SHORT).show();
        }

        if (view.getId() == R.id.dialog_rename_shopping_list_rename_btn) {
            View dialogView = (View) view.getParent().getParent();
            EditText etName = (EditText) dialogView.findViewById(R.id.dialog_rename_shopping_list_name_et);
            String name = etName.getText().toString();
            shoppingList.setName(name);
            new OperationsWithShoppingListImpl(getActivity()).execute(OperationsConstants.UPDATE, shoppingList);
            this.dismiss();
        }

        if (view.getId() == R.id.dialog_rename_shopping_list_cancel_btn) {
            this.dismiss();
            Toast.makeText(getActivity(), R.string.shopping_list_not_rename, Toast.LENGTH_SHORT).show();
        }
    }
}
