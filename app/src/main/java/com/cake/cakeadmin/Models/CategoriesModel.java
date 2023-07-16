package com.cake.cakeadmin.Models;

public class CategoriesModel {
    String cat;


    public CategoriesModel(){

    }

    public CategoriesModel(String cat) {
        this.cat = cat;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

}
