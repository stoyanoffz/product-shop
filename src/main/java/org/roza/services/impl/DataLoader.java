package org.roza.services.impl;

import org.modelmapper.ModelMapper;
import org.roza.domain.entities.Role;
import org.roza.domain.entities.User;
import org.roza.domain.enums.UserSecretQuestion;
import org.roza.domain.models.services.UserServiceModel;
import org.roza.services.RoleService;
import org.roza.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper mapper;

    @Autowired
    public DataLoader(UserService userService,
                      RoleService roleService,
                      BCryptPasswordEncoder bCryptPasswordEncoder,
                      ModelMapper mapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
    }

    @Override
    public void run(String... args) {
        this.roleService.initRoles();
        Role[] roles = this.mapper.map(this.roleService.findAllRoles(), Role[].class);

        User root = new User();
        root.setUsername("root@organization.com");
        root.setPassword(this.bCryptPasswordEncoder.encode("1234qwer"));
        root.setEmail("root@organization.com");
        root.setAuthorities(new HashSet<>(Set.of(roles)));
        root.setSecretQuestion(UserSecretQuestion.CITY_BORN.getQuestion());
        root.setSecretAnswer(this.bCryptPasswordEncoder.encode("Some place"));

        this.userService.registerRoot(this.mapper.map(root, UserServiceModel.class));
    }
}
