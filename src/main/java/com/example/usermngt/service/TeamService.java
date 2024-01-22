package com.example.usermngt.service;

import com.example.usermngt.entity.TeamEntity;
import com.example.usermngt.model.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public interface TeamService {
    Map<String, Object> getAllTeams(Specification<TeamEntity> specification, Pageable pageable);

    boolean create(Team team);

    boolean update(Integer id, Team team);

    TeamEntity getTeamEntity(Integer id);
}
