package com.example.alexl.sl.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.alexl.sl.R;
import com.example.alexl.sl.assistans.StringAssistant;
import com.example.alexl.sl.bean.Category;
import com.example.alexl.sl.bean.ItemObject;
import com.example.alexl.sl.bean.OrderCart;
import com.example.alexl.sl.constants.OperationsConstants;
import com.example.alexl.sl.dialog.OrderCartDialogFragment;
import com.example.alexl.sl.thread.OperationsWithOrderCartImpl;

import java.util.ArrayList;

/**
 * Адаптер для отображения списка товоров добавленных в корзину
 */

public class OrderCartAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ItemObject> categoriesAndOrderCarts;
    private android.app.FragmentManager fm;
    private static final int CHECKED = 1;
    private static final int UNCHECKED = 0;
    private Context context;

    public OrderCartAdapter(Context context, ArrayList<ItemObject> categoriesAndOrderCarts) {
        this.categoriesAndOrderCarts = categoriesAndOrderCarts;
        this.context = context;
        this.fm = ((Activity) context).getFragmentManager();
        inflater = LayoutInflater.from(context);
    }

    public void refreshData(ArrayList<ItemObject> categoriesAndOrderCarts) {
        this.categoriesAndOrderCarts = categoriesAndOrderCarts;
    }

    @Override
    public int getCount() {
        return categoriesAndOrderCarts == null ? 0 : categoriesAndOrderCarts.size();
    }

    @Override
    public Object getItem(int position) {
        return categoriesAndOrderCarts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
            if (getItem(position).getClass() == Category.class) {
                Category category = (Category) getItem(position);
                view = inflater.inflate(R.layout.item_categories_name, null);
                TextView tvNameCategory = (TextView) view.findViewById(R.id.item_categories_tv_name);
                tvNameCategory.setText(category.getName());
            }
            if (getItem(position).getClass() == OrderCart.class) {
                final OrderCart orderCart = (OrderCart) getItem(position);
                view = inflater.inflate(R.layout.item_order_cart, null);
                TextView tvOrderCartName = (TextView) view.findViewById(R.id.item_order_cart_tv_name_product);
                TextView tvOrderCartPrice = (TextView) view.findViewById(R.id.item_order_cart_tv_price_product);
                final CheckBox chbOrderCartStatus = (CheckBox) view.findViewById(R.id.item_order_cart_chb);

                tvOrderCartName.setText(StringAssistant.getStringBuilder(orderCart.getName(), " (", Integer.toString(orderCart.getCount()), ")"));
                tvOrderCartPrice.setText(String.valueOf(orderCart.getPrice()));
                chbOrderCartStatus.setChecked(orderCart.getStatus() == CHECKED);

                chbOrderCartStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (chbOrderCartStatus.isChecked()) {
                            new OperationsWithOrderCartImpl(context).execute(OperationsConstants.UPDATE, new OrderCart(orderCart.getId(), CHECKED));
                        }
                        if (!chbOrderCartStatus.isChecked()) {
                            new OperationsWithOrderCartImpl(context).execute(OperationsConstants.UPDATE, new OrderCart(orderCart.getId(), UNCHECKED));
                        }
                    }
                });
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OrderCartDialogFragment dialog = new OrderCartDialogFragment();
                        Bundle args = new Bundle();
                        args.putInt(OperationsConstants.DIALOG_OPERATION, OperationsConstants.UPDATE);
                        args.putSerializable(OperationsConstants.BEAN_ORDER_CART, orderCart);
                        dialog.setArguments(args);
                        dialog.show(fm, null);
                    }
                });
            }
        return view;
    }
}
