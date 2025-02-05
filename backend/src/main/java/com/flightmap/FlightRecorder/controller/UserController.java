package main.java.com.flightmap.FlightRecorder.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.java.com.flightmap.FlightRecorder.model.User;
import main.java.com.flightmap.FlightRecorder.service.UserService;
import java.util.*;

//Class that communicates with HTTP Requests to change states in database
@RestController
@RequestMapping("/users")

public class UserController {
   private final UserService userService;

   @Autowired //injects the UserService logic into this controller
    public UserController(UserService userService) {
       this.userService = userService;
   }

   @PostMapping("/create")

    public ResponseEntity<User> createUser(@RequestParam String username, @RequestParam Long id, @RequestParam String email, @RequestParam String password){
       User user = userService.registerNewUser(username,id,email,password);
       return ResponseEntity.ok(user); //Accept HTML post request
   }

   @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
       return ResponseEntity.ok(userService.getAllUsers());
   }

   @GetMapping("/email{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        Optional<User> user = userService.getUserByEmail(email);
       return ResponseEntity.ok(user.orElse(null));
   }

   @GetMapping("/username{username}")
   public ResponseEntity<User> getUserByUsername(@PathVariable String username){
       Optional<User> user = userService.getUserByUsername(username);
       return ResponseEntity.ok(user.orElse(null));
   }

   @PutMapping("/{username}/New Flight Entry")
    public ResponseEntity<User> addFlightEntry(@PathVariable String username, @RequestParam String airport,@RequestParam String flight, @RequestParam String country,@RequestParam String airline){
       try{
       userService.addFlightEntry(username,airport,flight,country,airline);
       return ResponseEntity.ok().build(); //accept request but also build the new entry

   }catch(IllegalArgumentException e){
         return ResponseEntity.notFound().build();
       }
   }

   @PutMapping("/{username}/deactivate")
    public ResponseEntity<User> deactivateUser(@PathVariable String username){
        try {
            userService.deactivateUser(username);
            return ResponseEntity.ok().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
   }
}
