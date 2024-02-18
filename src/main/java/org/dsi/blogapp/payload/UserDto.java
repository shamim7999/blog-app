package org.dsi.blogapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotNull
    @NotBlank
    @Size(min = 3, message = "Enter minimum 3 characters.")
    private String name;

    @Email(message = "Email is Not valid")
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 3, message = "Password must contain at least 3 characters.")
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 3, message = "Enter minimum 3 characters.")
    private String about;
}
