package com.journalApp.jounalApp.Controller;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class journalEntryControllerV2 {

    @Autowired
    private journalEntryService journalEntryService; // Spring makes the instance of journal entry service and we import it in a desired class
    @Autowired
    private UserService userService;


    //Get Entry of all User at ones
    @GetMapping("/allEntries")
    public ResponseEntity<?> getallJournalEntriesOfUsers(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<journalEntry> listAll = journalEntryService.getAll();
        if (listAll != null && !listAll.isEmpty()){
            return new ResponseEntity<>(listAll, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Get entries of patricular user (in review)
//    @GetMapping
//    public ResponseEntity<?> getallEntriesOfUser(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String userName = auth.getName();
//
//        User user = userService.findByUserName(userName);
//        List<journalEntry> listAll = user.getJournalEntries();
//        if (listAll != null && !listAll.isEmpty()){
//            return new ResponseEntity<>(listAll, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PostMapping
    public ResponseEntity<journalEntry> createEntry(@RequestBody journalEntry content){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();

            journalEntryService.saveEntry(content,userName);
            return new ResponseEntity<>(content, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<journalEntry> getById(@PathVariable ObjectId myid){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findByUserName(userName);
        List<journalEntry> list =  user.getJournalEntries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());

        if (!list.isEmpty()) {
            Optional<journalEntry> journalEntry = journalEntryService.findById(myid);
            if (journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{Id}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId Id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
       boolean removed = journalEntryService.deleteById(Id,userName);
       if (removed) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }else{
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<journalEntry> update(
            @PathVariable ObjectId id,
            @RequestBody journalEntry content)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findByUserName(userName);
        List<journalEntry> list =  user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());

        if (!list.isEmpty()) {
            Optional<journalEntry> journalEntry = journalEntryService.findById(id);
            if (journalEntry.isPresent()){
                journalEntry oldEntry = journalEntry.get();
                if (content.getTitle() != null && !content.getTitle().isEmpty()) {
                    oldEntry.setTitle(content.getTitle());
                }

                if (content.getContent() != null && !content.getContent().isEmpty()) {
                    oldEntry.setContent(content.getContent());
                }

                journalEntryService.saveEntry(oldEntry);

                return ResponseEntity.ok(oldEntry);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
