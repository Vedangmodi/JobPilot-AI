package com.vedang.jobpilot_ai.dto.response;

public class DashboardResponse {
    private Long totalApplications;
    private Long applied;
    private Long oa;
    private Long interview;
    private Long rejected;
    private Long offer;
    private Long ghosted;

    public DashboardResponse() {
    }

    public DashboardResponse(Long totalApplications, Long applied, Long oa, Long interview, Long rejected, Long offer, Long ghosted) {
        this.totalApplications = totalApplications;
        this.applied = applied;
        this.oa = oa;
        this.interview = interview;
        this.rejected = rejected;
        this.offer = offer;
        this.ghosted = ghosted;
    }

    public Long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(Long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public Long getApplied() {
        return applied;
    }

    public void setApplied(Long applied) {
        this.applied = applied;
    }

    public Long getOa() {
        return oa;
    }

    public void setOa(Long oa) {
        this.oa = oa;
    }

    public Long getInterview() {
        return interview;
    }

    public void setInterview(Long interview) {
        this.interview = interview;
    }

    public Long getRejected() {
        return rejected;
    }

    public void setRejected(Long rejected) {
        this.rejected = rejected;
    }

    public Long getOffer() {
        return offer;
    }

    public void setOffer(Long offer) {
        this.offer = offer;
    }

    public Long getGhosted() {
        return ghosted;
    }

    public void setGhosted(Long ghosted) {
        this.ghosted = ghosted;
    }
}