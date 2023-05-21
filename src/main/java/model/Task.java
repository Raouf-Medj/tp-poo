package model;

import javafx.application.Preloader;
import model.exceptions.BeyondDeadlineException;
import model.exceptions.NotFitInDayExeception;
import model.exceptions.NotFitInZoneException;

import java.io.Serializable;
import java.time.*;

abstract public class Task implements Comparable<Task>, Serializable {


    public Task(String name,Category category,Priority priority,LocalDateTime deadLine,Duration duration){
        this.name=name;
        this.category=category;
        this.priority=priority;
        this.deadLine=deadLine;
        this.duration=duration;
        this.unscheduled = true;
        this.state=State.NOT_REALIZED;
    }

    /**
     * helps interpreting the type of comparison and translating it to an index (see use in compareTo overloading)
     */
    private static String[] criteria = {"deadLine","priority","duration"};
    private String name;
    private Category category;
    private Priority priority;
    private LocalDateTime deadLine;
    private Duration duration;
    private Boolean unscheduled;
    private Project project;
    private State state;

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public Boolean getUnscheduled() {
        return unscheduled;
    }

    public void setUnscheduled(Boolean unscheduled) {
        this.unscheduled = unscheduled;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public Duration durationFromDeadLine(){
        return Duration.between(LocalDateTime.now(),getDeadLine());
    }

    public Duration durationFromDeadLine(LocalDate currentDay){
        return Duration.between(currentDay,getDeadLine() );
    }

    abstract public void appendZone(FreeZone zone);

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    /**
     * Default task comparing method (by deadline)
     * @param o the task to be compared with.
     * @return int : positive value : current is prior to o
     *               negative value : o is prior to current
     *               null value (0) : current is as important as o
     */



    @Override
    public int compareTo(Task o) {
        // if both have got the same deadline (day), we care about priority
        if(deadLine.toLocalDate().compareTo(o.getDeadLine().toLocalDate()) == 0 ){
            return compareTo(o,1);
        }
        // else we care about deadline
        else{
            return -deadLine.toLocalDate().compareTo(o.getDeadLine().toLocalDate());
        }
    }

    /**
     *
     * @param o the task to be compared with
     * @param i the criteria index (refer to the static array 'criteria')
     * @return int : positive value : current is prior to/takes more time than o
     *               negative value : o is prior to/takes more time than current
     *               null value (0) : current is as important as o
     */
    public int compareTo(Task o, int i){
        return switch (i) {
            case 0 -> compareTo(o);
            case 1 -> priority.compareTo(o.getPriority());
            case 2 -> duration.compareTo(o.getDuration());
            // add more cases here when new criteria are added to the static criteria array
            default -> 0;
        };
    }

    // checks whether a task is insertable in a zone
    public boolean isInsertable(FreeZone zone) throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException {
        return isInsertable(zone,zone.getStartTime());
    }


    // must check that is unscheduled first
    // insertion time is within the zone --> Exception : TimeNotInZone
    // the zone is not occupied --> Exception : ZoneIsOccupied
    // the task fits inside the zone (taking into account the insertion time) --> Exception : NotFitInZone
    public boolean isInsertable(FreeZone zone,LocalTime insertionTime)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{
        //must check first that it is unscheduled !
        // verify wether the zone is after the deadline of the task and that the zone is not occupied
        if(zone.contains(insertionTime) && !(zone instanceof OccupiedZone) ){
            // verify if the task fits inside the zone
            if(getDuration().minus(Duration.between(insertionTime,zone.getEndTime())).isNegative() || getDuration().minus(Duration.between(insertionTime,zone.getEndTime())).isZero() ){
                return true;
            }
        }else{
            throw new NotFitInZoneException();
        }
        return false;
    }

    // the day respects the deadline of the task --> Exception : BeyondDeadLine
    // at least one of the zones can accept the task --> Exception : NotFitInDay
    public boolean isInsertable(Day day)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{
        if(getDeadLine().toLocalDate().equals(day.getDate()) || getDeadLine().toLocalDate().isAfter(day.getDate())){
            for(FreeZone zone: day.getZones()){
                System.out.println(zone);
                try{
                    if(isInsertable(zone)){

                        return true;
                    }
                }
                catch(BeyondDeadlineException e){
                    System.out.println(e.getMessage());
                }
                catch (NotFitInZoneException e){
                    System.out.println(e.getMessage());
                }
                catch(NotFitInDayExeception e){
                    System.out.println(e.getMessage());
                }

            }
        }else{
            throw new BeyondDeadlineException();
        }
        return false;
    }

    // checks whether a task is insertable in a day given a specefic zone
    public boolean isInsertable(Day day,FreeZone zone)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{
        return isInsertable(day,zone,zone.getStartTime());
    }


    // The day accepts the task --> Exception : (one of the two mentioned in isInsertable(Day))
    // The zone is within the day --> Exception : ZoneNotInDay
    // The zone accepts the task --> Exception : (one of the mentioned in isInsertable(zone,Day))
    // The zone respects the deadline of the task --> Exception : BeyondDeadLine
    public boolean isInsertable(Day day,FreeZone zone,LocalTime insertionTime)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{
        return day.contains(zone) && isInsertable(day) && isInsertable(zone) && (!getDeadLine().toLocalDate().equals(day.getDate())||(getDeadLine().toLocalTime().isAfter(insertionTime.plus(getDuration())) || getDeadLine().toLocalTime().equals(insertionTime.plus(getDuration()))) );
    }

    // at least one of the zones respects the time --> Exception : TimeNotInZones
    // any other exception should be at ( isInsertable(day,zn,insertionTime) )
    public boolean isInsertable(Day day,LocalTime insertionTime)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{
        for(FreeZone zn: day.getZones()){
            if(zn.contains(insertionTime)){
                return isInsertable(day,zn,insertionTime);
            }
        }
        return false;
    }

    // watch out ! this one can return a null value !
    // returns the first zone that you can insert a task inside (in a given day ofc)
    public FreeZone getInsertable(Day day)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{
        if(getDeadLine().toLocalDate().equals(day.getDate()) || getDeadLine().toLocalDate().isAfter(day.getDate())){
            for(FreeZone zn: day.getZones()){
                try{
                    if(isInsertable(zn)){
                        return zn;
                    }
                }catch (BeyondDeadlineException e){
                    System.out.println(e.getMessage());
                }catch(NotFitInZoneException e){
                    System.out.println(e.getMessage());
                }catch(NotFitInDayExeception e){
                    System.out.println(e.getMessage());
                }

            }
        }else {
            throw new BeyondDeadlineException();
        }
        return null;
    }

    public FreeZone getInsertable(Day day,LocalTime insertionTime)throws BeyondDeadlineException, NotFitInDayExeception, NotFitInZoneException{
        if(getDeadLine().toLocalDate().equals(day.getDate()) || getDeadLine().toLocalDate().isAfter(day.getDate())){
            for(FreeZone zn: day.getZones()){
                try{
                if(isInsertable(zn,insertionTime)){
                    return zn;
                }
                }catch (NotFitInZoneException e){

                }

            }
        }else{
            throw new BeyondDeadlineException();
        }
        return null;
    }
    public boolean equals(Task task){
        return (this.unscheduled==task.getUnscheduled() && this.name==task.getName() && this.deadLine.equals(task.deadLine) && this.duration.minus(task.duration)==Duration.ZERO);
    }

    @Override
    public String toString() {
        return getName();
    }
}


