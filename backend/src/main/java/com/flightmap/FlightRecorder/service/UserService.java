package main.java.com.flightmap.FlightRecorder.service;


import main.java.com.flightmap.FlightRecorder.model.User;
import main.java.com.flightmap.FlightRecorder.repository.UserRepository;

import java.util.Optional;
import java.util.*;

/*Class that serves as the database for all known users that utilise the app*/
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerNewUser(String username, Long id, String email, String password ){
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("Username already in use");
        }

        if(userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("Email already in use");
        }
        User newUser = new User(username,id,email,password);
        userRepository.save(newUser); //save the new user to the known database
        return newUser;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll(); //returns list data structure of all the users in repository database
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void addFlightEntry(String username,String airport, String flight, String country, String airline){
      User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
      user.addFlightEntry(airport,flight,country,airline);
      userRepository.save(user);
    }

    public void deactivateUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.active = false;
        userRepository.save(user);
        //could use userRepository.delete to fully remove it from database but you never know if users want to come back and reregister
    }

    public void reactivateUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.active = true;
        userRepository.save(user);
    }




}
