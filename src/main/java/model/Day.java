package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Day implements Comparable<Day> {
    private static LocalTime[] timeInterval = {LocalTime.of(0, 0), LocalTime.of(23, 59)};
    private TreeSet<FreeZone> zones = new TreeSet<>(); // insertion et suppression de zones
    private DayOfWeek dayOfWeek;
    private LocalDate date;

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
    public void insertZone(FreeZone zone) {
        LocalTime start = zone.getStartTime();
        LocalTime end = zone.getEndTime();

        if (start.isAfter(end)) System.out.println("ERREUR: Debut apres Fin?");
        else {
            if (isInsertable(zones, zone)) {
                zones.add(zone);
            }
            else System.out.println("ERREUR: insertion impossible!");
        }

        //fuseZones();
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
            z.showZone();
        }
    }

    static boolean isInsertable(Iterable<FreeZone> c, FreeZone toInsert) {
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
}