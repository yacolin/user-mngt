package com.example.usermngt.repository;

import com.example.usermngt.entity.PostEntity;
import com.example.usermngt.entity.UserEntity;
import com.example.usermngt.model.PostWithUserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>, JpaSpecificationExecutor<PostEntity> {
    Page<PostEntity> findByUserEntity(UserEntity userEntity, Pageable pageable);

    // 此种方法不可行，虽然达到了要求
    // @Query("SELECT p.id as id, p.title as title, p.content as content, p.liked as liked, p.createTime as createTime, p.updateTime as updateTime, p.userEntity.id as userId FROM PostEntity p")

    // 此语句相对上述 as 简短了一点，也算是精简从而满足了要求
    // @Query("SELECT new com.example.usermngt.model.PostWithUserId(p.id, p.title, p.content, p.liked, p.createTime, p.updateTime, p.userEntity.id) FROM PostEntity p")
    @Query("SELECT new com.example.usermngt.model.PostWithUserId(p, p.userEntity.id) FROM PostEntity p")
    Page<PostWithUserId> findAllWithUserId(Specification<PostEntity> specification, Pageable pageable);

}
