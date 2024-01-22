package com.example.usermngt.model;


import com.example.usermngt.service.Create;
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
public class Team {
    private int id;

    @NotNull(message = "name cannot be null", groups = {Create.class})
    @Size(min=6, max=30)
    private String name;

    @NotNull(message = "city cannot be null", groups = {Create.class})
    @Size(min=6, max=30)
    private String city;

    @NotNull(message = "part cannot be null", groups = {Create.class})
    private char part;

    private String divide;

    private String logo;

    private int champions;
}
