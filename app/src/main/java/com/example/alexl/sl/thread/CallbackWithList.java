package com.example.alexl.sl.thread;

import android.content.Context;
import android.os.AsyncTask;

import com.example.alexl.sl.db.DBModel;

/**
 * Класс необходим для формарования классов потомков, которые вносящих измениения в базу данных.
 * Этот абстрактный класс содержит методы, которые позволяют обновлять адаптер ListView
 */

abstract class CallbackWithList extends AsyncTask<Object, Void, Void> {

    protected Context context;
    protected IUpdateAdapter callback;

    CallbackWithList(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        try {
            callback = (IUpdateAdapter) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement DialogClickListener interface");
        }
    }

    @Override
    protected Void doInBackground(Object... params) {
        return null;
    }

}
