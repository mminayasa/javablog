package com.pustaka.pustaka.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;
    // title should not be null or empty
    // title should have at least 2 characters
    @NotEmpty
    @Column(unique = true)
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;
    // description should not be null or empty
    // description should have at least 10 characters
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;
    // post content should not be null or empty
    @NotEmpty
    private String content;
}
