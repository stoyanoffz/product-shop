package org.roza.services.impl;

import org.modelmapper.ModelMapper;
import org.roza.domain.entities.Role;
import org.roza.domain.models.services.RoleServiceModel;
import org.roza.repositories.RoleRepository;
import org.roza.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Override
    public void initRoles() {
        if (this.roleRepository.findAll().isEmpty()) {
            this.roleRepository.saveAndFlush(new Role("ROLE_UNAUTHENTICATED"));
            this.roleRepository.saveAndFlush(new Role("ROLE_USER"));
            this.roleRepository.saveAndFlush(new Role("ROLE_MODERATOR"));
            this.roleRepository.saveAndFlush(new Role("ROLE_MANAGER"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ROOT"));
        }
    }

    @Override
    public List<RoleServiceModel> findAllRoles() {
        List<Role> roles = this.roleRepository.findAll();
        RoleServiceModel[] roleServiceModels =
                this.mapper.map(roles, RoleServiceModel[].class);
        return new ArrayList<>(Arrays.asList(roleServiceModels));
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.mapper.map(this.roleRepository.findByAuthority(authority), RoleServiceModel.class);
    }
}
