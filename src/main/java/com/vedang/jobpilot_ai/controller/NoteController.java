package com.vedang.jobpilot_ai.controller;

import com.vedang.jobpilot_ai.dto.request.NoteRequest;
import com.vedang.jobpilot_ai.dto.response.NoteResponse;
import com.vedang.jobpilot_ai.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @PostMapping("/api/applications/{applicationid}/notes")
    public ResponseEntity<NoteResponse> createNote(@RequestBody NoteRequest noteRequest,
                                                   @PathVariable("applicationid") Long id){
        NoteResponse response = noteService.createNote(noteRequest, id);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/api/applications/{applicationid}/notes")
    public ResponseEntity<List<NoteResponse>> getNotes(@PathVariable("applicationid") Long id){

        List<NoteResponse> response = noteService.getNotes(id);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/api/notes/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable("noteId") Long noteId){
        noteService.deleteNote(noteId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
