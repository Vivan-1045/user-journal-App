package com.journalApp.jounalApp.service;

import com.journalApp.jounalApp.Repo.UserRepo;
import com.journalApp.jounalApp.Repo.journalRepo;
import com.journalApp.jounalApp.entity.User;
import com.journalApp.jounalApp.entity.journalEntry;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepo userRepo; //Dependency injection
    private final PasswordEncoder pwd = new BCryptPasswordEncoder();

    public void saveEntry(User user){
        userRepo.save(user);
    }

    public boolean saveNewUser(User user){
        try {
            user.setPassword(pwd.encode(user.getPassword()));
            user.setRole(Arrays.asList("USER"));
            userRepo.save(user);
            return true;
        }catch (Exception e){
            log.error("error");
            log.warn("Warn");
            log.info("info");
            log.debug("debug");
            log.trace("trace");

            return false;
        }
    }

    public void saveAdminUser(User user){
        user.setPassword(pwd.encode(user.getPassword()));
        user.setRole(Arrays.asList("USER","ADMIN"));
        userRepo.save(user);
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepo.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepo.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }


}


//Controller --> Service --> repoEntry