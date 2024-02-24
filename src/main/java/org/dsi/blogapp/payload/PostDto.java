package org.dsi.blogapp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<CommentDto> commentDtos = new ArrayList<>();
}
