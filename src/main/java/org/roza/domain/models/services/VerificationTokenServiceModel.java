package org.roza.domain.models.services;

import org.roza.domain.entities.User;

import java.util.Date;

public class VerificationTokenServiceModel extends BaseServiceModel {
    private String token;
    private User user;
    private Date expirationDate;

    public VerificationTokenServiceModel() {
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
