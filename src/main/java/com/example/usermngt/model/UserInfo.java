package com.example.usermngt.model;

import com.example.usermngt.service.Create;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfo {
    private long id;
    private String lastname;
    private String firstname;
    private int age;
    private int gender;
    private String hobbies;
    private String tags;

    @Min(value= 1, message = "user_id cannot be null", groups = {Create.class})
    private long userId;
}
