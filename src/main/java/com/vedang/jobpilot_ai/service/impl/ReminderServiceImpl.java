package com.vedang.jobpilot_ai.service.impl;

import com.vedang.jobpilot_ai.dto.request.ReminderRequest;
import com.vedang.jobpilot_ai.dto.response.NoteResponse;
import com.vedang.jobpilot_ai.dto.response.ReminderResponse;
import com.vedang.jobpilot_ai.entity.Application;
import com.vedang.jobpilot_ai.entity.Reminder;
import com.vedang.jobpilot_ai.entity.User;
import com.vedang.jobpilot_ai.exception.ResourceNotFoundException;
import com.vedang.jobpilot_ai.exception.UnauthorizedException;
import com.vedang.jobpilot_ai.repository.ApplicationRepository;
import com.vedang.jobpilot_ai.repository.NoteRepository;
import com.vedang.jobpilot_ai.repository.ReminderRepository;
import com.vedang.jobpilot_ai.repository.UserRepository;
import com.vedang.jobpilot_ai.service.ReminderService;
import com.vedang.jobpilot_ai.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService{

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final ReminderRepository reminderRepository;

    public ReminderServiceImpl(UserRepository userRepository, ApplicationRepository applicationRepository, ReminderRepository reminderRepository){
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.reminderRepository = reminderRepository;
    }


    @Override
    public ReminderResponse createReminder(ReminderRequest reminderRequest, Long id, Long userId){
//        return null;
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        Application application = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Application Not Found!"));

        if(!application.getUser().getId().equals(userId)){
            throw new UnauthorizedException("You are not authorized to view this application");
        }

        Reminder reminder = MapperUtil.reminderRequestToReminder(reminderRequest);

        reminder.setApplication(application);

        Reminder savedReminder = reminderRepository.save(reminder);

        return MapperUtil.reminderToReminderResponse(savedReminder);

    }

    @Override
    public List<ReminderResponse> getReminders(Long id, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        Application application = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Application Not Found!"));

        if(!application.getUser().getId().equals(userId)){
            throw new UnauthorizedException("You are not authorized to view this application");
        }

        List<Reminder> reminderList = reminderRepository.findByApplication(application);

        List<ReminderResponse>  responses = new ArrayList<>();

        for(Reminder reminder : reminderList){
            responses.add(MapperUtil.reminderToReminderResponse(reminder));
        }

        return responses;

    }

    @Override
    public ReminderResponse updateReminder(ReminderRequest reminderRequest, Long reminderId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        Reminder reminder = reminderRepository.findById(reminderId).orElseThrow(() -> new ResourceNotFoundException("Reminder Not Found"));

        Application application = reminder.getApplication();

        if(!application.getUser().getId().equals(userId)){
            throw new UnauthorizedException("You are not authorized to view this application");
        }

        reminder.setReminderDate(reminderRequest.getReminderDate());
        reminder.setMessage(reminderRequest.getMessage());
        reminder.setCompleted(reminderRequest.isCompleted());

        Reminder savedReminder = reminderRepository.save(reminder);

        return MapperUtil.reminderToReminderResponse(savedReminder);

    }

    @Override
    public void deleteReminder(Long userId, Long reminderId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        Reminder reminder = reminderRepository.findById(reminderId).orElseThrow(() -> new ResourceNotFoundException("Reminder Not Found"));

        Application application = reminder.getApplication();

        if(!application.getUser().getId().equals(userId)){
            throw new UnauthorizedException("You are not authorized to delete this Reminder");
        }

        reminderRepository.deleteById(reminderId);
    }
}
