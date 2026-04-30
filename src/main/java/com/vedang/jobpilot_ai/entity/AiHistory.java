package com.vedang.jobpilot_ai.entity;

import com.vedang.jobpilot_ai.entity.enums.AiFeatureType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ai_history")
public class AiHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name  = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = true)
    private Application application;

    @Enumerated(EnumType.STRING)
    private AiFeatureType featureType;

    @Column(columnDefinition="TEXT")
    private String inputText;

    @Column(columnDefinition="TEXT")
    private String outputText;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public AiHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public AiFeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(AiFeatureType featureType) {
        this.featureType = featureType;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
