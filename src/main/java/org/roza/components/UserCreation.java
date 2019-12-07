package org.roza.components;

import org.roza.domain.models.services.UserServiceModel;
import org.roza.domain.models.services.VerificationTokenServiceModel;
import org.roza.exceptions.UserAlreadyExistsException;

import javax.mail.MessagingException;

public interface UserCreation {
    UserServiceModel addUser(UserServiceModel userServiceModel) throws UserAlreadyExistsException;

    VerificationTokenServiceModel createToken(UserServiceModel userServiceModel);

    void sendVerificationEmail(VerificationTokenServiceModel tokenServiceModel, String domain) throws MessagingException;
}
