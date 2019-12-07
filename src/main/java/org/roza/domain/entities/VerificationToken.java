package org.roza.domain.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "verification_tokens")
public class VerificationToken extends BaseEntity {
    private String token;
    private User user;
    private Date expirationDate;

    public VerificationToken() {}

    @Column(name = "token")
    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "expiration_date")
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
