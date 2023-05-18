package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Exceptions.BeyondDeadlineException;
import model.Exceptions.NotFitInDayExeception;
import model.Exceptions.NotFitInZoneException;
import model.users.User;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class Calendar implements Serializable {
    // each user is bonded with one calendar and vice-versa
    private User user;
    // a calendar should contains all plannings
    private final ArrayList<Planning> plannings = new ArrayList<>();
    private transient ObservableList<Project> projects = FXCollections.observableArrayList();
    private final TreeMap<LocalDate, Day> calendar = new TreeMap<>();
    private transient ObservableList<Task> unscheduled = FXCollections.observableArrayList();
    private Duration minDuration = Duration.ofMinutes(30);
    private int nbCompletedToCongratulate = 3;
    private final List<Badge> badges = new ArrayList<>();

    public List<Badge> getBadges() {
        return badges;
    }

    public int getNbCompletedToCongratulate() {
        return nbCompletedToCongratulate;
    }

    public void setNbCompletedToCongratulate(int nbCompletedToCongratulate) {
        this.nbCompletedToCongratulate = nbCompletedToCongratulate;
    }

    public void setMinDuration(Duration minDuration) {
        this.minDuration = minDuration;
    }

    public Duration getMinDuration() {
        return minDuration;
    }

    public void addDay(Day day) {
        calendar.putIfAbsent(day.getDate(), day);
    }
    public Day getDay(LocalDate date) {
        // can return null if the day doesn't exist in the tree (days)
        return calendar.getOrDefault(date, null);
    }
    public Planning addPlanning(Planning planning) {
        Day day;
        Planning toReturn = null;
        // plannings can override, so there are no restriction to how plannings are added
        if (!planning.getStartDay().isBefore(LocalDate.now()) && !planning.getEndDay().isBefore(planning.getStartDay())) {
            if (!plannings.contains(planning)) {
                for (LocalDate date = planning.getStartDay(); !date.isAfter(planning.getEndDay()); date = date.plusDays(1)) {
                    if (!calendar.containsKey(date)) {
                        day = new Day(date);
                        planning.addDay(day);
                        addDay(day); //  add to the calendar
                    }
                    else planning.addDay(calendar.get(date));
                }
                plannings.add(planning);
            }
            else {
                for (Planning p : plannings) {
                    if (p.equals(planning)) toReturn = p;;
                }
            }
        }
        else {
            System.out.println("ERREUR: Période de planning invalide!");
        }

        return toReturn;
    }
    public void removePlanning(Planning planning) {
        // check whether the planning exists (using it's ID, and then removing it using the predefined method, must have a user validation before ...)
    }
    public void extendPlanning(Planning planning) {
        //check first that the given planning is extendable, if so then call : planning.extendPlanning
    }

    //temp
    public void showCalendar() {
        for (Map.Entry<LocalDate, Day> entry : calendar.entrySet()) {
            System.out.println(entry.getKey() + " ->  " + entry.getValue());
        }
    }

    public ObservableList<Task> getUnscheduled() {
        return unscheduled;
    }

    public ObservableList<Project> getProjects() {
        return projects;
    }

    public ArrayList<Planning> getPlannings() {
        return plannings;
    }

    public ArrayList<Task> fillPlanning(Planning planning, ArrayList<Task> tasks, Duration minimumZoneSize){
        ArrayList<Task> currentListUnscheduledTasks=new ArrayList<Task>(tasks);
        currentListUnscheduledTasks.sort(Collections.reverseOrder());
        Set<Map.Entry<LocalDate, Day> > days = planning.getDays().entrySet();
        Day currentDay;

        boolean dayFilled;

        for (Map.Entry<LocalDate, Day> entry : days) {
            currentDay = getDay(entry.getValue().getDate());
            for(Task tsk : currentListUnscheduledTasks){
                if(tsk.getUnscheduled()){
                    try {
                        currentDay.appendTask(tsk, minimumZoneSize);
                    }catch(BeyondDeadlineException e){

                    }catch(NotFitInZoneException e){

                    }catch(NotFitInDayExeception e){

                    }

                }
                if(currentDay.getDurationLeft().compareTo(minimumZoneSize)<=0){
                    break;
                }
            }
        }

        return currentListUnscheduledTasks;
    }

    //-------------------{ util attributes }----------------------
    private ArrayList<Project> projectsSave = new ArrayList<>();
    private ArrayList<Task> unscheduledSave = new ArrayList<>();

    public void setProjectsSave(ArrayList<Project> projectsSave) {
        this.projectsSave = projectsSave;
    }

    public ArrayList<Project> getProjectsSave() {
        return projectsSave;
    }

    public void setUnscheduledSave(ArrayList<Task> unscheduledSave) {
        this.unscheduledSave = unscheduledSave;
    }

    public ArrayList<Task> getUnscheduledSave() {
        return unscheduledSave;
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        projects = FXCollections.observableArrayList();
        unscheduled = FXCollections.observableArrayList();
    }

}
