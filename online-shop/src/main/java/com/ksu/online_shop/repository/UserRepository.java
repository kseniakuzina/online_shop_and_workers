package com.ksu.online_shop.repository;

import com.ksu.online_shop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    @Query("SELECT u.username, u.roles FROM User u")
    List<Object[]> findAllUsersWithRoles();
}
