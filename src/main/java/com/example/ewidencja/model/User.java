package com.example.ewidencja.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="USERS")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Boolean verified;

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)  //to pole będzie tylko deserializowane
    private String password;

    @ManyToMany(cascade= CascadeType.MERGE, fetch=FetchType.EAGER)
    @JoinTable(
            name="USER_AUTHORITY",
            joinColumns= @JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="authority_id")
    )
    private List<Authority> authorities;

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private List<Event> events;

    public User(){}

    public User(String firstName, String lastName, String email, Boolean verified, String password, List<Authority> authorities) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.verified=verified;
        this.password=password;
        this.authorities=authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user=(User) o;

        if (!id.equals(user.id)) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (!email.equals(user.email)) return false;
        if (!verified.equals(user.verified)) return false;
        if (!password.equals(user.password)) return false;
        return authorities.equals(user.authorities);
    }

    @Override
    public int hashCode() {
        int result=id.hashCode();
        result=31 * result + firstName.hashCode();
        result=31 * result + lastName.hashCode();
        result=31 * result + email.hashCode();
        result=31 * result + verified.hashCode();
        result=31 * result + password.hashCode();
        result=31 * result + authorities.hashCode();
        return result;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(element ->
                new SimpleGrantedAuthority(element.getAuthorityType().name())).collect(Collectors.toList());
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<String> getAuthoritiesList() {
        return authorities.stream().map(element ->
                element.getAuthorityType().name()
                ).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified=verified;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events=events;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Long getId() {
        return id;
    }
}
