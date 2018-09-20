package com.example.alexl.sl.bean;

/**
 * Объект товар в корзине
 */

public class OrderCart extends ItemObject{

    private int count;

    private int price;

    private int mustBay;

    private long idShoppingList;

    private long idProduct;

    private String comment = "";

    private long idCategory;

    public OrderCart() {
    }

    public OrderCart(int id) {
        this.id = id;
    }

    public OrderCart (long idProduct, String name, int count, int price, int status) {
        this.id = idProduct;
        this.name = name;
        this.count = count;
        this.price = price;
        this.status = status;
    }

    public OrderCart(long id, int count, int price, int mustBay, int idProduct, String comment) {
        this.id = id;
        this.count = count;
        this.price = price;
        this.mustBay = mustBay;
        this.idProduct = idProduct;
        this.comment = comment;
    }

    public OrderCart(String name, int count, int price, String comment, int mustBay, long idCategory) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.comment = comment;
        this.mustBay = mustBay;
        this.idCategory = idCategory;
    }

    public OrderCart(long id, int status) {
        this.id = id;
        this.status = status;
    }

    public OrderCart(long idCategory) {
        this.idCategory = idCategory;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    public int getMustBay() {
        return mustBay;
    }

    public long getIdShoppingList() {
        return idShoppingList;
    }

    public int getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public long getIdCategory() {
        return idCategory;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMustBay(int mustBay) {
        this.mustBay = mustBay;
    }

    public void setIdShoppingList(long idShoppingList) {
        this.idShoppingList = idShoppingList;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setIdCategory(long nameCategory) {
        this.idCategory = nameCategory;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }
}


