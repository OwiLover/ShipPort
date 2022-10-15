package rest.cranes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@SpringBootApplication
public class shipport {
    public static long time = System.currentTimeMillis();
    static void getTime() {
        System.out.println(time);
    }

    public static void main(String[] args) throws IOException {

        SpringApplication.run(shipport.class, args);

        List<ships> ship = null;
        Scanner in = new Scanner(System.in);
        System.out.print("Write name of schedule you want to find: ");
        System.out.println();
        String Check = in.next();
        ship = findJson(Check);
        while (ship == null) {
            System.out.println("Try again");
            System.out.print("Write name of schedule you want to find: ");
            Check = in.next();
            ship = findJson(Check);
        }
//        System.out.println(ship);
        for (int i = 0; i < ship.size(); i++) {
            ship.get(i).getShipInfo();
//                System.out.println(ship.get(i).getDate());
            int random = (int) (Math.random() * 7) - (int) (Math.random() * 7);
            if (Math.abs(random) >= ship.get(i).getDate()) {
                ship.get(i).setDate(1);
            } else if (random + ship.get(i).getDate() > 30) {
                ship.get(i).setDate(30);
            } else {
                ship.get(i).setDate(ship.get(i).getDate() + random);
            }
            ship.get(i).getShipInfo();
        }
        sortSchedule(ship);
        cranes.readsched(ship);
    }

    public static List<ships> findJson(String filename) {
        URI url = null;
        try {
            url = new URI("http://localhost:8081/readJson/" + filename + ".json");
        } catch (
                URISyntaxException e) {
            throw new RuntimeException(e);
        }
        RestTemplate restTemplate = new RestTemplate();
        ships[] shedule = restTemplate.getForObject(url, ships[].class);
        List<ships> ship = null;
//        System.out.println(shedule);
//        System.out.println(sched);
        if (shedule == null) {
            System.out.println("File does not exist");
        } else {
            ship = Arrays.asList(shedule);
        }
        return ship;
    }

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
}

//public
