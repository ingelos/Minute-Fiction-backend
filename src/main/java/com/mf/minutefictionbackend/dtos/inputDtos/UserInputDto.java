package com.mf.minutefictionbackend.dtos.inputDtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserInputDto {


    @NotBlank(message = "Username is required")
    public String username;
    @NotBlank(message = "Password is required")
    public String password;
    @Email(message = "Invalid email")
    public String email;
    public Boolean subscribedToMailing;


    public UserInputDto() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSubscribedToMailing() {
        return subscribedToMailing;
    }

    public void setSubscribedToMailing(Boolean subscribedToMailing) {
        this.subscribedToMailing = subscribedToMailing;
    }
}
