package com.project.trippilot.session;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "travel_sessions")
@Getter
@Setter
public class TravelSession {

    @Id
    private String sessionId;

    @Column(columnDefinition = "TEXT")
    private String userQuery;

    @Column(columnDefinition = "TEXT")
    private String stateJson;
}