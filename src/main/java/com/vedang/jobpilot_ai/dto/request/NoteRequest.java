package com.vedang.jobpilot_ai.dto.request;

import com.vedang.jobpilot_ai.entity.Application;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public class NoteRequest {

    @NotBlank(message = "Note is required")
    private String content;

    public @NotBlank(message = "Note is required") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "Note is required") String content) {
        this.content = content;
    }
}
