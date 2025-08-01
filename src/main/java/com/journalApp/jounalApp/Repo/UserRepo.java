package com.journalApp.jounalApp.Repo;

import com.journalApp.jounalApp.entity.User;
import com.journalApp.jounalApp.entity.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
    void deleteByUserName(String username);
}
