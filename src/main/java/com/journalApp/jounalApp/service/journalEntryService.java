package com.journalApp.jounalApp.service;

import com.journalApp.jounalApp.Repo.UserRepo;
import com.journalApp.jounalApp.Repo.journalRepo;
import com.journalApp.jounalApp.entity.User;
import com.journalApp.jounalApp.entity.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class journalEntryService {
    @Autowired
    private journalRepo journalRepo; //Dependency injection
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public void saveEntry (journalEntry journalEntry, String userName)throws Exception{
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            journalEntry saved = journalRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        } catch (Exception e) {
            throw new Exception("Error ocurred during savings of jounal Entry");
        }

    }

    public void saveEntry(journalEntry journalEntry){
        journalRepo.save(journalEntry);
    }

    public List<journalEntry> getAll(){
        return journalRepo.findAll();
    }

    public Optional<journalEntry> findById(ObjectId id){
        return journalRepo.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveEntry(user);
                journalRepo.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during deletion of entry ",e);
        }
        return removed;
    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }

}


//Controller --> Service --> repoEntry