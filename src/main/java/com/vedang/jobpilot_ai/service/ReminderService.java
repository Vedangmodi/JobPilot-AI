package com.vedang.jobpilot_ai.service;

import com.vedang.jobpilot_ai.dto.request.ReminderRequest;
import com.vedang.jobpilot_ai.dto.response.ReminderResponse;
import com.vedang.jobpilot_ai.entity.Reminder;

import java.util.List;

public interface ReminderService {
    ReminderResponse createReminder(ReminderRequest reminderRequest, Long id);

    List<ReminderResponse> getReminders(Long id);

    ReminderResponse updateReminder(ReminderRequest reminderRequest, Long reminderId);

    void deleteReminder(Long reminderId);
}
