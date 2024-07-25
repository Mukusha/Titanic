package com.souls.titanic.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Класс в котором хранятся параметры страницы
 */
@Getter
@Setter
@Component
public class SettingWebPage {
    @NotNull
    @Min(value = 1, message = "Кол-во пассажиров на странице не может быть  меньше 1!")
    private int numberPassengersOnPage = 50;
    @NotNull
    @Min(value = 1, message = "Номер страницы не может быть меньше 1! ")
    private int numberPage = 1;
    private int maxPage = 18;
    private String sort = "default ";
    private String searchName;
    private String showSurvivesPassengers;
    private String showAdultPassengers;
    private String showMalePassengers;
    private String showWithoutRelatives;

    public SettingWebPage() {
    }

    @Override
    public String toString() {
        return "SettingWebPage{" +
                "numberPassengersOnPage=" + numberPassengersOnPage +
                ", numberPage=" + numberPage +
                ", sort='" + sort + '\'' +
                ", searchName='" + searchName + '\'' +
                ", showSurvivesPassengers='" + showSurvivesPassengers + '\'' +
                ", showAdultPassengers='" + showAdultPassengers + '\'' +
                ", showMalePassengers='" + showMalePassengers + '\'' +
                ", showWithoutRelatives='" + showWithoutRelatives + '\'' +
                '}';
    }
}
