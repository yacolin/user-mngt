package com.example.usermngt.controller;


import com.example.usermngt.entity.TeamEntity;
import com.example.usermngt.model.Team;
import com.example.usermngt.service.Create;
import com.example.usermngt.service.TeamService;
import com.example.usermngt.util.FormValidate;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }


    @GetMapping("/team")
    public ResponseEntity<Map<String, Object>> getAllTeams(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String id
    ) {
        // 构建分页和查询字段
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Specification<TeamEntity> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (!id.equals("")) {
                predicateList.add(criteriaBuilder.equal(root.get("id"), id));
            }
            // 当所有条件都为空时，即无字段查询
            if (predicateList.size() == 0) {
                return null;
            }
            return criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()]));
        };
        Map<String, Object> response = teamService.getAllTeams(specification, pageable);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/team")
    public ResponseEntity<Boolean> Create(@RequestBody @Validated(Create.class) Team team, BindingResult bindingResult) {
        FormValidate.validateField(bindingResult);
        
        boolean flag = teamService.create(team);
        return ResponseEntity.ok(flag);
    }

    @PutMapping("/team/{id}")
    public ResponseEntity<Boolean> Update(@PathVariable("id") Integer id, @RequestBody @Validated Team team, BindingResult bindingResult) {
        FormValidate.validateField(bindingResult);

        boolean flag = teamService.update(id, team);
        return ResponseEntity.ok(flag);
    }
}
