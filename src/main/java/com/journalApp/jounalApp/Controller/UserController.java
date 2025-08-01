package com.journalApp.jounalApp.Controller;

import com.journalApp.jounalApp.Repo.UserRepo;
import com.journalApp.jounalApp.entity.User;
import com.journalApp.jounalApp.entity.journalEntry;
import com.journalApp.jounalApp.service.UserService;
import com.journalApp.jounalApp.service.journalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    private final PasswordEncoder pwd = new BCryptPasswordEncoder();

    @Autowired
    private UserRepo userRepo;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User userIndb = userService.findByUserName(username);
            userIndb.setUserName(user.getUserName());
            userIndb.setPassword(pwd.encode(user.getPassword()));
            userService.saveEntry(userIndb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        userRepo.deleteByUserName(auth.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
