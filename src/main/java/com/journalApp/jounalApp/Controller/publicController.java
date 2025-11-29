package com.journalApp.jounalApp.controller;

import com.journalApp.jounalApp.entity.User;
import com.journalApp.jounalApp.service.UserDetailServiceImp;
import com.journalApp.jounalApp.service.UserService;
import com.journalApp.jounalApp.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class publicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @GetMapping("/health-check")
    public String health(){
        return "Ok";
    }

    @PostMapping("/signup")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));

            UserDetails userDetails = userDetailServiceImp.loadUserByUsername(user.getUserName());
            String token = jwtUtil.getToken(userDetails.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occur during authentication",e);
            return new ResponseEntity<>("Incorrect UserName and password ",HttpStatus.BAD_REQUEST);
        }
    }
}
