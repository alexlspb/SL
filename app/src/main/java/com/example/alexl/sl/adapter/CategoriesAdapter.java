package com.example.alexl.sl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alexl.sl.R;
import com.example.alexl.sl.bean.ItemObject;

import java.util.ArrayList;

/**
 * Адаптер формирует список категорий
 */

public class CategoriesAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ItemObject> categories;

    public CategoriesAdapter(Context context, ArrayList<ItemObject> categories) {
        this.categories = categories;
        inflater = LayoutInflater.from(context);
    }

    public void refreshData(ArrayList<ItemObject> categories) {
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories == null ? 0 : categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_categories_name_spinner, parent, false);
        }
        TextView tvNameCategory = (TextView) view.findViewById(R.id.item_categories_spinner_tv_name);
        String nameCategory = categories.get(position).getName();
        tvNameCategory.setText(nameCategory);
        return view;
    }
}
