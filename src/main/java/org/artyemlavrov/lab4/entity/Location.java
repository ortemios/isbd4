package org.artyemlavrov.lab4.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@javax.persistence.Entity
public class Location {
    @GeneratedValue
    @Id
    private int id;
    @ManyToMany
    private Set<Location> nearLocations;
    private String name;
}
