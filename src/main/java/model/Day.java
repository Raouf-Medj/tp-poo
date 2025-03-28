package model;

import model.exceptions.BeyondDeadlineException;
import model.exceptions.NotFitInDayExeception;
import model.exceptions.NotFitInZoneException;

import java.io.Serializable;
import java.time.Duration;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Day implements Comparable<Day>, Serializable {
    private static LocalTime[] timeInterval = {LocalTime.of(0, 0), LocalTime.of(23, 59)};
    private TreeSet<FreeZone> zones = new TreeSet<>(); // insertion et suppression de zones
    private DayOfWeek dayOfWeek;
    private LocalDate date;
    private boolean goalAchieved = false;

    public void setGoalAchieved(boolean goalAchieved) {
        this.goalAchieved = goalAchieved;
    }

    public boolean isGoalAchieved() {
        return goalAchieved;
    }

    public Day(LocalDate date) {
        dayOfWeek = date.getDayOfWeek();
        this.date = date;
    }


//    List<FreeZone> list = new ArrayList<>(zones);
//    ListIterator<FreeZone> i = list.listIterator();
//        i.previous();
//  ----------------------------------------------------------------------------------------------------------------------------------------
//    public void fuseFreeZones() { // methode soudeur
//        Iterator<FreeZone> i = zones.iterator();
//        TreeSet<FreeZone> temp = new TreeSet<>();
//        boolean stop = false;
//
//        if (i.hasNext()) {
//            FreeZone z = i.next();
//            if (i.hasNext()) {
//                FreeZone y = i.next();
//                while (!stop) {
//                    if (z instanceof FreeZone) {
//                        System.out.println(z.getEndTime().equals(y.getStartTime()));
//                        System.out.println("z: "+z.getStartTime()+", "+z.getEndTime()+" || y: "+y.getStartTime()+", "+y.getEndTime());
//                        if (y instanceof FreeZone && z.getEndTime().equals(y.getStartTime())) {
//                            FreeZone x = y;
//                            if (i.hasNext()) {
//                                y = i.next();
//                            }
//                            else stop = true;
//                            LocalTime start = z.getStartTime();
//                            LocalTime end = x.getEndTime();
////                            zones.remove(z);
////                            zones.remove(x);
//                            z = new FreeZone(start, end);
//                            //zones.add(z);
//
//                        }
//                        else {
//                            z = y;
//                            System.out.println("hasNext(): "+i.hasNext());
//                            if (i.hasNext()) {
//                                y = i.next();
//                            }
//                            else stop = true;
//                        }
//                    }
//                    temp.add(z);
//                }
//
//                zones = temp;
//                showDay();
//                System.out.println("----------");
//            }
//        }
//    }

    public void fuseZones() { // methode soudeur des créneaux libres
        List<FreeZone> list = new ArrayList<>(zones);
        List<FreeZone> temp = new ArrayList<>();
        Collections.sort(list);
        ListIterator<FreeZone> i = list.listIterator();
        boolean stop = false;

        while (i.hasNext()) {
            FreeZone z = i.next();
            if (!(z instanceof OccupiedZone) && i.hasNext()) {
                FreeZone y = i.next();
                while (!(y instanceof OccupiedZone) && y !=  null) {
                    if (z.getEndTime().equals(y.getStartTime())) {
                        if (temp.contains(z)) temp.remove(z);
                        FreeZone toAdd = new FreeZone(z.getStartTime(), y.getEndTime());
                        temp.add(toAdd);
                        z = toAdd;
                    }
                    else {
                        if (isInsertable(temp, z)) {
                            temp.add(z);
                        }
                        z = y;
                    }
                    if (i.hasNext()) {
                        y = i.next();
                    }
                    else {
                        if (isInsertable(temp, y)) {
                            temp.add(y);
                        }
                        y = null;
                    }
                }
                if (y !=  null) {
                    if (isInsertable(temp, z)) {
                        temp.add(z);
                    }
                    if (isInsertable(temp, y)) {
                        temp.add(y);
                    }
                }
            }
            else {
                if (isInsertable(temp, z)) {
                    temp.add(z);
                }
            }
        }

        TreeSet<FreeZone> updated = new TreeSet<>(temp);
        zones.clear();
        zones.addAll(updated);

    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return Objects.equals(date, day.date);
    }
    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public int compareTo(Day o) {
        return date.compareTo(o.date);
    }
    public void insertZone(FreeZone zone) throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{
        LocalTime start = zone.getStartTime();
        LocalTime end = zone.getEndTime();

        if (start.isAfter(end)) System.out.println("ERREUR: Debut apres Fin?");
        else {
            boolean stop = false;
            FreeZone z;
            Iterator<FreeZone> iterator = zones.iterator();
            while (iterator.hasNext() && !stop) {
                z = iterator.next();
                if ((start.isAfter(z.getStartTime()) && start.isBefore(z.getEndTime())) || (end.isAfter(z.getStartTime()) && end.isBefore(z.getEndTime())) || (end.isAfter(z.getEndTime()) && start.isBefore(z.getStartTime()))) {
                    throw new NotFitInDayExeception();
                    //stop = true;
                }
            }
            if (!stop) {

                zones.add(zone);

            }
            else System.out.println("ERREUR: insertion impossible!");
        }
        fuseZones();

    }
    public void removeZone(FreeZone zone) {
        LocalTime start = zone.getStartTime();
        LocalTime end = zone.getEndTime();

        if (!zones.contains(zone)) System.out.println("ERREUR: Zone inexistante!");
        else {
            zones.remove(zone);
        }
    }

    public void showDay() {
        for (FreeZone z : zones) {
            //System.out.println("->  "+z.getStartTime()+" - "+z.getEndTime());
            z.showZone();
            if(z instanceof OccupiedZone){
                if (((OccupiedZone) z).getTask() !=  null) System.out.println(((OccupiedZone) z).getTask().getName());
                if (((OccupiedZone) z).getTask() !=  null) System.out.println(((OccupiedZone) z).getTask().getDuration().toString());
            }

        }
    }


    public static boolean isInsertable(Iterable<FreeZone> c, FreeZone toInsert){
        LocalTime start = toInsert.getStartTime();
        LocalTime end = toInsert.getEndTime();
        boolean stop = false;
        FreeZone z;
        Iterator<FreeZone> iterator = c.iterator();
        while (iterator.hasNext() && !stop) {
            z = iterator.next();
            if ((start.isAfter(z.getStartTime()) && start.isBefore(z.getEndTime())) || (end.isAfter(z.getStartTime()) && end.isBefore(z.getEndTime())) || (end.isAfter(z.getEndTime()) && start.isBefore(z.getStartTime()))) {
                stop = true;
            }
        }
        return !stop;
    }


    public int getZonesNumber(){
        return zones.size();
    }
    public TreeSet<FreeZone> getZones(){
        return zones;
    }
    public boolean contains(FreeZone zone){
        return zones.contains(zone);
    }

    public boolean unAppendTask(Task task,FreeZone zone)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{

        FreeZone newZone;

        if(zone!=null){

            if(contains(zone) && (zone instanceof OccupiedZone)){

                if(((OccupiedZone) zone).contains(task)){
                    newZone = new FreeZone(zone.getStartTime(),zone.getEndTime());
                    newZone.showZone();
                    removeZone(zone);
                    System.out.println("it should get removed");
                    insertZone(newZone);
                    if(task instanceof SimpleTask){
                        ((SimpleTask) task).unAppendZone();
                    }
                    else{

                        ((ComplexTask)task).unAppend(zone);

                    }
                    task.setState(State.NOT_REALIZED);
                    return true;
                }
            }
        }
        return false;

    }

    // removes a task from a day (the one that we will use)
    // if the task is simple, then it will remove one zone from the day
    // if the task is complex, then it will remove all occurences of task in that day
    public boolean unAppendTask(Task task)throws BeyondDeadlineException, NotFitInDayExeception,NotFitInZoneException{
        if(task instanceof SimpleTask){
            if(!task.getUnscheduled()){
                return unAppendTask(task,((SimpleTask) task).getAssignedZone());
            }

        }
        else if(task instanceof ComplexTask){
            boolean returnValue = false;
            ArrayList<FreeZone> currentZonesInTask=(ArrayList<FreeZone>)((ComplexTask) task).getAssignedZones().clone();
            for (FreeZone zone : currentZonesInTask) {
                if(!((ComplexTask) task).isTotallyFree()){
                    // Note: do not flip the two parameters
                    // because in boolean operations (logical 'or')
                    // if the first operator is true, no need to go
                    // through the other operators
                    // and so it can skip function execution
                    // which can be sometimes usefull
                    returnValue = unAppendTask(task,zone) || returnValue;
                    System.out.println("what ?******************");

                }
            }
            return returnValue;
        }
        return false;
    }

    //For each of the following methods
    //you must check that the given task is unscheduled (because of other treatments with periodic tasks)

    // adds a task where possible in a day
    public boolean appendTask(Task task, Duration minimumZoneLength)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException {
        // must check if unschedule before calling this

        if(task.isInsertable(this)){

            if(task instanceof SimpleTask){
                FreeZone zone=task.getInsertable(this);

                if(zone!=null){

                    removeZone(zone);
                    ArrayList<FreeZone> zones=zone.appendTask((SimpleTask)task,minimumZoneLength);
                    for (FreeZone zn : zones){
                        insertZone(zn);
                    }
                    return true;
                }
                return false;
            }
            else if(task instanceof ComplexTask){
                FreeZone zone=task.getInsertable(this);
                if(zone!=null){
                    removeZone(zone);
                    ArrayList<FreeZone> zones=zone.appendTask((ComplexTask)task,minimumZoneLength);
                    for(FreeZone zn:zones){
                        insertZone(zn);

                    }
                    return true;
                }
                return false;
            }
        }
        return false;

    }

    // adds a task to a day while specifying exactly the insertion time
    public boolean appendTask(Task task,Duration minimumZoneLength ,LocalTime insertionTime)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{
        if(task instanceof SimpleTask){
            FreeZone zone=task.getInsertable(this,insertionTime);
            if(zone!=null) {
                removeZone(zone);
                ArrayList<FreeZone> zones = zone.appendTask((SimpleTask) task, minimumZoneLength, insertionTime);
                for (FreeZone zn : zones) {
                    insertZone(zn);
                }
                return true;
            }
            throw new NotFitInDayExeception();
            //return false;
        }
        else if(task instanceof ComplexTask){
            FreeZone zone=task.getInsertable(this,insertionTime);
            if(zone!=null){
                removeZone(zone);

                ArrayList<FreeZone> zones=zone.appendTask((ComplexTask)task,minimumZoneLength,insertionTime);
                for(FreeZone zn:zones){
                    insertZone(zn);
                }
                return true;
            }
            throw new NotFitInDayExeception();
            //return false;
        }
        return false;
    }


    // adds a task to a day depending on the insertion time "like the previous one" and the duration
    // of the portion of the task you want to insert (if the task was complex, other wise the subtask
    // duration would not have any effect)
    public boolean appendTask(Task task,Duration minimumZoneLength ,LocalTime insertionTime,Duration subTaskDuration)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{
        if(task instanceof SimpleTask){
            FreeZone zone=task.getInsertable(this,insertionTime);
            if(zone!=null){
                removeZone(zone);
                ArrayList<FreeZone> zones=zone.appendTask((SimpleTask)task,minimumZoneLength,insertionTime);
                for (FreeZone zn : zones) {
                    insertZone(zn);
                }
                return true;
            }
            return false;

        }
        else if(task instanceof ComplexTask){
            FreeZone zone=task.getInsertable(this,insertionTime);
            System.out.println("i was here");
            if(zone!=null){
                removeZone(zone);
                ArrayList<FreeZone> zones=zone.appendTask((ComplexTask)task,minimumZoneLength,insertionTime,subTaskDuration);
                for(FreeZone zn:zones){
                    insertZone(zn);
                }
                return true;
            }
            return false;
        }
        return false;
    }


    // adds a task in a day when possible with specifying the duration of the portion inserted
    // this is when the task is complex, other wise that parameter is ignored
    public boolean appendTask(Task task,Duration minimumZoneLength ,Duration subTaskDuration)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{
        if(task instanceof SimpleTask){
            FreeZone zone=task.getInsertable(this);
            if(zone!=null){
                removeZone(zone);
                ArrayList<FreeZone> zones=zone.appendTask((SimpleTask)task,minimumZoneLength);
                for (FreeZone zn : zones) {
                    insertZone(zn);
                }
                return true;
            }
            return false;

        }
        else if(task instanceof ComplexTask){
            FreeZone zone=task.getInsertable(this);
            if(zone!=null){
                removeZone(zone);
                ArrayList<FreeZone> zones=zone.appendTask((ComplexTask)task,minimumZoneLength,subTaskDuration);
                for(FreeZone zn:zones){
                    insertZone(zn);
                }
                return true;
            }
            return false;
        }
        return false;
    }


    // inserting a task in a day in a specified zone (i don't really know the reason why but we may use it who knows)
    public boolean appendTask(Task task,Duration minimumZoneLength,FreeZone zone)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{
        return appendTask(task,minimumZoneLength,zone.getStartTime());
    }

    @Override
    public String toString() {
        return getDate().toString();
    }

    public Duration getDurationLeft(){
        Duration sum=Duration.ZERO;
        for(FreeZone zn: zones){
            if (!(zn instanceof OccupiedZone)){
                sum=sum.plus(zn.getDuration());
            }
        }
        return sum;
    }

    public void updateNumberOfDoneTasks(int numberOfTasks,int numberOfDoneTasks,Day day){
        numberOfDoneTasks=0;
        numberOfTasks=0;
        for(FreeZone zn : day.getZones()){
            if(zn instanceof OccupiedZone){
                if(((OccupiedZone) zn).getTask().getState().equals(State.COMPLETED)){
                    numberOfDoneTasks++;
                    numberOfTasks++;
                }
                else if(!((OccupiedZone) zn).getTask().getState().equals(State.CANCELLED)&&!((OccupiedZone) zn).getTask().getState().equals((State.DELAYED))){
                    numberOfTasks++;
                }
            }
        }
    }

    public void setProgressState(Calendar cal){
        int goal = cal.getNbCompletedToCongratulate();
        int numberOfTasks=0;
        int numberOfDoneTasks=0;
        this.setGoalAchieved(false);
        updateNumberOfDoneTasks(numberOfTasks,numberOfDoneTasks,this);

        if(numberOfDoneTasks>=goal){
            setGoalAchieved(true);

        }
    }
}