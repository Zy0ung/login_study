package com.example.security_jwt_token.schedule;

import com.example.security_jwt_token.entity.RefreshEntity;
import com.example.security_jwt_token.repository.RefreshRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * @author jiyoung
 */
@Component
@RequiredArgsConstructor
public class TokenScheduleTasks {

    private final RefreshRepository refreshRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void TokenVerified() {

        List<RefreshEntity> refreshList = refreshRepository.findAllByOrderByRefreshAsc();

        for (RefreshEntity refreshEntity : refreshList){
            if(refreshEntity.getExpiration().isBefore(LocalDateTime.now())){
                refreshRepository.deleteByRefresh(refreshEntity.getRefresh());
                System.out.println("delete refresh " + refreshEntity.getId());
            }
        }
    }
}