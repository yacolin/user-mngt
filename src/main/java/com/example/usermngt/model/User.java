package com.example.usermngt.model;


import com.example.usermngt.service.Create;
import com.example.usermngt.service.Update;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private long id;

    @NotNull(message = "username cannot be null", groups = {Create.class})
    @Size(min=2, max=30)
    private String username;


    @NotNull(message = "password cannot be null", groups = {Create.class, Update.class})
    @Size(min=6, max=30)
    private String password;


    private String email;
}
