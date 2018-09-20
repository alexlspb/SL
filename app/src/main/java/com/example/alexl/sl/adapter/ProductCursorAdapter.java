package com.example.alexl.sl.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FilterQueryProvider;
import android.widget.TextView;

import com.example.alexl.sl.R;
import com.example.alexl.sl.constants.DBConstants;
import com.example.alexl.sl.db.DBModel;

/**
 * Адаптер для AutoCompleteTextView
 * отоброжает название продуктов, которое удовлетворяют веденным буквам в поле AutoCompleteTextView
 * Предназначен для облегчения ввода продуктов и автоматического подбора категории продукта и также его последней стоимости
 */

public class ProductCursorAdapter extends CursorAdapter {

    private LayoutInflater inflater;
    private String constraint;

    public ProductCursorAdapter(final Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFilterQueryProvider = new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                if (constraint != null) {
                    ProductCursorAdapter.this.constraint = constraint.toString();
                }
                return DBModel.openDatabase(context).getCursorForItemProduct((constraint != null ? constraint.toString() : null));
            }
        };
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return this.inflater.inflate(R.layout.item_product_name, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvNameProduct = (TextView) view.findViewById(R.id.item_product_tv_name);
        String nameProduct = cursor.getString(cursor.getColumnIndex(DBConstants.COL_NAME_PRODUCT));
        SpannableStringBuilder text = new SpannableStringBuilder(nameProduct);
        ForegroundColorSpan style = new ForegroundColorSpan(Color.rgb(255, 122, 0));

        int start = nameProduct.toLowerCase().lastIndexOf(constraint.toLowerCase());
        int end = start + constraint.length();

        text.setSpan(style, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        tvNameProduct.setText(text);
    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_NAME_PRODUCT));
    }
}
