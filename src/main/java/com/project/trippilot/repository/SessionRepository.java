package com.project.trippilot.repository;

import com.project.trippilot.session.TravelSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<TravelSession,String> {
}
