package com.journalApp.jounalApp.service;

import com.journalApp.jounalApp.Repo.UserRepo;
import com.journalApp.jounalApp.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
public class userServiceTest {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;


    @ParameterizedTest
    @ArgumentsSource(ArgumentProvider.class)
    public void testSaveNewUser(User name){
//        assertEquals(8,7+1);
        assertTrue(userService.saveNewUser(name));
    }


//    @Disabled
//    @ParameterizedTest
//    @CsvSource({
//            "1,7,8",
//            "3,5,8",
//            "9,10,19"
//    })
//    public void test(int a, int b,int res){
//        assertEquals(res,a+b);
//    }
}
