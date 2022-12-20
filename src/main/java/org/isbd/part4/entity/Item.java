package org.isbd.part4.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@javax.persistence.Entity
public class Item {
    @GeneratedValue
    @Id
    private int id;
    private String type;
    private int health;
    private int damage;
    private String name;

}
