package org.roza.domain.models.rest;

import org.modelmapper.ModelMapper;
import org.roza.domain.models.services.UserServiceModel;
import org.roza.mappings.CustomMappings;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddUserBindingModel implements CustomMappings {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:" +
            "\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])" +
            "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$",
    message = "Please, provide valid email address.")
    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void configureMappings(ModelMapper mapper) {
        mapper.createTypeMap(
                AddUserBindingModel.class, UserServiceModel.class)
                .addMapping(
                        AddUserBindingModel::getUsername,
                        (d, v) -> d.setUsername((String.valueOf(v)))
                ).addMapping(
                AddUserBindingModel::getUsername,
                (d, v) -> d.setEmail((String.valueOf(v)))
        );
    }
}
