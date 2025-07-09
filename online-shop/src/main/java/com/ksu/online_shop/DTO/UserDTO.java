package com.ksu.online_shop.DTO;

import com.ksu.online_shop.entities.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class UserDTO {
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;


    public UserDTO(String username, String password, String firstName, String lastName, String email, String phone) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public User toUser(PasswordEncoder encoder){
        return new User(firstName,lastName,username,encoder.encode(password),email,phone);
    }
}
