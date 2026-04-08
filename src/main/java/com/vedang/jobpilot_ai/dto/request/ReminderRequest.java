package com.vedang.jobpilot_ai.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class ReminderRequest {
    @NotBlank(message = "Reminder message is required")
    private String message;

    private LocalDate reminderDate;

    private boolean completed;


    public ReminderRequest() {
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public @NotBlank(message = "Reminder message is required") String getMessage() {
        return message;
    }

    public void setMessage(@NotBlank(message = "Reminder message is required") String message) {
        this.message = message;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate = reminderDate;
    }
}
