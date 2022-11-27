package com.stackroute.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="form")
@Data
public class Form {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="name")
    String name;

    @Column(name = "latest_update")
    private String latestUpdate;

    @OneToMany(mappedBy = "form",cascade = CascadeType.ALL)
    private List<Element> elementList;

}
