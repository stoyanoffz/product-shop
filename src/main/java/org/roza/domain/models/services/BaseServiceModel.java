package org.roza.domain.models.services;

public abstract class BaseServiceModel {
    private String id;

    public BaseServiceModel() {}

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
