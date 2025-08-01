package com.journalApp.jounalApp.Controller;

import com.journalApp.jounalApp.entity.User;
import com.journalApp.jounalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class publicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String health(){
        return "Ok";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }
}
