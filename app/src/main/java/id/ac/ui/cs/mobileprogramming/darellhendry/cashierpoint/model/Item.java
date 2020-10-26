package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model;

import androidx.annotation.NonNull;

public class Item {
    private String name;
    private String imageUrl;
    private int price;

    public Item(String name,  int price, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
    public Item(){}

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
