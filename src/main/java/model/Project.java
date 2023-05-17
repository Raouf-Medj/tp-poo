package model;

import java.io.Serializable;
import java.util.*;

public class Project implements Serializable {
    private String name;
    private String description;
    private final List<Task> tasks = new ArrayList<>();

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return name;
    }
}
