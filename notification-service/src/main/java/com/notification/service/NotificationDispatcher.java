package com.notification.service;

import org.springframework.stereotype.Service;

import com.notification.dto.NotificationRequest;
import com.notification.model.NotificationEvent;

@Service
public class NotificationDispatcher {
	
	public String buildMessage(NotificationRequest request) {

        if (request.getMessage() != null) {
            return request.getMessage();
        }

        NotificationEvent event = request.getEvent();

        return switch (event) {

            case ACCOUNT_CREATED ->
                "Welcome " + request.getUsername() +
                ", your account has been successfully created.";

            case ACCOUNT_DISABLED ->
                "Your account has been disabled. Please contact support.";

            case LOAN_APPLIED ->
                "Your loan application (" + request.getReferenceId() +
                ") has been submitted successfully.";

            case LOAN_REVIEW_PENDING_MANAGER ->
                "Your loan (" + request.getReferenceId() +
                ") is pending manager approval.";

            case LOAN_APPROVED ->
                "Congratulations! Your loan (" + request.getReferenceId() +
                ") has been approved.";

            case LOAN_REJECTED ->
                "We regret to inform you that your loan application (" + request.getReferenceId() +
                ") has been rejected.";

            case LOAN_DISBURSED ->
                "Loan amount for " + request.getReferenceId() +
                " has been successfully disbursed.";
        };
    }
}
