package com.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.auth.dto.NotificationRequest;

@FeignClient(
	    name = "notification-service"
//	    url = "http://localhost:8010"
	)
public interface NotificationFeignClient {

	@PostMapping("/notifications")
    void sendNotification(@RequestBody NotificationRequest request);
}
