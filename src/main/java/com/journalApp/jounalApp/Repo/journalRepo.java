package com.journalApp.jounalApp.Repo;

import com.journalApp.jounalApp.entity.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface journalRepo extends MongoRepository<journalEntry, ObjectId> {

}
