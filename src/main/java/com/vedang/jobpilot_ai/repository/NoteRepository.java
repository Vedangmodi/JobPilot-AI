package com.vedang.jobpilot_ai.repository;

import com.vedang.jobpilot_ai.entity.Application;
import com.vedang.jobpilot_ai.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByApplication(Application application);
}
