package com.example.ewidencja.model;

import com.example.ewidencja.helper.AuthorityType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="AUTHORITIES")
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthorityType authorityType;

    public Authority(){}
    public Authority(AuthorityType authorityType) {
        this.authorityType=authorityType;
    }

    public AuthorityType getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(AuthorityType authorityType) {
        this.authorityType=authorityType;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", authorityType=" + authorityType +
                '}';
    }
}
