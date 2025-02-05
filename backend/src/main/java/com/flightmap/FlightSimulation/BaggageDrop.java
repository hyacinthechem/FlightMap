package main.java.com.flightmap.FlightSimulation;

import java.sql.Array;
import java.util.*;
import ecs100.*;

public class BaggageDrop {

    public static final int BAG_DROP_COUNTERS = 4;
    public static final int MAX_BAGDROP_PASSENGERS = 5;
    public Queue<Passenger> bagDropQueue; //individual bagdrop
    public Queue<Passenger> waitingQueue; //the main waiting queue
    public List<Queue<Passenger>> allBagDrops; //list of all bagdrops
    public boolean running = true;
    public boolean prioQueue;
    public int delay = 200;


  public void setupGUI(){
    UI.addButton("Run Baggage Drop" , this::run);
    UI.addButton("Run Baggage Drop Member",()->{prioQueue = true; resetQueues(); loadPassengers(); run();});
    UI.addButton("Display Checked-in Passengers", this::displayPassengers);
    UI.addButton("Exit", UI::quit);
    UI.addSlider("Speed", 1, 400, (double val) -> { val = delay;});
   }


   public void displayPassengers(){
      for(Passenger p : waitingQueue){
          UI.println(p.toString());
      }
   }

public void resetQueues(){

      if(prioQueue) {

         Comparator<Passenger> memberStatus = new Comparator<>() {
             public int compare(Passenger p1, Passenger p2) {
                 if (p1.getPriority() > p2.getPriority()) {
                     return 1;
                 } else if (p1.getPriority() < p2.getPriority()) {
                     return -1;
                 } else if (p1.getPriority() == p2.getPriority()) {
                     return 0;
                 }
                 return 0;
             }
         };

          waitingQueue = new PriorityQueue<>(memberStatus);


      }else{
          waitingQueue = new ArrayDeque<>();
      }


      allBagDrops = new ArrayList<Queue<Passenger>>();

      for(int i = 0; i < BAG_DROP_COUNTERS; i++){
          allBagDrops.add(new ArrayDeque<>()); //initialise the individual bagdrops and add them to bag drops queue
      }

      for(Queue<Passenger> bagDrop : allBagDrops){
          bagDropQueue = new ArrayDeque<Passenger>();
      }

}



public void run(){
    //Main simulation
    int time = 0;
    int count = 0;
    while(running){
        if(waitingQueue.isEmpty()){
            running = false;
        }
        for(Queue<Passenger> bagDrop : allBagDrops){
            //check if a bag drop is empty. if its empty add it until full from mainqueue
            time++;
            while(bagDrop.size() < MAX_BAGDROP_PASSENGERS && !waitingQueue.isEmpty()){
                bagDrop.offer(waitingQueue.poll());
            }

            for(Passenger p : bagDrop) {
                Passenger current = bagDrop.poll();
                count++;
                if (current != null) {
                    String bDrop = current.baggageDrop();
                    UI.println(bDrop + current.getStatus());
                    UI.println(count);
                }
                UI.sleep(delay);
            }

        }

    }

}
public void loadPassengers(){
      String[] names = {"Logan Clark",
              "Daniel Baker", "Zoe Johnson",
              "Sophie Taylor", "Sarah Campbell",
              "Joseph Thompson", "John Taylor", "Isabella Allen",
              "Nathan Robinson", "Ella Thompson", "Jane Turner",
              "Hannah Allen", "Chloe Hall", "Isabella King",
              "Ella Allen", "Jane Scott", "Andrew Rodriguez",
              "Jacob Adams", "David Miller", "Logan Anderson", "Logan Miller",
              "Ella Jackson", "Grace Anderson", "Logan Lewis", "Mia White",
              "Sophia Clark", "Nathan Lee", "Joseph Rodriguez", "Daniel Adams",
              "Emily Lee", "Zoe Roberts", "Isabella Perez", "Lily Johnson", "Matthew Nelson", "Michael Brown",
              "William Garcia", "William Clark", "Anthony Phillips", "Ryan Garcia", "Mia Baker",
              "Joseph Mitchell", "Amelia Mitchell", "Isabella Baker", "Andrew Martinez", "Sarah Campbell",
              "Chloe Phillips", "Lily Martin", "Isabella Baker", "Madison Davis", "Nathan Mitchell",
              "Zoe Harris", "Nathan Green", "Benjamin Thomas", "Grace Lee", "Daniel White", "Joseph Taylor",
              "Samuel Robinson", "James Robinson", "Nathan Lewis", "Ryan Hall", "Zoe Lee", "Ava Roberts", "Hannah Thompson",
              "David Martinez", "Matthew Williams", "Daniel Miller", "Ella Martinez",
              "Zoe Baker", "Michael Adams", "Samuel Robinson", "Jacob Thomas", "William King",
              "Emma Lewis", "Logan Robinson", "William Garcia", "Anthony Wilson", "Madison Jackson",
              "Madison Mitchell", "Charlotte Jones", "Jane Roberts",
              "Logan Roberts", "Amelia Perez", "Ella Baker", "James Taylor", "Ethan Baker", "James Baker", "Andrew Carter", "Ethan Lee", "Charlotte Jackson",
              "Michael Young", "Ava Carter", "Olivia Young", "Jacob Green", "Andrew Jones", "Daniel Lewis", "Zoe Johnson", "Lily White", "Olivia Robinson",
              "Madison Scott", "Ryan Clark", "Ava Taylor", "Ella Miller", "Ava Allen", "Laura Perez", "Ella Adams", "Sarah Jackson",
              "Sophie Lewis", "Lily Williams", "Charlotte White", "Zoe Robinson", "Logan Scott", "Ava Williams", "Logan King", "Sophia Nelson", "Ella Wilson", "Amelia Martinez", "Mia Wilson", "Matthew Martin", "Jane Green", "Laura Campbell", "Sophie Rodriguez", "Grace Adams", "Sophia Taylor", "Andrew Clark", "Sophia Thompson", "Hannah King", "Anthony Rodriguez", "Daniel Baker", "Michael Anderson", "Andrew Martinez", "Matthew Scott", "Chloe Green",
              "Michael Brown", "Madison Johnson", "Charlotte Thomas", "Alex Davis", "Hannah Jones", "Anthony Garcia", "Isabella Lee", "Ella Baker", "Emma Baker", "Ava Jackson", "Benjamin Nelson", "Sophia Lewis", "Jacob Scott", "Daniel Rodriguez", "Chloe Brown", "Laura Allen",
              "Ella Jones", "Sophie King", "Sophie Carter", "Sarah Adams", "Emily Walker", "Hannah Williams", "John Jackson",
              "Mia Taylor", "William Thompson", "Sophia Lewis", "Jane Perez", "Daniel Green", "James Thomas", "Nathan Wilson",
              "Chris Miller", "Chris Anderson", "David Phillips", "Lily Martin", "Daniel Perez", "Andrew Turner", "Joshua Rodriguez", "Sarah Garcia", "Grace Adams", "Joseph White", "William Jackson", "Jane Clark", "Madison Mitchell", "Emily Walker", "Emily Nelson", "Nathan Nelson", "Benjamin Johnson", "Grace Thompson", "Mia Rodriguez", "Hannah Young", "Chloe Garcia", "Joseph Jones", "Amelia Williams", "Isabella Jackson", "Hannah Clark", "Alex Lewis", "Hannah Johnson", "Samuel Allen", "John Martin",
              "Hannah White", "Ryan Lewis", "Ava Thomas", "Emma Jones", "Emily Jones",
              "Hannah Davis", "Andrew Phillips", "Emma Robinson", "Chris Wilson"};

    String[] flightNumbers = {"ANZ510", "ANZ671", "ANZ711", "ANZ106", "ANZ298", "ANZ376", "ANZ278", "ANZ188", "ANZ195", "ANZ858", "ANZ731", "ANZ352", "ANZ671", "ANZ556", "ANZ500", "ANZ929", "ANZ260", "ANZ429", "ANZ984", "ANZ593", "ANZ734", "ANZ124", "ANZ113", "ANZ529", "ANZ923", "ANZ374", "ANZ906", "ANZ247", "ANZ826", "ANZ820", "ANZ634", "ANZ262", "ANZ339", "ANZ538", "ANZ742", "ANZ481",};
    String[] memberStatus = {"Airpoints Silver","Airpoints Gold", "Airpoints Elite", "Airpoints Platinum" };



    Random rand = new Random();
    for(int i = 0; i < 200; i++){
        String name = names[rand.nextInt(names.length)];
        String flightNumber = flightNumbers[rand.nextInt(flightNumbers.length)];
        String membStatus = memberStatus[rand.nextInt(memberStatus.length)];
        Passenger p = new Passenger(name,flightNumber,membStatus);
        waitingQueue.offer(p);
    }
}

public static void main(String[] args){
    BaggageDrop bd = new BaggageDrop();
    bd.resetQueues();
    bd.loadPassengers();
    bd.setupGUI();

}




}
