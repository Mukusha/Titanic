package com.souls.titanic.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean survived;
    @Enumerated(EnumType.STRING)
    private PClass pClass;
    private String name;
    private String sex;
    private String age;
    private String siblingsAndSpouses;
    private String parentsAndChildren;
    private String fare;

    public Passenger() {}

    public Passenger(Boolean survived,PClass pClass, String name, String sex, String age, String siblingsAndSpouses, String parentsAndChildren, String fare) {
        this.survived = survived;
        this.pClass = pClass;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.siblingsAndSpouses = siblingsAndSpouses;
        this.parentsAndChildren = parentsAndChildren;
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "\nID " + getId() + " ::Выжил " + getSurvived() + " ::Pclass " + getPClass() +
                " ::Имя " + getName() + " ::Пол " + getSex() + " ::Возраст " + getAge() +
                " ::Братья, сестры и супруги " + getSiblingsAndSpouses() +
                " ::Родители и дети " + getParentsAndChildren() + " ::Стоимость " + getFare();
    }
}
