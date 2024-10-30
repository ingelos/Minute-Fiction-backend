package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateEmailDto {

    @Size(min=8, message = "Password needs to be at least 8 characters long")
    private String currentPassword;
    @Email(message = "Invalid email")
    private String email;
//    @NotNull
//    private boolean subscribedToMailing;

//    private Set<AuthorityInputDto> authorities = new HashSet<>();


}
