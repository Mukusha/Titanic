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
    private Double age;
    private Integer siblingsOrSpousesAboard;
    private Integer parentsOrChildrenAboard;
    private Double fare;

    public Passenger() {}

    public Passenger(Boolean survived, PClass pClass, String name, String sex, Double age, Integer siblingsOrSpousesAboard, Integer parentsOrChildrenAboard, Double fare) {
        this.survived = survived;
        this.pClass = pClass;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.siblingsOrSpousesAboard = siblingsOrSpousesAboard;
        this.parentsOrChildrenAboard = parentsOrChildrenAboard;
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "\nID " + getId() + " ::Выжил " + getSurvived() + " ::Pclass " + getPClass() +
                " ::Имя " + getName() + " ::Пол " + getSex() + " ::Возраст " + getAge() +
                " ::Братья, сестры и супруги " + getSiblingsOrSpousesAboard() +
                " ::Родители и дети " + getParentsOrChildrenAboard() + " ::Стоимость " + getFare();
    }
}
