package com.journalApp.jounalApp.repository;

import com.journalApp.jounalApp.Repo.UserRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoImplTest {

    @Autowired
    private UserRepoImpl userRepo;

    @Test
   public void testUser(){
       userRepo.getUserForSA();
   }
}
