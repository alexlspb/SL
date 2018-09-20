package com.example.alexl.sl.bean;

/**
 * Объект список покупок
 */

public class ShoppingList extends ItemObject{

    private String mDateCreate;

    public ShoppingList(int id) {
        this.id = id;
    }

    public ShoppingList(String name, String dateCreate) {
        this.name = name;
        this.mDateCreate = dateCreate;
    }

    public ShoppingList(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getDateCreated() {
        return mDateCreate;
    }

    public void setDateCreated(String dateCreate) {
        this.mDateCreate = dateCreate;
    }
}
