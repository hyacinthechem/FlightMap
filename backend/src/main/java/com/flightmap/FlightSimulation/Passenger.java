package main.java.com.flightmap.FlightSimulation;

import java.util.*;
public class Passenger implements Comparable<Passenger> {

    public String name;
    public String flight;
    public Passenger passenger;
    public String status;
    public boolean bagDropped = false;


    public Passenger(String name, String flight, String status) {
        this.name = name;
        this.flight = flight;
        this.status = status;

    }

    public String getName(){
        return name;
    }

    public String baggageDrop() {
        bagDropped = true;
        return ("Passenger: " + name + " Succesfully dropped bag");
    }

    public boolean allBagsDropped() {
        return bagDropped;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public String getStatus(){
        return status;
    }

    @Override
    public String toString() {
        return ("name: " + name + " Flight: " + flight + " Airpoints Status: " + status);
    }

    @Override

    public int compareTo(Passenger other){
        if(this.status.equals("Airpoints Platinum")){
            if(!other.status.equals("Airpoints Platinum")){
                return -1;
            }
        }

        else if(this.status.equals("Airpoints Elite")){
            if(other.status.equals("Airpoints Platinum")){
                return 1;
            }else if(!other.status.equals("Airpoints Elite")){
                return -1;
            }
        }

        else if(this.status.equals("Airpoints Gold")){
            if(other.status.equals("Airpoints Platinum") || other.status.contains("Airpoints Elite")){
                return 1;
            }else if(!other.status.equals("Airpoints Gold")){
                return -1;
            }
        }
        else if(this.status.equals("Airpoints Silver")){
            if(other.status.equals("Airpoints Platinum") || other.status.contains("Airpoints Gold") || other.status.contains("Airpoints Elite")){
                return 1;
            }else if(!other.status.equals("Airpoints Silver")){
                return -1;
            }
        }

        if(this.status.equals(other.status)){
            return 0;
        }
        return 0;
    }





    public int getPriority(){
        switch(status){
            case "Airpoints Platinum":
              return 1;
            case "Airpoints Elite":
              return 2;
            case "Airpoints Gold":
              return 3;
            case "Airpoints Silver":
              return 4;
        }

       return 0;
    }



    /*
    @Override

    public int compareTo(Passenger other){

        List<String> memberStatus = Arrays.asList("Airpoints Silver","Airpoints Gold", "Airpoints Elite", "Airpoints Platinum");
        Collections.reverse(memberStatus);
        int thisPassengerStatus = memberStatus.indexOf(this.status);
        int otherPassengerStatus = memberStatus.indexOf(other.status);

        return Integer.compare(thisPassengerStatus,otherPassengerStatus);
    }

     */

}
