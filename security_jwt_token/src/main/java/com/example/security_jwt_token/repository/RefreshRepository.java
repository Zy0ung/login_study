package com.example.security_jwt_token.repository;


import com.example.security_jwt_token.entity.RefreshEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jiyoung
 */
@Repository
public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {

    Boolean existsByRefresh(String token);

    // 데이터 변경 작업이기 때문에 Transactional 어노테이션을 붙인다.
    @Transactional
    void deleteByRefresh(String token);
}