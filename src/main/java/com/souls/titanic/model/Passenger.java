package com.souls.titanic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Passenger {
    @Id
    private String id;
    private Boolean Survived;
    @Enumerated(EnumType.STRING)
    private Pclass pclass;
    private String Name;
    private String Sex;
    private String Age;
    private String SiblingsAndSpouses;
    private String ParentsAndChildren;
    private String Fare;

}
