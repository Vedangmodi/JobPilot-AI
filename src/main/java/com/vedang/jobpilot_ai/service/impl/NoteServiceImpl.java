package com.vedang.jobpilot_ai.service.impl;

import com.vedang.jobpilot_ai.dto.request.NoteRequest;
import com.vedang.jobpilot_ai.dto.response.NoteResponse;
import com.vedang.jobpilot_ai.entity.Application;
import com.vedang.jobpilot_ai.entity.Note;
import com.vedang.jobpilot_ai.entity.User;
import com.vedang.jobpilot_ai.exception.ResourceNotFoundException;
import com.vedang.jobpilot_ai.exception.UnauthorizedException;
import com.vedang.jobpilot_ai.repository.ApplicationRepository;
import com.vedang.jobpilot_ai.repository.NoteRepository;
import com.vedang.jobpilot_ai.repository.UserRepository;
import com.vedang.jobpilot_ai.service.NoteService;
import com.vedang.jobpilot_ai.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public NoteServiceImpl(NoteRepository noteRepository, ApplicationRepository applicationRepository , UserRepository userRepository){
        this.noteRepository = noteRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NoteResponse createNote(NoteRequest noteRequest, Long id, Long userId){

        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not Found!"));

        Application application = applicationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Application Not Found!"));

        if (!application.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to view this application");
        }

        Note note = MapperUtil.noteRequestToNote(noteRequest);

        note.setApplication(application);

        Note savedNote = noteRepository.save(note);

        NoteResponse noteResponse = MapperUtil.noteToNoteResponse(savedNote);

        return noteResponse;

    }

    @Override
    public List<NoteResponse> getNotes(Long id, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        Application application = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Application Not Found!"));

        if (!application.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to view this application");
        }

        List<Note> notes = noteRepository.findByApplication(application);

        List<NoteResponse> list = new ArrayList<>();

        for(Note note : notes){
            list.add(MapperUtil.noteToNoteResponse(note));
        }

        return list;

    }

    @Override
    public void deleteNote(Long userId, Long noteId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        Note note = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note Not Found!"));
        Application application = note.getApplication();

        if (!application.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to delete this note");
        }

        noteRepository.deleteById(noteId);

    }
}
