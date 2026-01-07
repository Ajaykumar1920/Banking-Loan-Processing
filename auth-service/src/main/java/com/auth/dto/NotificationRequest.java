package com.auth.dto;

import com.auth.model.NotificationEvent;

public class NotificationRequest {
	
	private NotificationEvent event;
    private String recipient;
    private String username;
    private String referenceId;
    private String message;
    
	public NotificationEvent getEvent() {
		return event;
	}
	public void setEvent(NotificationEvent event) {
		this.event = event;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
