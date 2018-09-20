package com.example.alexl.sl.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;



/**
 * Created by LebedevAA on 02.11.2017.
 */

public class ColorsAdapter extends BaseAdapter {

    private Context context;
    private static final int[] colors = {Color.BLUE, Color.BLACK, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.BLUE, Color.BLACK, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.BLUE, Color.BLACK, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.BLUE, Color.BLACK, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.YELLOW};

    public ColorsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int position) {
        return colors[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = new DrawView(context, colors[position]);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 45);
            view.setLayoutParams(layoutParams);
        }
        return view;
    }

    private class DrawView extends View {

        private Paint circle;
        private final int color;

        public DrawView(Context context, int color) {
            super(context);
            this.circle = new Paint();
            this.color = color;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            circle.setAntiAlias(true);
            circle.setStyle(Paint.Style.FILL);
            circle.setColor(Color.BLACK);
            canvas.drawRect((getWidth()/2)-20, (getHeight()/2)-20, (getWidth()/2)+20, (getHeight()/2)+20, circle);
            circle.setColor(color);
            canvas.drawRect((getWidth()/2)-19, (getHeight()/2)-19, (getWidth()/2)+19, (getHeight()/2)+19, circle);
        }
    }
}
