package com.vedang.jobpilot_ai.util;

import com.vedang.jobpilot_ai.dto.request.ApplicationRequest;
import com.vedang.jobpilot_ai.dto.request.NoteRequest;
import com.vedang.jobpilot_ai.dto.request.ReminderRequest;
import com.vedang.jobpilot_ai.dto.response.ApplicationResponse;
import com.vedang.jobpilot_ai.dto.response.NoteResponse;
import com.vedang.jobpilot_ai.dto.response.ReminderResponse;
import com.vedang.jobpilot_ai.entity.Application;
import com.vedang.jobpilot_ai.entity.Note;
import com.vedang.jobpilot_ai.entity.Reminder;

public class MapperUtil {
    public static Application applicatonRequestToApplication(ApplicationRequest applicationRequest) {
        Application application = new Application();

        application.setCompanyName(applicationRequest.getCompanyName());
        application.setRoleTitle(applicationRequest.getRoleTitle());
        application.setJobLink(applicationRequest.getJobLink());
        application.setLocation(applicationRequest.getLocation());
        application.setSalary(applicationRequest.getSalary());
        application.setStatus(applicationRequest.getStatus());
        application.setSource(applicationRequest.getSource());
        application.setNotesSummary(applicationRequest.getNotesSummary());
        application.setAppliedDate(applicationRequest.getAppliedDate());
        application.setJobDescription(applicationRequest.getJobDescription());

        return application;
    }

    public static ApplicationResponse applicationToApplicationResponse(Application application) {
        ApplicationResponse applicationResponse = new ApplicationResponse();

        applicationResponse.setId(application.getId());
        applicationResponse.setCompanyName(application.getCompanyName());
        applicationResponse.setRoleTitle(application.getRoleTitle());
        applicationResponse.setJobLink(application.getJobLink());
        applicationResponse.setLocation(application.getLocation());
        applicationResponse.setSalary(application.getSalary());
        applicationResponse.setStatus(application.getStatus());
        applicationResponse.setSource(application.getSource());
        applicationResponse.setNotesSummary(application.getNotesSummary());
        applicationResponse.setAppliedDate(application.getAppliedDate());
        applicationResponse.setJobDescription(application.getJobDescription());
        applicationResponse.setCreatedAt(application.getCreatedAt());
        applicationResponse.setUpdatedAt(application.getUpdatedAt());

        return applicationResponse;
    }

    public static Note noteRequestToNote(NoteRequest noteRequest){
        Note note = new Note();

        note.setContent(noteRequest.getContent());

        return note;
    }

    public static NoteResponse noteToNoteResponse(Note note){
        NoteResponse noteResponse = new NoteResponse();

        noteResponse.setId(note.getId());
        noteResponse.setContent(note.getContent());
        noteResponse.setCreatedAt(note.getCreatedAt());
        noteResponse.setUpdatedAt(note.getUpdatedAt());
        noteResponse.setApplicationId(note.getApplication().getId());

        return noteResponse;
    }

    public static Reminder reminderRequestToReminder(ReminderRequest reminderRequest){
        Reminder reminder = new Reminder();

        reminder.setMessage(reminderRequest.getMessage());
        reminder.setReminderDate((reminderRequest.getReminderDate()));

        return reminder;
    }

    public static ReminderResponse reminderToReminderResponse(Reminder reminder){
        ReminderResponse reminderResponse = new ReminderResponse();

        reminderResponse.setId(reminder.getId());
        reminderResponse.setMessage(reminder.getMessage());
        reminderResponse.setReminderDate(reminder.getReminderDate());
        reminderResponse.setCompleted(reminder.isCompleted());
        reminderResponse.setCreatedAt(reminder.getCreatedAt());
        reminderResponse.setApplicationId(reminder.getApplication().getId());

        return reminderResponse;

    }
}