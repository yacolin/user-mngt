package com.example.usermngt.repository;


import com.example.usermngt.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Integer>, JpaSpecificationExecutor<TeamEntity> {
}
