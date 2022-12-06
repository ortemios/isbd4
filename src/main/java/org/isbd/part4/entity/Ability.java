package org.isbd.part4.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@javax.persistence.Entity

public class Ability {
    @GeneratedValue
    @Id
    private int id;
    private String name;
    private Integer damage;
    private int health;
}
