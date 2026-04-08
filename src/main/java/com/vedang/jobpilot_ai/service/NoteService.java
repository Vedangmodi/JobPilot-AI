package com.vedang.jobpilot_ai.service;

import com.vedang.jobpilot_ai.dto.request.NoteRequest;
import com.vedang.jobpilot_ai.dto.response.NoteResponse;

import java.util.List;

public interface NoteService {
    NoteResponse createNote(NoteRequest noteRequest, Long id, Long userId);

    List<NoteResponse> getNotes(Long id, Long userId);

    void deleteNote(Long userId, Long noteId);
}
