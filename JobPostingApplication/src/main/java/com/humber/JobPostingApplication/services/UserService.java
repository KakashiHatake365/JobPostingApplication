package com.humber.JobPostingApplication.services;

import com.humber.JobPostingApplication.models.MyUser;
import com.humber.JobPostingApplication.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //saves a new user with encrypted password
    //save the user to the database/registration
    // 0 - users already exists, 1 user has been added succesffuly
    public int saveUser(MyUser user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return 0;
        }

        //encrypting the password
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        //saving the user to database
        userRepository.save(user);
        return 1;
    }
}
