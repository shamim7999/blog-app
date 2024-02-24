package org.dsi.blogapp.repository;

import org.dsi.blogapp.model.Category;
import org.dsi.blogapp.model.Post;
import org.dsi.blogapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    public List<Post> findByUser(User user);
    public List<Post> findByCategory(Category category);

    @Query("Select p from Post p where p.title like :keyword")
    public List<Post> findByTitleContaining(@Param ("keyword") String keyword);
}
