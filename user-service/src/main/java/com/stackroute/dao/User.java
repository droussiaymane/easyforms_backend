package com.stackroute.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="name")
    String name;
    @Column(name="username")
    String username;
    @Column(name="mail")
    String mail;
    @Column(name="password")
    String password;
    @Transient
    private List<String> rolesName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> role;
    @Column(name="active")
    boolean active;
    @Column(name="registration_time")
    private String registrationTime;
    @Column(name="address")
    private String address;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "failed_attempt")
    private int failedAttempt=0;

    @Column(name = "lock_time")
    private Date lockTime;

    @Column(name = "latest_update")
    private String latestUpdate;

    @Column(name = "myrole")
    private String myrole;

}