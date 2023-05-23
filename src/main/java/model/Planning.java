package model;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collections;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Planning implements Serializable {
    private TreeMap<LocalDate, Day> days = new TreeMap<>();
    private LocalDate startDay;
    private LocalDate endDay;
    private final List<Badge> badges = new ArrayList<>();

    public List<Badge> getBadges() {
        return badges;
    }

    public Planning(LocalDate startDay, LocalDate endDay, Calendar calendar) {
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public void setStartEndDay(LocalDate startDay, LocalDate endDay){
        this.startDay = startDay;
        this.endDay = endDay;
    }
    public LocalDate getStartDay(){
        return startDay;
    }
    public LocalDate getEndDay(){
        return endDay;
    }
    public Day getDay(LocalDate date) {
        // can return null if the day doesn't exist in the tree (days)
        return days.getOrDefault(date, null);
    }
    public void addDay(Day day) {
        days.putIfAbsent(day.getDate(), day);
    }

    // temp
    public void showPlanning() {
        for (Map.Entry<LocalDate, Day> d : days.entrySet()) {
            System.out.println(d.getValue().getDate()+" ->  "+d);
        }
    }

    public TreeMap<LocalDate, Day> getDays() {
        return days;
    }
    @Override
    public String toString() {
        return startDay.toString()+" -> "+endDay.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Planning other = (Planning) obj;

        // Perform null checks for the fields being compared
        if (startDay == null && other.startDay != null) {
            return false;
        }
        if (startDay != null && !startDay.equals(other.startDay)) {
            return false;
        }

        return true;
    }

    public int[] getStats() {
        // 0: nb of completed tasks (cpt1), 1: nb of completed projects (cpt2)
        int cpt1 = 0;
        int cpt2 = 0;
        List<Project> prjts = new ArrayList<>();

        for (Map.Entry<LocalDate, Day> e : days.entrySet()) {
            for (FreeZone zone : e.getValue().getZones()) {
                if (zone instanceof OccupiedZone) {
                    if (((OccupiedZone) zone).getTask().getState().equals(State.COMPLETED)) cpt1++;
                    if (((OccupiedZone) zone).getTask().getProject() !=  null) prjts.add(((OccupiedZone) zone).getTask().getProject());
                }
            }
        }

        for (Project project : prjts) {
            if (project.isCompleted()) cpt2++;
        }

        return new int[]{cpt1, cpt2};
    }
}
