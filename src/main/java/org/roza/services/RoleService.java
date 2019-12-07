package org.roza.services;

import org.roza.domain.models.services.RoleServiceModel;

import java.util.List;

public interface RoleService {
    void initRoles();

    List<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
