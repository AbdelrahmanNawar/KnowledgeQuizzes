package com.example.nawar.quizes.model;

public class Category  {
    String name;

    public Category(int imgResId,String name) {
        this.imgResId = imgResId;
        this.name=name;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    int imgResId;
}
