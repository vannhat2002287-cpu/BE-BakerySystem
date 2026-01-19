package com.ra.bakerysystem.scheduler;

import com.ra.bakerysystem.service.FactoryRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class FactoryRequestScheduler {

    private final FactoryRequestService factoryRequestService;

    @Scheduled(cron = "0 0 12,17 * * *")
    public void runAutoFactoryRequests() {
        factoryRequestService.autoCreateFactoryRequests();
    }
}