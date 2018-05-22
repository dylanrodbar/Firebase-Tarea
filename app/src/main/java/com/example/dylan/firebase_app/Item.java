package com.example.dylan.firebase_app;

public class Item {

    private String name;
    private float price;
    private String photo;
    private String description;

    Item(String name, float price, String photo, String description) {
        this.setName(name);
        this.setPrice(price);
        this.setPhoto(photo);
        this.setDescription(description);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
