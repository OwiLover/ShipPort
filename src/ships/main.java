package ships;

import org.codehaus.jackson.map.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class main {
        public static void main(String[] args) throws Exception {
                Scanner in = new Scanner(System.in);
                System.out.print("Input a number: ");

                while (!in.hasNextInt()) {
                        System.out.println("Please, enter some positive number");
                        in.next();
                }
                int num = in.nextInt();
//                String[] time = {"9", "8"};
//                ships shiiiip = new ships("Lincoln", "Liauid",22, time, 99);

                List<ships> sched = new ArrayList<ships>();
                schedule.generateSchedule(num, sched);
//                ships[] sched = schedule.generateSchedule(num);
                schedule.sortSchedule(sched);
                schedule.printSchedule(sched);

                anotherShip(sched);

                ObjectMapper objectMapper = new ObjectMapper();
                File scheduleFile = new File ("target/schedule.json");
                try {
                        objectMapper.writeValue(scheduleFile, sched);
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
                        schedule.sortSchedule(sched);
                        schedule.printSchedule(sched);
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

}
