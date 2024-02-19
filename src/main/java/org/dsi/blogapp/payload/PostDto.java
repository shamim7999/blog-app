package org.dsi.blogapp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dsi.blogapp.model.Category;
import org.dsi.blogapp.model.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {
    private int id;
    private String title;
    private String content;
    private String imageName;
    private LocalDateTime localDateTime;
    private CategoryDto categoryDto;
    private UserDto userDto;
}
