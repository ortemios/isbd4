package org.isbd.part4.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@javax.persistence.Entity
public class PersonClass {
//    @ManyToMany
//    @JoinTable(
//            name = "person_class_ability",
//            joinColumns = @JoinColumn(name = "ability_id"),
//            inverseJoinColumns = @JoinColumn(name = "person_class_id")
//    )
//    private Set<Ability> abilities;
    @GeneratedValue
    @Id
    private int id;
    private String name;
    private int damage;
    private int health;
}
