package model;

import java.io.Serializable;

public class Category implements Serializable {
    private String name;
    private String color;
    public Category(String name, String color){
        this.name=name;
        this.color=color;
    }

    @Override
    public String toString() {
        return name;
    }
}
