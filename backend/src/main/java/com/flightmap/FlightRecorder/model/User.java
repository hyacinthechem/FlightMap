package main.java.com.flightmap.FlightRecorder.model;
import jakarta.persistence.*;
import lombok.*;
import main.java.com.flightmap.FlightRecorder.util.SecurityUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //User Administrative Details
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto generates Id for User

    private String username;
    private Long id;
    private String email;
    private String password;
    public boolean active;


   //User flight statistics

    private int numberOfFlights;
    private int numberOfCountries;
    private int numberOfAirlines;
    private int numberOfAirports;

    //Data structures to hold flights, countries, airlines, airports

    @ElementCollection
    private Set<String> airports = new HashSet<>();
    @ElementCollection
    private Set<String> airlines = new HashSet<>();
    @ElementCollection
    private Set<String> countries = new HashSet<>();
    @ElementCollection
    private List<String> flights = new ArrayList<>();

    public User(String username, Long id, String email, String password){
        if(username == null || username.trim().isEmpty()){
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if(!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        }

        //admin
        this.username = username;
        this.id = id;
        this.email = email;
        this.password = SecurityUtil.hashPassword(password);
        this.active = true;
        //Data Structures to hold user stats
    }

    /*ADMIN METHODS TO CHECK EMAIL VALIDITY AND PASSWORD HASHING*/

    private static boolean isValidEmail(String email){
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    //Getters are automatically handled by the lombok dependency to avoid boilerplate code


    /* Methods to update users flight,airport,airline,country history*/

    public void addFlightEntry(String airport, String flight, String country, String airline){
        exceptionHandling(airport,flight,country,airline);

        if(airports.add(airport)){
            numberOfAirports++;
        }

        if(airlines.add(airline)){
            numberOfAirlines++;
        }

        if(countries.add(country)){
            numberOfCountries++;
        }

        flights.add(flight);
        numberOfFlights++;
    }

    public boolean exceptionHandling(String airport, String flight, String country, String airline){
        if(airport == null || airport.trim().isEmpty()){
            throw new IllegalArgumentException("Airport cannot be empty");
        }

        if(flight == null || flight.trim().isEmpty()){
            throw new IllegalArgumentException("Flight cannot be empty");
        }

        if(country == null || country.trim().isEmpty()){
            throw new IllegalArgumentException("Country cannot be empty");
        }

        if(airline == null || airline.trim().isEmpty()){
            throw new IllegalArgumentException("Airline cannot be empty");
        }
        return true;
    }


    /*Override Equals and HashCode*/
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        User user = (User) o;
        return this.id!=null && this.id.equals(user.id);
    }

    @Override
    public int hashCode(){
        int hash = 17;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.email);
        hash = 31 * hash + Objects.hashCode(this.username);
        return hash;
    }
}
