package rest.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class main {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(rest.main.main.class, args);
        Scanner in = new Scanner(System.in);

        System.out.print("Input a number: ");
        String Check = in.next();
        while (!isNumericPositive(Check)) {
            Check = in.next();
        }

//        List<ships> sched = new ArrayList<ships>();

        URI url = null;

        try {
            url = new URI("http://localhost:8081/schedule/"+Check);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);



//        String shipList = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        sched = objectMapper.readValue(shipList, new TypeReference<List<ships>>(){});
//        System.out.println(sched);
//        anotherShip(sched);
//        System.out.println(sched);
//        try {
//            url = new URI("http://localhost:8081/makeJson/"+sched);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        response = restTemplate.getForEntity(url, String.class);
    }

    public static void createJson(List<ships> sched) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        File scheduleFile = new File ("target/schedule.json");
        try {
            writer.writeValue(scheduleFile, sched);
            System.out.println("Successfully Copied JSON Object to File...");
        } catch(Exception e){
            System.out.println(e);
        }
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public static boolean isNumericLimited(String str, int Up, int Down)
    {
        double d;
        try
        {
            d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("Please, write a number");
            return false;
        }
        if (d<=Up && d>=Down) {
            return true;
        } else {
            System.out.println("Number should be more than "+Up+" and not below "+ Down);
            return false;
        }
    }

    public static boolean isNumericPositive(String str)
    {
        double d;
        try
        {
            d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("Please, write a number");
            return false;
        }
        if (d>=0) {
            return true;
        } else {
            System.out.println("Number should be positive!");
            return false;
        }
    }

    static List<ships> printSchedule (List<ships> schedule) {
        for (int i =0; i<schedule.size(); i++) {
            schedule.get(i).getShipInfo();
        }
        System.out.println();
        return schedule;
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

    public static void anotherShip (List<ships>sched) {
        Scanner in = new Scanner(System.in);
        System.out.println("Would you like to add new Ships? (Yes/No)");
        String answer = in.next();
        while (!Objects.equals(answer, "Yes") && !Objects.equals(answer, "No")) {
            System.out.println("Please, enter 'Yes' or 'No'");
            answer = in.next();
        }
        if (Objects.equals(answer, "Yes")) {
            ships Alegator = addShip();
            Alegator.getShipInfo();
            sched.add(Alegator);
            sortSchedule(sched);
            System.out.println();
            printSchedule(sched);
            System.out.println();
            anotherShip(sched);
        }
    }
    public static ships addShip () {
        Scanner in = new Scanner(System.in);
        in = new Scanner(System.in);
        System.out.println("Alright, write ship's name:");
        String Check = in.next();
        while (Check.trim()=="") {
            System.out.println("Please, write ship's name");
            Check = in.next();
        }
        String Name = Check;
        System.out.println("What type this ship transports? \nWrite a number (1-Dry, 2-Liquid, 3-Container)");
        Check = in.next();
        while (!Objects.equals(Check, "1") && !Objects.equals(Check, "2") && !Objects.equals(Check, "3")) {
            System.out.println("Please, enter '1' (Dry) or '2' (Liquid) or '3' (Container)");
            Check = in.next();
        }
        String Type;
        if (Objects.equals(Check, "1")) {
            Type="Dry";
        } else if (Objects.equals(Check, "2")) {
            Type="Liquid";
        } else Type="Container";
        System.out.println(Type);
        System.out.println("How much will it weight? \nWrite a number");
        Check = in.next();
        while (!isNumeric(Check)) {
            System.out.println("Please, write a number");
            Check = in.next();
        }
        int Weight = Integer.parseInt(Check);
        System.out.println("In which day "+Name+" should arrive? \nWrite a number (Not higher than 31 and not below 1)");
        Check = in.next();
        while (!isNumericLimited(Check, 31,1)) {
            Check = in.next();
        }
        int Date = Integer.parseInt(Check);
        System.out.println("In what time should "+Name+" arrive? \nFirst write an hour (Not more than 23 and not less than 0)");
        Check = in.next();
        while (!isNumericLimited(Check, 23,0)) {
            Check = in.next();
        }
        int Hour = Integer.parseInt(Check);
        System.out.println("Now write minute (Not more than 59 and not less than 0)");
        Check = in.next();
        while (!isNumericLimited(Check, 59,0)) {
            Check = in.next();
        }
        int Minute = Integer.parseInt(Check);
        String[] Time = new String[] {Integer.toString(Hour), Integer.toString(Minute)};
        return new ships(Name, Type, Date, Time, Weight);
    }

}