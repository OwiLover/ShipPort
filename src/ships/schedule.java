package ships;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class schedule {
//    static ships[] generateSchedule(int shipCount){
//        ships[] schedule = new ships[shipCount];
//        int i =0;
//        while (shipCount>0) {
//            schedule[i] = ships.generateNewShip();
////            schedule[i].getShipInfo();
//            shipCount--;
//            i++;
//        }
//        System.out.println();
//        return schedule;
//    }
    static List<ships> generateSchedule(int shipCount, List<ships> schedule){
        while (shipCount>0) {
            schedule.add(ships.generateNewShip());
            shipCount--;
        }
        System.out.println();
        return schedule;
    }
//    static ships[] shipSwap (ships ship, ships nextShip, ships[] schedule, int i, int j) {
//        ships buffShip=ship;
//        ship = nextShip;
//        nextShip=buffShip;
//        schedule[i]=ship;
//        schedule[j]=nextShip;
//        return schedule;
//    }
    static ships[] addShip(int shipCount, ships[] oldShed, ships newShip) {
        ships[] newSched = new ships[shipCount+1];
        int i = 0;
        while (shipCount>0) {
            newSched[i]=oldShed[i];
            shipCount--;
            i++;
        }
        i++;

        newSched[i]= newShip;
        return newSched;
    }
//
//    static List<ships> sortSchedule (List<ships> schedule) {
//        Collections.sort(schedule, ships.compare);
//        Collections.sort(schedule, ships);
//        return schedule;
//    }
static List<ships> sortSchedule(List<ships> schedule){
    for (int i = 0; i<schedule.size(); i++) {
        ships ship = schedule.get(i);
        for (int j=i+1; j<schedule.size(); j++) {
            ships nextShips = schedule.get(j);
            if (ship.getDate()>nextShips.getDate()) {
                ships buffShip=ship;
                ship = nextShips;
                nextShips=buffShip;
                schedule.set(i, ship);
                schedule.set(j, nextShips);
            }
            else if (ship.getDate()==nextShips.getDate()) {
                if (Integer.parseInt(ship.getHour().trim())>Integer.parseInt(nextShips.getHour().trim())) {
                    ships buffShip = ship;
                    ship = nextShips;
                    nextShips = buffShip;
                    schedule.set(i, ship);
                    schedule.set(j, nextShips);
                } else if (Integer.parseInt(ship.getHour().trim())==Integer.parseInt(nextShips.getHour().trim()) && Integer.parseInt(ship.getMinute())>Integer.parseInt(nextShips.getMinute())) {
                    ships buffShip = ship;
                    ship = nextShips;
                    nextShips = buffShip;
                    schedule.set(i, ship);
                    schedule.set(j, nextShips);
                }
            }
        }
    }
    return schedule;
}
//    static ships[] sortSchedule(ships[] schedule){
//        for (int i = 0; i<schedule.length; i++) {
//            ships ship = schedule[i];
//            for (int j=i+1; j<schedule.length; j++) {
//                ships nextShips = schedule[j];
//                if (ship.getDate()>nextShips.getDate()) {
//                    ships buffShip=ship;
//                    ship = nextShips;
//                    nextShips=buffShip;
//                    schedule[i]=ship;
//                    schedule[j]=nextShips;
//                }
//                else if (ship.getDate()==nextShips.getDate()) {
//                    if (Integer.parseInt(ship.getHour().trim())>Integer.parseInt(nextShips.getHour().trim())) {
//                        ships buffShip = ship;
//                        ship = nextShips;
//                        nextShips = buffShip;
//                        schedule[i] = ship;
//                        schedule[j] = nextShips;
//                    } else if (Integer.parseInt(ship.getHour().trim())==Integer.parseInt(nextShips.getHour().trim()) && Integer.parseInt(ship.getMinute())>Integer.parseInt(nextShips.getMinute())) {
//                        ships buffShip = ship;
//                        ship = nextShips;
//                        nextShips = buffShip;
//                        schedule[i] = ship;
//                        schedule[j] = nextShips;
//                    }
//                }
//            }
//        }
//        return schedule;
//    }
    static List<ships> printSchedule (List<ships> schedule) {
        for (int i =0; i<schedule.size(); i++) {
            schedule.get(i).getShipInfo();
        }
        System.out.println();
        return schedule;
    }
}
