package org.dsi.blogapp.repository;

import org.dsi.blogapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReository extends JpaRepository<Comment, Integer> {
}
