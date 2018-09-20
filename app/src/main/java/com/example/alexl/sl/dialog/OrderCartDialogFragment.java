package com.example.alexl.sl.dialog;

import android.app.Dialog;
import android.app.DialogFragment;

import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alexl.sl.OrderCartActivity;
import com.example.alexl.sl.R;
import com.example.alexl.sl.assistans.StringAssistant;
import com.example.alexl.sl.bean.Category;
import com.example.alexl.sl.bean.OrderCart;
import com.example.alexl.sl.bean.Product;
import com.example.alexl.sl.bean.ShoppingList;
import com.example.alexl.sl.constants.OperationsConstants;
import com.example.alexl.sl.thread.FillDialogOrderCart;
import com.example.alexl.sl.thread.OperationsWithOrderCartImpl;


/**
 * Диалог добавляет продукт в список
 * Этот диалог необходимо вызывать только из активити OrderCart
 */

public class OrderCartDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final int ADD_CATEGORY = 0x00000101;
    private ShoppingList shoppingList;
    private OrderCart orderCart;
    private OrderCartActivity orderCartActivity;
    private View mDialog;
    private AutoCompleteTextView etName;
    private EditText etCount, etPrice, etComment, etCategory;
    private CheckBox chbMustBay;
    public Spinner spUnit, spCategory;
    private int mOperations;
    private boolean newCategory = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initArgs();
        return getViewDialog().create();
    }

    private void initArgs() {
        this.orderCartActivity = (OrderCartActivity) getActivity();
        shoppingList = orderCartActivity.getShoppingList();
        mOperations = getArguments().getInt(OperationsConstants.DIALOG_OPERATION);
        switch (mOperations) {
            case OperationsConstants.CREATE:
                orderCart = new OrderCart();
                break;
            case OperationsConstants.UPDATE:
                orderCart = (OrderCart) getArguments().getSerializable(OperationsConstants.BEAN_ORDER_CART);
                break;
        }
    }

    private AlertDialog.Builder getViewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        initUI();
        initAdapterForSpCategory();
        builder.setView(mDialog);
        return builder;
    }

    private void initUI() {
        ViewGroup parent = (ViewGroup) getView();
        mDialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_order_cart, parent, false);

        Button btnCreate = (Button) mDialog.findViewById(R.id.dialog_add_order_cart_btn_create);
        btnCreate.setOnClickListener(this);
        Button btnCancel = (Button) mDialog.findViewById(R.id.dialog_add_order_cart_btn_cancel);
        btnCancel.setOnClickListener(this);

        this.etName = (AutoCompleteTextView) mDialog.findViewById(R.id.dialog_add_order_cart_et_name);
        this.etCount = (EditText) mDialog.findViewById(R.id.dialog_add_order_cart_et_count);
        this.etPrice = (EditText) mDialog.findViewById(R.id.dialog_add_order_cart_et_price);
        this.chbMustBay = (CheckBox) mDialog.findViewById(R.id.dialog_add_order_cart_chb_must_bay);
        this.etComment = (EditText) mDialog.findViewById(R.id.dialog_add_order_cart_et_comment);
        this.spCategory = (Spinner) mDialog.findViewById(R.id.dialog_add_order_cart_spinner_category);
        this.etCategory = (EditText) mDialog.findViewById(R.id.dialog_add_order_cart_et_category);
    }

    private void initAdapterForSpCategory() {
        this.spCategory.setAdapter(orderCartActivity.getCategoriesAdapter());
        orderCartActivity.getSupportLoaderManager().initLoader(OrderCartActivity.LOADER_CATEGORIES, null, orderCartActivity).forceLoad();

        this.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getItemAtPosition(position);
                if (category.getId() == ADD_CATEGORY) {
                    spCategory.setVisibility(View.GONE);
                    etCategory.setVisibility(View.VISIBLE);
                    etCategory.requestFocus();
                    newCategory = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //дописать лог
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (mOperations) {
            case OperationsConstants.CREATE:
                new FillDialogOrderCart(this).execute(OperationsConstants.NEW);
                break;
            case OperationsConstants.UPDATE:
                new FillDialogOrderCart(this).execute(OperationsConstants.EXIST, orderCart.getId());
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_add_order_cart_btn_cancel) {
            dismiss();
            Toast.makeText(getActivity(), R.string.product_not_create, Toast.LENGTH_SHORT).show();
        }

        if (v.getId() == R.id.dialog_add_order_cart_btn_create) {

            //создаем и заполняем из формы объект product
            Product product = new Product();
            product.setName(StringAssistant.firstUpperCase(StringAssistant.getStringOfEditText(etName)));
            product.setPrice(StringAssistant.getIntegerOfEditText(etPrice));

            //заполняем объект orderCart
            orderCart.setCount(StringAssistant.getIntegerOfEditText(etCount));
            orderCart.setPrice(StringAssistant.getIntegerOfEditText(etPrice));
            orderCart.setMustBay(chbMustBay.isChecked() ? 1 : 0);
            orderCart.setIdShoppingList(this.shoppingList.getId());
            orderCart.setStatus(0);
            orderCart.setComment(etComment.getText().toString());

            //создаем и заполняем объект category
            Category category = new Category();
            if (newCategory) {
                category.setName(StringAssistant.getStringOfEditText(etCategory));
            } else {

                Category selectedCategory = (Category) spCategory.getSelectedItem();
                String nameCategory = selectedCategory.getName();
                category.setName(nameCategory);
            }

            if (mOperations == OperationsConstants.CREATE) {
                new OperationsWithOrderCartImpl(getActivity()).execute(OperationsConstants.CREATE, orderCart, product, category);
            }
            if (mOperations == OperationsConstants.UPDATE) {
                new OperationsWithOrderCartImpl(getActivity()).execute(OperationsConstants.UPDATE, orderCart, product, category);
            }
            this.dismiss();
        }
    }

    public void updateFields(int operation, OrderCart orderCart) {
        if (operation == OperationsConstants.EXIST) {
            etName.setText(orderCart.getName());
            etName.dismissDropDown();
            etCount.setText(String.valueOf(orderCart.getCount()));
            etPrice.setText(String.valueOf(orderCart.getPrice()));
            etComment.setText(orderCart.getComment());
            chbMustBay.setChecked(orderCart.getMustBay() == 1);
        }

        int countCategories = spCategory.getCount();
        for (int i = 0; i < countCategories; i++) {
            Category category = (Category) spCategory.getItemAtPosition(i);
            long id = category.getId();
            if (id == orderCart.getIdCategory()) {
                spCategory.setSelection(i);
                return;
            }
        }
    }

    public void updateAdapterProductName(CursorAdapter adapter) {
        etName.setAdapter(adapter);
        etName.setThreshold(3);
        etName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new FillDialogOrderCart(OrderCartDialogFragment.this).execute(OperationsConstants.SELECTED_WORD, etName.getText().toString());
            }
        });
    }

}