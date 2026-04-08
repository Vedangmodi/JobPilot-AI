package com.vedang.jobpilot_ai.service;

import com.vedang.jobpilot_ai.dto.request.ReminderRequest;
import com.vedang.jobpilot_ai.dto.response.ReminderResponse;
import com.vedang.jobpilot_ai.entity.Reminder;

import java.util.List;

public interface ReminderService {
    ReminderResponse createReminder(ReminderRequest reminderRequest, Long id, Long userId);

    List<ReminderResponse> getReminders(Long id, Long userId);

    ReminderResponse updateReminder(ReminderRequest reminderRequest, Long reminderId, Long userId);

    void deleteReminder(Long userId, Long reminderId);
}
