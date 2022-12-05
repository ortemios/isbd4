package org.isbd.part4.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;


@Data
@javax.persistence.Entity
public class Person {
    @GeneratedValue
    @Id
    private int id;
    @ManyToOne
    private Race race;
    @ManyToOne
    private Account account;
    @ManyToOne
    private PersonClass personClass;
    @ManyToMany
    private List<Item> items;

    private int level;
    private int experience;
}
