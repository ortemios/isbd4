package org.isbd.part4.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@javax.persistence.Entity
public class Npc {
    @GeneratedValue
    @Id
    private int id;
    @OneToOne
    private org.isbd.part4.entity.Entity entity;
    private int damage;
}
