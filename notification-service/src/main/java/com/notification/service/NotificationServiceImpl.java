package com.notification.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.notification.dto.NotificationRequest;

@Service
public class NotificationServiceImpl implements NotificationService {

	private final JavaMailSender mailSender;
	private final NotificationDispatcher dispatcher;

	public NotificationServiceImpl(JavaMailSender mailSender, NotificationDispatcher dispatcher) {
		this.mailSender = mailSender;
		this.dispatcher = dispatcher;
	}

	@Override
	public void send(NotificationRequest request) {

		String message = dispatcher.buildMessage(request);

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(request.getRecipient());
		mail.setSubject("Bank Notification");
		mail.setText(message);

		mailSender.send(mail);
	}
}
