package com.tutorial.identity.repository;

import com.tutorial.identity.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    Staff getStaffById(String id);
    void deleteStaffById(String id);
    boolean existsStaffByUserName(String username);
    boolean existsStaffById(String id);
    Optional<Staff> findStaffByUserName(String username);
}
