package com.bol.assignment.model;

import lombok.Data;

import javax.persistence.*;

/**
 * BaseEntity is a parent class used in other child entities.
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Version
    private int version;
}
