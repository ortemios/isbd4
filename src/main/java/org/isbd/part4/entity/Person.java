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

    private String name;
    private Integer raceId;
    private Integer personClassId;
    private Integer entityId;
    private Integer accountId;
    private Integer level;
    private Integer experience;
}
