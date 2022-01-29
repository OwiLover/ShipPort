package ships;
import java.util.Comparator;

public class ships {
        private String name;
        private String type;
        private int weight;
        private int date;
        private String[] time;

        public ships() {
         super();
        }
        public ships(String name, String type, int date, String[] time, int weight) {
            this.name = name;
            this.type = type;
            this.weight = weight;
            this.date = date;
            this.time = time;
        }
//        public static final Comparator<ships> compareDate = new Comparator<ships>() {
//            @Override
//            public int compare(ships ship1, ships ship2) {
//                int result;
//                result = Integer.compare(ship1.date, ship2.date);
//                if (result != 0) {
//                    result = Integer.compare(ship1.date, ship2.date);
//                }
//                return Integer.compare(ship1.date, ship2.date);
//            }
//        };
//        public static final Comparator<ships> compareHour = new Comparator<ships>() {
//            @Override
//            public int compare(ships ship1, ships ship2) {
//                return Integer.compare(Integer.parseInt(ship1.getHour()), Integer.parseInt(ship2.getHour()));
//            }
//        };
//        public static final Comparator<ships> compareMinute = new Comparator<ships>() {
//            @Override
//            public int compare(ships ship1, ships ship2) {
//            return Integer.compare(Integer.parseInt(ship1.getMinute()), Integer.parseInt(ship2.getMinute()));
//            }
//        };

        public static ships generateNewShip() {
            String[] type = new String[] { "Dry", "Liquid", "Container" };
            String shipType = type[0 + (int) (Math.random() * type.length)];
            int shipDateDay = 1 + (int) (Math.random() * 30);
            int hour = (int) (Math.random()*24);
            int minute = (int) (Math.random()*59);
            String[] shipDatetime = new String[] {Integer.toString(hour), Integer.toString(minute)};
            int shipWeight = 1 + (int) (Math.random() * 100);
            String[] names = new String[] { "Lincoln", "Avrora", "Alyaska", "Arabella", "Arizona", "Archem", "Beda",
                    "Bonaventur", "Happy Rodjer", "Winlington", "Delphin", "Dmitriy" };
            String shipName = names[0 + (int) (Math.random() * names.length)];
            return new ships(shipName, shipType, shipDateDay, shipDatetime, shipWeight);
        }

        public String getShipInfo() {
            System.out.print("Ship info: ");
            System.out.print("Name: " + this.name+" ");
            System.out.print("Date: " + this.date+ " ");
            if (this.time[1].length()<2)
            System.out.print("Time: " + this.time[0]+":"+ "0"+this.time[1]+" ");
            else  System.out.print("Time: " + this.time[0]+":"+this.time[1]+" ");
            System.out.print("Type: " + this.type+ " ");
            if (this.type!="Container")
            System.out.println("Weight: " + this.weight+ " tons");
            else System.out.println("Weight: " + this.weight+" pieces");
            return "";
        }



//        public String getShip() {
//            String info = this.name.toString()+" "+this.date+" "+this.time[0]+":"+this.time[1]+" "+this.type;
//            return info;
//        }

        public String getName() {
            return this.name;
        }

        public int getDate() {
            return this.date;
        }

        public String getHour() {
            return  this.time[0];
        }
        void setHour(String Hour) {
            this.time[0]=Hour;
        }
        void setMinute(String Minute) {
            this.time[1] = Minute;
        }
        public String getMinute() {
        return  this.time[1];
        }

        public  String getType() {
            return this.type;
        }

        public int getWeight() {
            return this.weight;
        }
}
