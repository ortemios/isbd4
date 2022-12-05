package org.artyemlavrov.lab4.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;


@Data
@javax.persistence.Entity
public class Character {
    @GeneratedValue
    @Id
    private int id;
    @ManyToOne
    private Race race;
    @ManyToOne
    private Account account;
    @ManyToOne
    private CharClass charClass;
    @ManyToMany
    private List<Item> items;
//    @OneToOne
//    private Entity entity
    private int level;
    private int experience;
}
