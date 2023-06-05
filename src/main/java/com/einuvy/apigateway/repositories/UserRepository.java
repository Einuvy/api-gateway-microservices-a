package com.einuvy.apigateway.repositories;

import com.einuvy.apigateway.enums.Role;
import com.einuvy.apigateway.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User SET role=:role WHERE username=:username")
    void updateUserRole(@Param("username") String username, @Param("role")Role role);
}