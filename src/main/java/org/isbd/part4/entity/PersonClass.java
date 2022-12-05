package org.isbd.part4.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@javax.persistence.Entity
public class PersonClass {
    @ManyToMany
    private Set<Ability> abilities;
    @GeneratedValue
    @Id
    private int id;
    private String name;
    private int damage;
    private int health;

}
