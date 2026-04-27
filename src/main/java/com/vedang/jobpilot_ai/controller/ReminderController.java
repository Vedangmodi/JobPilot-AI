package com.vedang.jobpilot_ai.controller;

import com.vedang.jobpilot_ai.dto.request.ReminderRequest;
import com.vedang.jobpilot_ai.dto.response.ApplicationResponse;
import com.vedang.jobpilot_ai.dto.response.ReminderResponse;
import com.vedang.jobpilot_ai.entity.Reminder;
import com.vedang.jobpilot_ai.service.ReminderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService){
        this.reminderService = reminderService;
    }

    @PostMapping("/applications/{id}/reminders")
    public ResponseEntity<ReminderResponse> createReminder(@RequestBody ReminderRequest reminderRequest,
                                                           @PathVariable Long id){
        ReminderResponse reminderResponse = reminderService.createReminder(reminderRequest, id);

        return new ResponseEntity<>(reminderResponse, HttpStatus.CREATED);
    }

    @GetMapping("/applications/{id}/reminders")
    public ResponseEntity<List<ReminderResponse>> getReminders(@PathVariable Long id){
        List<ReminderResponse> responses = reminderService.getReminders(id);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/reminders/{reminderId}/complete")
    public ResponseEntity<ReminderResponse> updateReminder(@RequestBody ReminderRequest reminderRequest,
                                                              @PathVariable Long reminderId){
        ReminderResponse response = reminderService.updateReminder(reminderRequest, reminderId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/reminders/{reminderId}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long reminderId){
        reminderService.deleteReminder(reminderId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
