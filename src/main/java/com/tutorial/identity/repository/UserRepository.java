package com.tutorial.identity.repository;

import com.tutorial.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    //Custom Query
    @Query(value = "select * from user u where u.first_name like %:firstName%", nativeQuery = true)
    List<User> findAllUserByFirstName(@Param(value = "firstName") String firstName);
}
