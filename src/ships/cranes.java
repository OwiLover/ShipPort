package ships;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


public class cranes {
    public static long time = System.currentTimeMillis();

    static void getTime() {
        System.out.println(time);
    }

    static void readsched() throws IOException {

        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON array to list of books
            List<ships> ship = Arrays.asList(mapper.readValue(Paths.get("target/schedule.json").toFile(), ships[].class));

            // print books
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
            schedule.sortSchedule(ship);
            System.out.println(' ');
            System.out.println(' ');
            System.out.println(' ');
            for (int i = 0; i < ship.size(); i++) {
                ship.get(i).getShipInfo();
            }

            ShipPort shipPort = new ShipPort();
            shipPort.setShipsList(ship);
            shipPort.start();
//            Cranes crane = new Cranes();
//            Cranes crane2 = new Cranes();
//            for (int i = 0; i<1000; i++) {
//                int worldTime = i;
//                System.out.println(worldTime);
//                crane.run(ship.get(0), 1);
//                crane2.run(ship.get(0), 2);
//                ship.get(0).getShipInfo();
//            }
//            worldTime worldTime = new worldTime();
//            List<ships> shipsList = new ArrayList<ships>();
//            Cranes crane = new Cranes();
//            crane.getTime();
//            while (worldTime.getDate() <=30) {
//                System.out.println("Current Date: "+ worldTime.getDate());
//                int count = 0;
//                for (int i =0; i<ship.size(); i++) {
//                    if (ship.get(i).getDate() == worldTime.getDate()) {
//                        shipsList.add(ship.get(i));
//                        count++;
//                    }
//                }
//                System.out.println("Today will arrive "+ count + " ships");
//                while (worldTime.getHour() <= 23) {
//                    while (worldTime.getMinute() <= 59) {
////                        System.out.println("   Time: "+worldTime.getHour()+":"+worldTime.getMinute());
//                        for (int i =0; i<shipsList.size(); i++) {
//                            crane.run(shipsList.get(i), crane.getAvailable(), worldTime);
//                            if (shipsList.get(i).getWeight() <=0) {
//                                shipsList.remove(i);
//                            }
//                        }
//                        worldTime.setMinute(worldTime.getMinute()+1);
//                    }
//                    worldTime.setHour(worldTime.getHour()+1);
//                    worldTime.setMinute(0);
//                }
//                worldTime.setDate(worldTime.getDate()+1);
//                worldTime.setHour(0);
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //    public static class worldTime {
//        private int date;
//        private int hour;
//        private int minute;
//
//        public worldTime() {
//            this.date = 1;
//            this.hour = 0;
//            this.minute = 0;
//        }
//
//        public int getDate() {
//            return this.date;
//        }
//
//        public int getHour() {
//            return this.hour;
//        }
//
//        public int getMinute() {
//            return this.minute;
//        }
//
//        void setDate(int Date) {
//            this.date=Date;
//        }
//
//        void setHour(int Hour) {
//            this.hour=Hour;
//        }
//
//        void setMinute(int Minute) {
//            this.minute=Minute;
//        }
//    }
    static class ShipPort extends Thread {
        private volatile List<ships> shipsList;

        public void setShipsList(List<ships> ships) {
            this.shipsList = ships;
        }
        public void start(List<ships> ships) throws InterruptedException {
            long startTime = System.currentTimeMillis();
            int prevDay = 0;

            List<ships> shipsFluid = new ArrayList<ships>();
            List<ships> shipsDry = new ArrayList<ships>();
            List<ships> shipsContainer = new ArrayList<ships>();
            for (int i = 0; i < ships.size(); i++) {
                String type = ships.get(i).getType();
                if (Objects.equals(type, "Liquid")) {
                    shipsFluid.add(ships.get(i));
                } else if (Objects.equals(type, "Dry")) {
                    shipsDry.add(ships.get(i));
                } else shipsContainer.add(ships.get(i));
            }
            System.out.println(" ");

            CraneStation craneStationFluid = new CraneStation();
            CraneStation craneStationDry = new CraneStation();
            CraneStation craneStationContainer = new CraneStation();

            craneStationFluid.setParams(shipsFluid, 5, startTime);
            craneStationDry.setParams(shipsDry, 7, startTime);
            craneStationContainer.setParams(shipsContainer, 10, startTime);

            craneStationFluid.start();
            craneStationContainer.start();
            craneStationDry.start();
//         System.out.println("Dry Ships: ");
//         for (int i = 0; i<shipsDry.size(); i++) {
//             System.out.println(shipsDry.get(i).getShipInfo());
//         }
//
//         System.out.println("Container Ships: ");
//         for (int i = 0; i<shipsContainer.size(); i++) {
//             System.out.println(shipsContainer.get(i).getShipInfo());
//         }
//
//         System.out.println("Fluid Ships: ");
//         for (int i = 0; i<shipsFluid.size(); i++) {
//             System.out.println(shipsFluid.get(i).getShipInfo());
//         }


            while (System.currentTimeMillis() - startTime < 43200) {
                int Day = (int) ((System.currentTimeMillis() - startTime) / 1440) + 1;
                if (Day > prevDay) {
                    System.out.println("Day: " + Day);
                    prevDay = Day;
                }
            }
            Thread.sleep(10000);
            System.out.println(" ");
            System.out.println("Simulation ended...");


            long averageWait = 0;
            for (int i = 0; i<craneStationContainer.arrivedCheck.size(); i++) {
                averageWait += craneStationContainer.arrivedCheck.get(i).longValue();
                craneStationContainer.arrivedCheck.get(i).longValue();
            }



            for (int i = 0; i<craneStationDry.arrivedCheck.size(); i++) {
                averageWait += craneStationDry.arrivedCheck.get(i).longValue();
            }

            for (int i = 0; i<craneStationFluid.arrivedCheck.size(); i++) {
                averageWait += craneStationFluid.arrivedCheck.get(i).longValue();
            }


            long averageDelay = 0;

            for (int i = 0; i<craneStationContainer.delayCheck.size(); i++) {
                averageDelay += craneStationContainer.delayCheck.get(i);
            }

            for (int i = 0; i<craneStationDry.delayCheck.size(); i++) {
                averageDelay += craneStationDry.delayCheck.get(i);
            }

            for (int i = 0; i<craneStationFluid.delayCheck.size(); i++) {
                averageDelay += craneStationFluid.delayCheck.get(i);
            }
            long totalBill = craneStationContainer.totalBill;
            totalBill += craneStationFluid.totalBill;
            totalBill += craneStationDry.totalBill;
            long averageUnloadDelay = 0;

            for (int i = 0; i<craneStationContainer.unloadDelayList.size(); i++) {
                averageUnloadDelay += craneStationContainer.unloadDelayList.get(i);
            }

            for (int i = 0; i<craneStationDry.unloadDelayList.size(); i++) {
                averageUnloadDelay += craneStationDry.unloadDelayList.get(i);
            }

            for (int i = 0; i<craneStationFluid.unloadDelayList.size(); i++) {
                averageUnloadDelay += craneStationFluid.unloadDelayList.get(i);
            }

            if (craneStationContainer.arrivedCheck.size() + craneStationDry.arrivedCheck.size() + craneStationFluid.arrivedCheck.size() !=0) {
                averageWait = averageWait / (craneStationContainer.arrivedCheck.size() + craneStationDry.arrivedCheck.size() + craneStationFluid.arrivedCheck.size());
            }
            if(craneStationContainer.delayCheck.size() + craneStationFluid.delayCheck.size() + craneStationDry.delayCheck.size() != 0) {
                averageDelay = averageDelay / (craneStationContainer.delayCheck.size() + craneStationDry.delayCheck.size() + craneStationFluid.delayCheck.size());
            }
            if (craneStationContainer.unloadDelayList.size() + craneStationFluid.unloadDelayList.size() + craneStationDry.unloadDelayList.size() != 0) {
                averageUnloadDelay = averageUnloadDelay / (craneStationContainer.unloadDelayList.size() + craneStationDry.unloadDelayList.size() + craneStationFluid.unloadDelayList.size());
            }


            long waitMinute = averageDelay%60;
            long waitHour = averageDelay/60;
            long waitDate = 0;
            while (waitHour>=24) {
                waitDate++;
                waitHour -=24;
            }
            System.out.println(" ");
            System.out.println("Total ships unloaded: " + (craneStationContainer.unloadShipCount+craneStationFluid.unloadShipCount+craneStationDry.unloadShipCount));
            System.out.println("Average queue size: " + averageWait);
            System.out.println("Average queue wait time: " + waitDate + " "+ waitHour+":"+waitMinute);

            long waitUnloadMinute = averageUnloadDelay%60;
            long waitUnloadHour = averageUnloadDelay/60;
            long waitUnloadDate = 0;
            while (waitUnloadHour>=24) {
                waitUnloadDate++;
                waitUnloadHour -=24;
            }
            System.out.println("Average unload delay: "+ waitUnloadDate + " "+ waitUnloadHour+":"+ waitUnloadMinute);
            if (craneStationDry.longestUnloadDelay != 0) {
                waitUnloadMinute = craneStationDry.longestUnloadDelay % 60;
                waitUnloadHour = craneStationDry.longestUnloadDelay / 60;
                waitUnloadDate = 0;
                while (waitUnloadHour >= 24) {
                    waitUnloadDate++;
                    waitUnloadHour -= 24;
                }

                System.out.println("Longest unload delay from Dry: " + waitUnloadDate + " " + waitUnloadHour + ":" + waitUnloadMinute);
            }

            if (craneStationFluid.longestUnloadDelay !=0 ) {
                waitUnloadMinute = craneStationFluid.longestUnloadDelay % 60;
                waitUnloadHour = craneStationFluid.longestUnloadDelay / 60;
                waitUnloadDate = 0;
                while (waitUnloadHour >= 24) {
                    waitUnloadDate++;
                    waitUnloadHour -= 24;
                }
                System.out.println("Longest unload delay from Fluid: " + waitUnloadDate + " " + waitUnloadHour + ":" + waitUnloadMinute);
            }

            if (craneStationContainer.longestUnloadDelay !=0) {
                waitUnloadMinute = craneStationContainer.longestUnloadDelay % 60;
                waitUnloadHour = craneStationContainer.longestUnloadDelay / 60;
                waitUnloadDate = 0;
                while (waitUnloadHour >= 24) {
                    waitUnloadDate++;
                    waitUnloadHour -= 24;
                }

                System.out.println("Longest unload delay from Container: " + waitUnloadDate + " " + waitUnloadHour + ":" + waitUnloadMinute);
            }
            System.out.println(" ");
            System.out.println("Total Bill: " + totalBill);
            System.out.println(" ");
            System.out.println("Cranes count:");
            System.out.println("Fluid cranes - " + craneStationFluid.getCraneCount());
            System.out.println("Dry cranes - " + craneStationDry.getCraneCount());
            System.out.println("Container cranes - " + craneStationContainer.getCraneCount());

        }

        @Override
        public void run() {
            try {
                this.start(shipsList);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class CraneStation extends Thread {
        private int craneCount = 1;

        private boolean isNew = false;

        private volatile List<ships> shipsList;

        private List<Integer> arrivedCheck;

        private List<Long> delayCheck;

        private volatile int craneUnload;


        private long startTime;

        private int unloadShipCount;

        private List<Long> unloadDelayList;
        private long longestUnloadDelay;

        private long totalBill;

        public void setParams(List<ships> shipsList, int craneUnload, long startTime) {
            this.shipsList=shipsList;
            this.craneUnload= craneUnload;
            this.startTime = startTime;
            this.unloadShipCount = 0;
            this.arrivedCheck = new ArrayList<>();
            this.delayCheck = new ArrayList<>();
            this.unloadDelayList = new ArrayList<>();
            this.longestUnloadDelay = 0;
            this.totalBill = 0;
        }

        public int getCraneCount() {
            return this.craneCount;
        }

        private void increaseCraneCount() {
            this.craneCount+=1;
        }
        synchronized void craneAwake() {
            notify();
        }

        public void createStation(List<ships> shipsList, int craneUnload, long startTime) throws InterruptedException {
            List<Cranes> cranesList = new ArrayList<Cranes>();
            for (int i = 0; i < craneCount; i++) {
                Cranes crane = new Cranes(craneUnload);
                cranesList.add(crane);
            }

            List<ships> shipsArrived = new ArrayList<ships>();
            List<ships> shipsInProgress = new ArrayList<ships>();

            for (int i = 0; i<cranesList.size();i++) {
                cranesList.get(i).start();
            }
            while (!shipsList.isEmpty() || !shipsArrived.isEmpty() || !shipsInProgress.isEmpty() && System.currentTimeMillis() - startTime < 43200) {
                for (int i = 0; i < shipsList.size(); i++) {
                    int minute = Integer.parseInt(shipsList.get(i).getMinute());
                    int hour = Integer.parseInt(shipsList.get(i).getHour())*60;
                    int date = (shipsList.get(i).getDate()-1)*60*24;
                    if (minute == 0) {
                        minute++;
                    }
                    if (hour == 0) {
                        hour= 60;
                    }



                    if ((System.currentTimeMillis() - startTime) >= ((long) minute + hour + date)) {

//                        System.out.println(System.currentTimeMillis() - startTime);
//                        System.out.println(date + hour + minute);

                        shipsArrived.add(shipsList.get(i));
                        System.out.println(shipsList.get(i).getName() + " just Arrived!");
                        System.out.println("Arriving time: "+shipsList.get(i).getDate()+" "+Integer.parseInt(shipsList.get(i).getHour())+":"+Integer.parseInt(shipsList.get(i).getMinute()));
                        System.out.println("Type: "+shipsList.get(i).getType());
                        shipsList.remove(i);
                        arrivedCheck.add(shipsArrived.size());
                    }
                }

                for (int j = 0; j < cranesList.size(); j++) {
                    if (cranesList.get(j).isFree && cranesList.get(j).ship != null && !cranesList.get(j).isBusy && cranesList.get(j).ship.getWeight() == 0) {
                        if (!cranesList.get(j).isDual) {
                            System.out.println(cranesList.get(j).ship.getName() + " left this port");
                            shipsInProgress.remove(cranesList.get(j).ship);
                        } else cranesList.get(j).isDual = false;
                        cranesList.get(j).ship = null;

                    }
                    if (cranesList.get(j).isFree && !shipsArrived.isEmpty() && cranesList.get(j).ship == null) {
                        for (int k = 0; k < shipsArrived.size(); k++) {
                            if (cranesList.get(j).isFree) {
                                shipsArrived.get(k).incrCraneNum();
                                shipsInProgress.add(shipsArrived.get(k));
                                cranesList.get(j).setParams(shipsArrived.remove(k), shipsInProgress, startTime);
                                cranesList.get(j).isFree = false;
                                isNew = true;
                                synchronized (cranesList.get(j)) {
                                    cranesList.get(j).notify();
                                }
                            }
                        }
                    }
                    if (!isNew && !shipsInProgress.isEmpty() && cranesList.get(j).isFree && cranesList.get(j).ship == null) {
                        for (int k = 0; k < shipsInProgress.size(); k++) {
                            if (cranesList.get(j).isFree && shipsInProgress.get(k).getCraneNum() < 2) {
                                shipsInProgress.get(k).incrCraneNum();
                                System.out.println(" Second crane joined " + shipsInProgress.get(k).getName() + " unloading");
                                cranesList.get(j).setParams(shipsInProgress.get(k), shipsInProgress, startTime);
                                cranesList.get(j).isDual = true;
                                cranesList.get(j).isFree = false;
                                synchronized (cranesList.get(j)) {
                                    cranesList.get(j).notify();
                                }
                            }
                        }
                    }
                }
                isNew = false;
                long endTime;
                long minEndTime = 0;
                boolean flag = false;
                for (int j = 0; j < cranesList.size(); j++) {
                    if (!cranesList.get(j).isFree) {
                        endTime = cranesList.get(j).getEndTime();
                        if (minEndTime > endTime || minEndTime == 0) {
                            minEndTime = endTime;
                        }
                    } else flag = true;
                }
                if (!flag) {
                        if ((((minEndTime - (System.currentTimeMillis() - startTime)) / 60) * (shipsArrived.size() * 100L)) > 30000) {
                            Cranes crane = new Cranes(craneUnload);
                            cranesList.add(crane);
                            cranesList.get(cranesList.size() - 1).start();
                            System.out.println(" Added new crane for " + shipsArrived.get(0).getType());
                            increaseCraneCount();
                            isNew = true;
                        }
                }
            }
            Thread.sleep(4320);
            System.out.println("Station finished unload!");
            for (int i = 0; i< cranesList.size(); i++) {
                cranesList.get(i).setUnactive();
                synchronized (cranesList.get(i)) {
                    cranesList.get(i).notify();
                }
                this.unloadShipCount+=cranesList.get(i).unloadCount;
                this.delayCheck = cranesList.get(i).delayList;
                this.unloadDelayList = cranesList.get(i).unloadDelayList;
                if (longestUnloadDelay < cranesList.get(i).longestUnloadDelay) {
                    longestUnloadDelay = cranesList.get(i).longestUnloadDelay;
                }
                System.out.println("Crane "+ i + " unloaded "+cranesList.get(i).unloadCount + " ships");
                totalBill += cranesList.get(i).bill;
            }

        }
        @Override
        public void run() {
            try {
                this.createStation(shipsList, craneUnload,startTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Cranes extends Thread {
        private final long unload;
        private boolean isFree;

        private boolean isBusy;
        private boolean isActive;

        private boolean isDual;

        private volatile int i;
        private int unloadCount;
        private volatile ships ship;

        private long bill;

        private volatile List<ships> shipsInProgress;

        private List<Long> delayList;
        private List<Long> unloadDelayList;
        private long longestUnloadDelay;

        private long startTime;
        private long endTime;

        private long delay;


        public Cranes(long unload) {
            this.isFree = true;
            this.isBusy = true;
            this.isActive = true;
            this.unload = unload;
            this.unloadCount =0;
            this.delayList = new ArrayList<>();
            this.unloadDelayList = new ArrayList<>();
            this.longestUnloadDelay = 0;
            this.bill = 0;
            this.ship = null;
        }

        public void setParams(ships ship, List<ships> shipsInProgress, long startTime) {
            this.ship=ship;
            this.startTime=startTime;
            this.shipsInProgress = shipsInProgress;
        }

        public void setUnactive() {
            this.isActive = false;
        }
//        synchronized void waitMethod() throws InterruptedException {
//            wait();
//        }

        public long getEndTime() {
            return endTime;
        }

        public long getDelay() {
            return delay;
        }
        public void start(long startTime) throws InterruptedException {

            while (isActive) {
                if (isActive) {
                    int minute = Integer.parseInt(ship.getMinute());
                    int hour = Integer.parseInt(ship.getHour())*60;
                    int date = (ship.getDate()-1)*60*24;
                    if (minute == 0) {
                        minute++;
                    }
                    if (hour == 0) {
                        hour= 60;
                    }

                    long wait = System.currentTimeMillis() - startTime - (minute + hour + date);
                    if (!isDual) {
                        bill += (wait / 60) * 100;
                        delayList.add(wait);
                    }
                    long waitMinute = wait%60;
                    long waitHour = wait/60;
                    long waitDate = 0;
                    while (waitHour>=24) {
                        waitDate++;
                        waitHour -=24;
                    }
//                    if (ship.getCraneNum() == 2 ) {
//                        System.out.println(" Working with already unloading ship");
//                    }
                    System.out.println(" Starting work with "+ this.ship.getName());
                    if (!isDual) {
                        System.out.println(" " + this.ship.getName() + " was waiting in queue " + waitDate + " " + waitHour + ":" + waitMinute);
                    }
                    delay = (long) (Math.random()*1440);
                    if (!isDual) {
                        bill += (delay / 60) * 100;

                        unloadDelayList.add(delay);

                        if (longestUnloadDelay < delay) {
                            longestUnloadDelay = delay;
                        }
                    }
                    endTime = (System.currentTimeMillis()-startTime) + ((this.ship.getWeight()/unload)*240) + delay;
                    if (!isDual) {
                        System.out.println(" Will finish job " + (int) ((endTime / 1440) + 1));
                    }
                    int i=0;

                    while (endTime>System.currentTimeMillis()-startTime && ship.getWeight() !=0) {
                        int k;
                        k = shipsInProgress.indexOf(ship);
                        if (shipsInProgress.get(k).getCraneNum()==2 && i == 0 && !isDual) {
                            endTime = endTime - ((endTime - (System.currentTimeMillis()-startTime))/2);
                            System.out.println(" New unload finish day: " + (int)((endTime/1440)+1));
                            i++;
                        }
                    }
                    if (!isDual) {
                        System.out.println(" Crane unloaded " + this.ship.getName());
                        this.unloadCount += 1;
                        System.out.println(" Already unloaded " + this.unloadCount + " ships");
                    }
                    isFree=true;
                    isBusy = false;
                    this.ship.setWeight(0);
                    synchronized (this) {
                        this.wait();
                        isBusy = true;
                    }

                }
            }
//            System.out.println("краник всё");

        }
//        public  void start(int i, List<ships> shipsList, long startTime, long unloadTime) {
//                while (!isActive) {
//
//                }
////                    while (shipsList.isEmpty()) {
////
////                    }
//                        this.isFree = false;
//                        ships ship = shipsList.get(i);
//                        System.out.println("Crane started work with " + ship.getName() + " Type: " + ship.getType());
////            long unloadTime = (ship.getWeight() / unload) * 10;
//                        long endTime = (System.currentTimeMillis() - startTime) + unloadTime + (long) (Math.random() * 1440);
//                        System.out.println((System.currentTimeMillis() - startTime));
////                        System.out.println("Will end at " + endTime);
////            System.out.println(endTime);
//                        while (System.currentTimeMillis() - startTime < endTime) {
////                System.out.println(System.currentTimeMillis() - startTime);
//                        }
////                        System.out.println((System.currentTimeMillis() - startTime));
//                        System.out.println(ship.getName() + ": Job is Done");
////                        shipsList.remove(i);
////            System.out.println("Ships left: " + shipsList.size());
//                        this.isFree = true;
//                        this.isActive = false;
//
//                        start(i, shipsList, startTime, unload);
//        }

        @Override
        public void run() {
            while (shipsInProgress == null) {
                if (!isActive) {
                    break;
                }
            }
            if (isActive) {
                try {
                    this.start(startTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

//    static class Cranes extends Thread {
//        int craneTime = 1;
//
//        private int craneCount;
//        private int availableCount;
//        private boolean isWorking;
//        private int craneLoad;
//
//
//        public Cranes() {
//            this.craneCount = 1;
//            this.availableCount = 1;
//            this.isWorking = false;
//            this.craneLoad = 30;
//        }
//
//        void setCount(int count) {
//            this.craneCount=count;
//        }
//
//        void setAvailable(int count) {
//            this.availableCount=count;
//        }
//
//        public int getCount() {
//            return this.craneCount;
//        }
//
//        public int getAvailable() {
//            return this.availableCount;
//        }
//
//        void setLoad(int count) {
//            this.craneLoad=count;
//        }
//
//        public int getLoad() {
//            return this.craneLoad;
//        }
//
//        public void getTime() throws InterruptedException {
//            while (true) {
//                System.out.println(System.currentTimeMillis()-time);
//                sleep(500);
//            }
//        }

//        public void run(ships ship, int i, worldTime worldTime) throws InterruptedException {
//            if (!isWorking) {
//                craneTime = worldTime.getMinute();
//                ship.setWeight(ship.getWeight() - getLoad());
//                if (craneTime < 50) {
//                    craneTime += 1 + (int) (Math.random() * 10);
//                } else craneTime -= 1 + (int) (Math.random() * 49);
//                System.out.println(ship.getName() + ": " + ship.getWeight() + " " + i);
//                setAvailable(getAvailable()-1);
//                isWorking = true;
//            } else {
//                    if (craneTime == worldTime.getMinute()) {
//                        isWorking = false;
//                        setAvailable(getAvailable()+1);
//                    }
//                }
//        }
//            while (ship.getWeight()>0 && worldTime.getMinute() ==craneTime) {
//                ship.setWeight(ship.getWeight() - 1);
//                System.out.println("Crane#" + i + " did his job, ship weight: " + ship.getWeight());
//                craneTime += 3 + i;
//            }
//}
}
