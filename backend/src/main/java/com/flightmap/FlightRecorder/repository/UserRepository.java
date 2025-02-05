package main.java.com.flightmap.FlightRecorder.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import main.java.com.flightmap.FlightRecorder.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //define using springboot that this is the repository location
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

}
