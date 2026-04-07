package com.vedang.jobpilot_ai.service.impl;

import com.vedang.jobpilot_ai.dto.request.ApplicationRequest;
import com.vedang.jobpilot_ai.dto.response.ApplicationResponse;
import com.vedang.jobpilot_ai.entity.Application;
import com.vedang.jobpilot_ai.entity.User;
import com.vedang.jobpilot_ai.entity.enums.ApplicationStatus;
import com.vedang.jobpilot_ai.exception.ResourceNotFoundException;
import com.vedang.jobpilot_ai.exception.UnauthorizedException;
import com.vedang.jobpilot_ai.repository.ApplicationRepository;
import com.vedang.jobpilot_ai.repository.UserRepository;
import com.vedang.jobpilot_ai.service.ApplicationService;
import com.vedang.jobpilot_ai.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {
//    @Autowired on field
//    @Autowired ApplicationRepository applicationRepository;

    // Constructor injection

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, UserRepository userRepository){
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ApplicationResponse create(ApplicationRequest req, Long userId){
        Application application = MapperUtil.applicatonRequestToApplication(req);

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
        application.setUser(user);

        Application savedApplication = applicationRepository.save(application);

        ApplicationResponse applicationResponse = MapperUtil.applicationToApplicationResponse(savedApplication);

        return applicationResponse;

    }

    @Override
    public List<ApplicationResponse> getAll(Long userId){

//        Optional<Application> optionalApplication = applicationRepository.findById(userId);
//        if(optionalApplication.isEmpty()){
//            throw new ResourceNotFoundException("Not Application Found!");
//
//        }  -- wrong

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        List<Application> applications = applicationRepository.findByUser(user);

        List<ApplicationResponse> ans = new ArrayList<>();

        for(Application application : applications){
            ans.add(MapperUtil.applicationToApplicationResponse(application));

        }

        return ans;

    }

    @Override
    public ApplicationResponse getById(Long id, Long userId){
        User user = userRepository.findById(userId).orElseThrow(()  -> new ResourceNotFoundException("User Not Found!"));

        Application application = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Application Not Found!"));

        if(!application.getUser().getId().equals(userId)){
            throw new UnauthorizedException("You are not authorized to view this application");
        }

        return MapperUtil.applicationToApplicationResponse(application);


    }

    @Override
    public ApplicationResponse update(ApplicationRequest applicationRequest, Long id, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        Application application = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Application Not Found!"));

        if(!application.getUser().getId().equals(userId)){
            throw new UnauthorizedException("You are not authorized to view this application");
        }

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

        Application savedApplication = applicationRepository.save(application);

        return MapperUtil.applicationToApplicationResponse(savedApplication);

    }

    @Override
    public void delete(Long id, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        Application application = applicationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Application Not Found!"));

        if(!application.getUser().getId().equals(userId)){
            throw new UnauthorizedException("You are not authorized to view this application");
        }

        applicationRepository.deleteById(id);

    }

    @Override
    public List<ApplicationResponse> getAllWithFilter(Long userId, ApplicationStatus applicationStatus, String search){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        List<Application> application = applicationRepository.findByUserWithFilter(user, applicationStatus, search);

        List<ApplicationResponse> list = new  ArrayList<>();

        for(Application app : application){
            list.add(MapperUtil.applicationToApplicationResponse(app));
        }

        return list;

    }

    @Override
    public Page<ApplicationResponse> getAllPaginated(Long userId, int page, int size){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        Pageable pageable = PageRequest.of(page, size);

        Page<Application> applications = applicationRepository.findByUser(user, pageable);

//        Page<ApplicationResponse> res = MapperUtil.applicationToApplicationResponse(applications);

        Page<ApplicationResponse> res = applications.map(app -> MapperUtil.applicationToApplicationResponse(app));

        return res;



    }

}




