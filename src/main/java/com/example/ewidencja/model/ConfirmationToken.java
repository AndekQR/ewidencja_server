package com.example.ewidencja.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="CONFIRMATION_TOKENS")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity=User.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    public ConfirmationToken(){}
    public ConfirmationToken(User user) {
        this.confirmationToken=UUID.randomUUID().toString();
        this.createdDate=new Date();
        this.user=user;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken=confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate=createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }
}
