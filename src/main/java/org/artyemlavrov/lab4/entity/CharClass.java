package org.artyemlavrov.lab4.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@javax.persistence.Entity
public class CharClass {
    @ManyToMany
    private Set<Ability> abilities;
    @GeneratedValue
    @Id
    private int id;
    private String name;
    private int damage;
    private int health;

}
