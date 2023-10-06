package com.example.api.auth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginParamDTO {

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    @Size(min=8)
    private String userPassword;
}
