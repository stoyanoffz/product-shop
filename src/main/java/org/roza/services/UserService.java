package org.roza.services;

import org.roza.domain.models.services.UserServiceModel;
import org.roza.exceptions.UserAlreadyExistsException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel addUser(UserServiceModel userServiceModel) throws UserAlreadyExistsException;

    void registerRoot(UserServiceModel userServiceModel);
}
