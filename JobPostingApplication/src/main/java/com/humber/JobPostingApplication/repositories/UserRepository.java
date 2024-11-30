package com.humber.JobPostingApplication.repositories;

import com.humber.JobPostingApplication.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    //get optional user by username
    public Optional<MyUser> findByUsername(String username);
}
