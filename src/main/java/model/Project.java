package model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Project implements Serializable {
    private String name;
    private String description;
    private final List<Task> tasks = new ArrayList<>();
    private boolean completed = false;

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

    public String getDescription() {
        return description;
    }

    public double getProgress() {
        double progress = 0;

        int cpt = 0;
        for (Task task : tasks) {
            if(task.getState().equals(State.COMPLETED)) {
                cpt++;
            }
        }

        progress = ((double) cpt) / tasks.size();

        return progress;
    }

    public boolean isCompleted() {
        double progress = 0;

        int cpt = 0;
        for (Task task : tasks) {
            if(task.getState().equals(State.COMPLETED)) {
                cpt++;
            }
        }

        progress = ((double) cpt) / tasks.size();
        return progress ==  1;
    }
}
