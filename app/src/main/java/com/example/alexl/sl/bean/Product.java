package com.example.alexl.sl.bean;

/**
 * Объект товар
 */

public class Product extends ItemObject {

    private String unit;

    private long idCategory;

    private int price;

    public Product() {
    }

    public Product(String name, String unit, long idCategory, int price) {
        this.name = name;
        this.unit = unit;
        this.idCategory = idCategory;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public long getIdCategory() {
        return idCategory;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setIdCategory(long idCategory) {
        this.idCategory = idCategory;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
