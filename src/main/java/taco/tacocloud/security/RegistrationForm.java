package taco.tacocloud.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import taco.tacocloud.TacoUser;

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public TacoUser toUser(PasswordEncoder passwordEncoder) {
    return new TacoUser(username,passwordEncoder.encode(password),
            fullname,street,city,state,zip,phone);
    }
}
