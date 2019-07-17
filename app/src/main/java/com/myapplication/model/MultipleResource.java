package com.myapplication.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MultipleResource {

    @SerializedName("page")
    public int page;

    @SerializedName("per_page")
    public int per_page;

    @SerializedName("total")
    public int total;

    @SerializedName("total_pages")
    public int total_pages;

    public List<Dataum> data=null;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public class Dataum{

        @SerializedName("id")
        public int id;

        @SerializedName("name")
        public String name;

        @SerializedName("year")
        public int year;

        @SerializedName("color")
        public String color;

        @SerializedName("pantone_value")
        public String pantone_value;
    }
}