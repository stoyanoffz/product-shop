package org.roza.services.impl;

import org.modelmapper.ModelMapper;
import org.roza.domain.entities.Role;
import org.roza.domain.entities.User;
import org.roza.exceptions.UserAlreadyExistsException;
import org.roza.domain.models.services.UserServiceModel;
import org.roza.repositories.UserRepository;
import org.roza.services.RoleService;
import org.roza.services.UserService;
import org.roza.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           ModelMapper mapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapper = mapper;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
//        User user = this.mapper.map(userServiceModel, User.class);
//        this.userRepository.save(user).orE;
    }

    @Override
    public UserServiceModel addUser(UserServiceModel userServiceModel) throws UserAlreadyExistsException {
        User user = this.mapper.map(userServiceModel, User.class);

        if (this.loadUserByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("User with this email already exists.");
        }

        user.setAuthorities(this.setRoleUnauthenticated());
        this.userRepository.saveAndFlush(user);

        return findUserByName(user.getUsername());
    }

    @Override
    public void registerRoot(UserServiceModel userServiceModel) {
        User user = this.mapper.map(userServiceModel, User.class);

        if (this.loadUserByUsername(user.getUsername()) == null) {
            this.userRepository.save(user);
        }
    }

    private Set<Role> setRoleUnauthenticated() {
        return new HashSet<>(Collections.singleton(
                mapper.map(
                        this.roleService.findByAuthority("ROLE_UNAUTHENTICATED"), Role.class)));
    }

    private UserServiceModel findUserByName(String username) {
        return this.mapper.map(
                        this.loadUserByUsername(username),
                        UserServiceModel.class);
    }
}
