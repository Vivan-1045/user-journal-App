package com.journalApp.jounalApp.service;

import com.journalApp.jounalApp.Repo.UserRepo;
import com.journalApp.jounalApp.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.*;


public class UserServiceDetailImpTest {
    @InjectMocks
    private UserDetailServiceImp userDetailServiceImp;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    void setUpInitailization(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepo.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("Mohit").password("Mohit10452").role(new ArrayList<>()).build());
        UserDetails user = userDetailServiceImp.loadUserByUsername("Vivek");
        Assertions.assertNotNull(user);
    }
}
