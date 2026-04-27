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
import com.vedang.jobpilot_ai.util.AuthUtil;
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
    private final AuthUtil authUtil;


    public ApplicationServiceImpl(ApplicationRepository applicationRepository, UserRepository userRepository, AuthUtil authUtil){
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.authUtil = authUtil;
    }

    @Override
//    public ApplicationResponse create(ApplicationRequest req, Long userId){
    public ApplicationResponse create(ApplicationRequest req) {
        Application application = MapperUtil.applicatonRequestToApplication(req);

//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
//        application.setUser(user);

        User user = authUtil.getCurrentUser();

        Application savedApplication = applicationRepository.save(application);

        ApplicationResponse applicationResponse = MapperUtil.applicationToApplicationResponse(savedApplication);

        return applicationResponse;

    }

    @Override
    public List<ApplicationResponse> getAll(){

//        Optional<Application> optionalApplication = applicationRepository.findById(userId);
//        if(optionalApplication.isEmpty()){
//            throw new ResourceNotFoundException("Not Application Found!");
//
//        }  -- wrong
        User user = authUtil.getCurrentUser();

        List<Application> applications = applicationRepository.findByUser(user);

        List<ApplicationResponse> ans = new ArrayList<>();

        for(Application application : applications){
            ans.add(MapperUtil.applicationToApplicationResponse(application));

        }

        return ans;

    }

    @Override
    public ApplicationResponse getById(Long id){
        User user = authUtil.getCurrentUser();
        Application application = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Application Not Found!"));

        if(!application.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException("You are not authorized to view this application");
        }

        return MapperUtil.applicationToApplicationResponse(application);


    }

    @Override
    public ApplicationResponse update(ApplicationRequest applicationRequest, Long id){
        User user = authUtil.getCurrentUser();
        Application application = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Application Not Found!"));

        if(!application.getUser().getId().equals(user.getId())){
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
    public void delete(Long id){
        User user = authUtil.getCurrentUser();
        Application application = applicationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Application Not Found!"));

        if(!application.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException("You are not authorized to view this application");
        }

        applicationRepository.deleteById(id);

    }

    @Override
    public List<ApplicationResponse> getAllWithFilter(ApplicationStatus applicationStatus, String search){
        User user = authUtil.getCurrentUser();
        List<Application> application = applicationRepository.findByUserWithFilter(user, applicationStatus, search);

        List<ApplicationResponse> list = new  ArrayList<>();

        for(Application app : application){
            list.add(MapperUtil.applicationToApplicationResponse(app));
        }

        return list;

    }

    @Override
    public Page<ApplicationResponse> getAllPaginated(int page, int size){
        User user = authUtil.getCurrentUser();
        Pageable pageable = PageRequest.of(page, size);

        Page<Application> applications = applicationRepository.findByUser(user, pageable);

//        Page<ApplicationResponse> res = MapperUtil.applicationToApplicationResponse(applications);

        Page<ApplicationResponse> res = applications.map(app -> MapperUtil.applicationToApplicationResponse(app));

        return res;



    }

//    @Override
//    public List<ApplicationResponse> getAppMoreThan10Notes(){
//        List<Application> application = applicationRepository.findAppWithMoreThan10Notes();
//
//        List<ApplicationResponse> responses = new ArrayList<>();
//
//        for(Application app : application){
//            responses.add(MapperUtil.applicationToApplicationResponse(app));
//        }
//
//        return responses;
//    }

}




