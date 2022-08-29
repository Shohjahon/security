package uz.playground.security.dto;

import uz.playground.security.validation.annotation.Password;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignupDto {
    @NotBlank(message = "valid.not.blank.signup.name")
    private String name;
    @NotBlank(message = "valid.not.blank.signup.username")
    private String username;
    @Email(message = "valid.email.signup.email")
    private String email;
    @Password(message = "valid.password.signup.password")
    private String password;

    public SignupDto(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public SignupDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupRequest{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
