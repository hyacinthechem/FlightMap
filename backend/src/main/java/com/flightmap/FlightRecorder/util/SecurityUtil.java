package main.java.com.flightmap.FlightRecorder.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtil{

private static final BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();

public static String hashPassword(String password){

return bpe.encode(password);
}

}
