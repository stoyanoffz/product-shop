package org.roza.services;

import org.roza.domain.models.services.VerificationTokenServiceModel;

import javax.mail.MessagingException;

public interface EmailService {

    void sendVerificationEmail(VerificationTokenServiceModel tokenServiceModel, String domain) throws MessagingException;
}
