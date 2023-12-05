package com.sparta.spring1week.controller.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // 커스텀 헬스 체크 로직
        int errorCode = check(); // 사용자 정의 메소드
        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    private int check() {
        // 특정 상태 검사 로직
        return 0; // 정상인 경우 0을 반환
    }
}