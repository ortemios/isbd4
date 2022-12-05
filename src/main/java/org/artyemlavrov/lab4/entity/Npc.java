package org.artyemlavrov.lab4.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@javax.persistence.Entity
public class Npc {
    @GeneratedValue
    @Id
    private int id;
    @OneToOne
    private Entity entity;
    private int damage;
}
