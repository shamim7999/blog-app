package org.dsi.blogapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 20, nullable = false)
    private String title;
    @Column(length = 100, nullable = false)
    private String content;
    private String imageName;

    private LocalDateTime localDateTime;

    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;
}
