package org.dsi.blogapp.payload;

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
public class CategoryDto {

    private int id;

    @Size(min = 1, message = "Title must contain at least 3 characters.")
    @NotNull
    @NotBlank
    private String title;

    @Size(min = 3, message = "Description must contain at least 3 characters.")
    @NotNull
    @NotBlank
    private String description;
}
