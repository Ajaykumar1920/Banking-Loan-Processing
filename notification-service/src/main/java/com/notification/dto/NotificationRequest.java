package com.notification.dto;

import com.notification.model.NotificationEvent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationRequest {
	@NotNull
    private NotificationEvent event;

    @NotBlank
    private String recipient;     // email / phone

    private String username;      // optional

    private String referenceId;   // loanId / userId

    private String message;       // optional override

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
