package com.example.alexl.sl.bean;

/**
 * Created by LebedevAA on 29.08.2017.
 */

public class Category extends ItemObject {

    private int color;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
