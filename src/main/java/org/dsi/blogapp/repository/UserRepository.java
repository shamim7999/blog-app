package org.dsi.blogapp.repository;

import org.dsi.blogapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
