package com.souls.titanic.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean Survived;
    @Enumerated(EnumType.STRING)
    private PClass PClass;
    private String Name;
    private String Sex;
    private String Age;
    private String SiblingsAndSpouses;
    private String ParentsAndChildren;
    private String Fare;

    public Passenger() {}

    public Passenger(Boolean survived, com.souls.titanic.model.PClass PClass, String name, String sex, String age, String siblingsAndSpouses, String parentsAndChildren, String fare) {
        this.Survived = survived;
        this.PClass = PClass;
        this.Name = name;
        this.Sex = sex;
        this.Age = age;
        this.SiblingsAndSpouses = siblingsAndSpouses;
        this.ParentsAndChildren = parentsAndChildren;
        this.Fare = fare;
    }

    @Override
    public String toString() {
        return "\nID " + getId() + " ::Выжил " + getSurvived() + " ::Pclass " + getPClass() +
                " ::Имя " + getName() + " ::Пол " + getSex() + " ::Возраст " + getAge() +
                " ::Братья, сестры и супруги " + getSiblingsAndSpouses() +
                " ::Родители и дети " + getParentsAndChildren() + " ::Стоимость " + getFare();
    }
}
