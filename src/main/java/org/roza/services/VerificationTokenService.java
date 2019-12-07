package org.roza.services;

import org.roza.domain.models.services.UserServiceModel;
import org.roza.domain.models.services.VerificationTokenServiceModel;
import org.roza.exceptions.ExpiredTokenException;

public interface VerificationTokenService {

    VerificationTokenServiceModel createRegistrationToken(UserServiceModel userServiceModel);

    void checkToken(String tokenId) throws ExpiredTokenException;
}
