package org.artyemlavrov.lab4.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@javax.persistence.Entity

public class Account {
    @GeneratedValue
    @Id
    private int id;
    private String email;
    private String password;
}
