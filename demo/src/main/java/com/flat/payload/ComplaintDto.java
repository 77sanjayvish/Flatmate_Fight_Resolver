package com.flat.payload;

import com.flat.enums.ComplaintType;
import com.flat.enums.SeverityLevel;
import java.time.LocalDateTime;

public class ComplaintDto {

    private String title;
    private String description;
    private ComplaintType complainType;
    private SeverityLevel severityLevel;
    private boolean resolved;
    private LocalDateTime localDateTime;
    private int upVotes;
    private int downVotes;
    private Long filedByUserId;


    public ComplaintDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ComplaintType getComplainType() {
        return complainType;
    }

    public void setComplainType(ComplaintType complainType) {
        this.complainType = complainType;
    }

    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(SeverityLevel severityLevel) {
        this.severityLevel = severityLevel;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public Long getFiledByUserId() {
        return filedByUserId;
    }

    public void setFiledByUserId(Long filedByUserId) {
        this.filedByUserId = filedByUserId;
    }

    public ComplaintDto(String title, String description, ComplaintType complainType, SeverityLevel severityLevel, boolean resolved, LocalDateTime localDateTime, int upVotes, int downVotes, Long filedByUserId) {
        this.title = title;
        this.description = description;
        this.complainType = complainType;
        this.severityLevel = severityLevel;
        this.resolved = resolved;
        this.localDateTime = localDateTime;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.filedByUserId = filedByUserId;
    }
}
