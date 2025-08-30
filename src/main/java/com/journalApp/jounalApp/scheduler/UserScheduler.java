package com.journalApp.jounalApp.scheduler;

import com.journalApp.jounalApp.Repo.UserRepoImpl;
import com.journalApp.jounalApp.entity.User;
import com.journalApp.jounalApp.entity.journalEntry;
import com.journalApp.jounalApp.service.EmailService;
import com.journalApp.jounalApp.service.sentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepoImpl userRepo;

    @Autowired
    private sentimentAnalysisService analysisService;

   @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendMail(){
        List<User> users = userRepo.getUserForSA();
        for (User user : users){
            List<journalEntry> journalEntries = user.getJournalEntries();
            List<String> filtered = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(s -> s.getContent()).collect(Collectors.toList());
            String entry = String.join(" ",filtered);
            String sentiment = analysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(),"Weekly Sentiment Analysis",sentiment);
        }
    }
}
