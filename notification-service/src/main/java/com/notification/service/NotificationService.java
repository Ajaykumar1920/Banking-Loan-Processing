package com.notification.service;

import com.notification.dto.NotificationRequest;

public interface NotificationService {

	void send(NotificationRequest request);
}
