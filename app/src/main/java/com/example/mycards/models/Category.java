package com.example.mycards.models;

//@Entity(tableName = "category_table")
public class Category {
  //  @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String image;
    int resource;
    public Category(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public Category(int id, String title, int resource) {
        this.id = id;
        this.title = title;
        this.resource = resource;
    }

    public Category(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public int getResource() {
        return resource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
