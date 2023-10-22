package com.example.kr3demo;

public class ProductData {
    private String name;
    private  String photo;
    private String description;
    private Integer price;
    private String manufacturer;

    private Integer stock;
    public ProductData(String name, String photo, Integer price, String description, String manufacturer, Integer stock) {
        this.name = name;
        this.photo = photo;
        this.price = price;
        this.description = description;
        this.manufacturer = manufacturer;
        this.stock = stock;
    }

    public ProductData(String name, String category, Integer stock, String manufacturer, String description) {
    }


    public String getName() {
        return this.name;
    }
    public String getPhoto() {
        return this.photo;
    }
    public String getDescription() {
        return this.description;
    }
    public String getManufacturer() {
        return this.manufacturer;
    }
    public Integer getCost() {
        return this.price;
    }
    public Integer getStock() {
        return this.stock;
    }



    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
}