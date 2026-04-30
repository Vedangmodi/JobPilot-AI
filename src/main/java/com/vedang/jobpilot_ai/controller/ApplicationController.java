package com.vedang.jobpilot_ai.controller;

import com.vedang.jobpilot_ai.dto.request.ApplicationRequest;
import com.vedang.jobpilot_ai.dto.response.ApplicationResponse;
import com.vedang.jobpilot_ai.entity.User;
import com.vedang.jobpilot_ai.entity.enums.ApplicationStatus;
import com.vedang.jobpilot_ai.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    @PostMapping()
    public ResponseEntity<ApplicationResponse> create(@Valid @RequestBody ApplicationRequest req){
        ApplicationResponse applicationResponse = applicationService.create(req);

        return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);

    }

    @GetMapping()
    public ResponseEntity<List<ApplicationResponse>> getAll(){
        List<ApplicationResponse> response = applicationService.getAll();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getById(@PathVariable("id") Long id){
        ApplicationResponse response = applicationService.getById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> update(@Valid @RequestBody ApplicationRequest applicationRequest,
                                                      @PathVariable("id") Long id){
        ApplicationResponse response = applicationService.update(applicationRequest, id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        applicationService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/filter")
    public ResponseEntity<List<ApplicationResponse>> getAllWithFilter(@RequestParam(required = false) ApplicationStatus applicationStatus,
                                                                      @RequestParam(required = false) String search){
        List<ApplicationResponse> response = applicationService.getAllWithFilter(applicationStatus, search);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<ApplicationResponse>> getAllPaginated(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size){

        Page<ApplicationResponse> response = applicationService.getAllPaginated(page, size );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

//    @GetMapping("/more-than-10-notes")
//
//    public ResponseEntity<List<ApplicationResponse>> getAppMoreThan10Notes(){
//        List<ApplicationResponse> res = applicationService.getAppMoreThan10Notes();
//
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }










}



