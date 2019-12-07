package org.roza.components.impl;

import org.roza.components.UserCreation;
import org.roza.domain.models.services.UserServiceModel;
import org.roza.domain.models.services.VerificationTokenServiceModel;
import org.roza.exceptions.UserAlreadyExistsException;
import org.roza.services.EmailService;
import org.roza.services.UserService;
import org.roza.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class UserCreationImpl implements UserCreation {

    private final UserService userService;
    private final VerificationTokenService tokenService;
    private final EmailService emailService;

    @Autowired
    public UserCreationImpl(UserService userService,
                            VerificationTokenService tokenService,
                            EmailService emailService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @Override
    public UserServiceModel addUser(UserServiceModel userServiceModel) throws UserAlreadyExistsException {
        return this.userService.addUser(userServiceModel);
    }

    @Override
    public VerificationTokenServiceModel createToken(UserServiceModel userServiceModel) {
        return this.tokenService.createRegistrationToken(userServiceModel);
    }

    @Override
    public void sendVerificationEmail(VerificationTokenServiceModel tokenServiceModel, String domain) throws MessagingException {
        this.emailService.sendVerificationEmail(tokenServiceModel, domain);
    }
}
