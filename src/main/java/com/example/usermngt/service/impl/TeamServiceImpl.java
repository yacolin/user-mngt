package com.example.usermngt.service.impl;

import com.example.usermngt.entity.TeamEntity;
import com.example.usermngt.model.Team;
import com.example.usermngt.repository.TeamRepository;
import com.example.usermngt.service.TeamService;
import com.example.usermngt.util.BizException;
import com.example.usermngt.util.PageableList;
import com.example.usermngt.util.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamsRepository) {
        this.teamRepository = teamsRepository;
    }

    @Override
    public Map<String, Object> getAllTeams(Specification<TeamEntity> specification, Pageable pageable) {
        Page<TeamEntity> teamsEntityPage = teamRepository.findAll(specification, pageable);
        long total = teamsEntityPage.getTotalElements();

        List<TeamEntity> teams = teamsEntityPage.getContent();
        return PageableList.map(total, teams);
    }

    @Override
    public boolean create(Team team) {
        TeamEntity teamEntity = new TeamEntity();
        BeanUtils.copyProperties(team, teamEntity);

        teamRepository.save(teamEntity);
        return true;
    }

    @Override
    public boolean update(Integer id, Team team) {
        TeamEntity teamEntity = getTeamEntity(id);

        if (team.getChampions() > 0) {
            teamEntity.setChampions(team.getChampions());
        }
        if (team.getDivide() != null) {
            teamEntity.setDivide(team.getDivide());
        }

        if (team.getLogo() != null) {
            teamEntity.setLogo(team.getLogo());
        }

        teamRepository.save(teamEntity);
        return true;
    }

    @Override
    public TeamEntity getTeamEntity(Integer id) {
        Optional<TeamEntity> OptionalT = teamRepository.findById(id);
        TeamEntity teamEntity = OptionalT.isPresent()?OptionalT.get() : null;

        if (teamEntity == null) {
            throw new BizException(ResultEnum.DATA_NOT_EXIST);
        }

        return teamEntity;
    }
}
