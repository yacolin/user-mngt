package com.example.usermngt.model;

import com.example.usermngt.service.Create;
import com.example.usermngt.service.Update;
import jakarta.validation.constraints.Min;
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
public class Post {
    private long id;

    @NotNull(message = "title cannot be null", groups = {Create.class})
    @Size(min=2, max=30)
    private String title;

    @NotNull(message = "content cannot be null", groups = {Create.class, Update.class})
    @Size(min=2, max=100)
    private String content;

    private long liked;

    @Min(value= 1, message = "user_id cannot be null", groups = {Create.class})
    private long userId;
}
