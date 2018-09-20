package com.example.alexl.sl.bean;

import java.io.Serializable;

/**
 * Родительский объект всех элементов списков
 */

public abstract class ItemObject implements Serializable {

    protected long id;

    protected int status;

    protected String name;

    public long getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }
}
