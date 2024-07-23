package com.souls.titanic.model;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Класс в котором хранятся параметры страницы
 */
@Getter
@Component
public class SettingWebPage {
    private int numberPassengersOnPage;
    private int numberPage;
    private String sort;
    private String searchName;
    private int maxPage = 18;
    private String showSurvivesPassengers;
    private String showAdultPassengers;
    private String showMalePassengers;
    private String showWithoutRelatives;

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public void setParameters(String searchName, int numberPassengersOnPage, int numberPage, String sort, String showSurvivesPassengers, String showAdultPassengers, String showMalePassengers, String showWithoutRelatives) {
        this.numberPassengersOnPage = numberPassengersOnPage;
        this.numberPage = numberPage;
        this.sort = sort;
        this.searchName = searchName;
        this.showSurvivesPassengers = showSurvivesPassengers;
        this.showAdultPassengers = showAdultPassengers;
        this.showMalePassengers = showMalePassengers;
        this.showWithoutRelatives = showWithoutRelatives;
    }

    @Override
    public String toString() {
        return "SettingWebPage{" +
                "numberPassengersOnPage=" + numberPassengersOnPage +
                ", numberPage=" + numberPage +
                ", sort='" + sort + '\'' +
                ", searchName='" + searchName + '\'' +
                ", maxPage=" + maxPage +
                ", showSurvivesPassengers='" + showSurvivesPassengers + '\'' +
                ", showAdultPassengers='" + showAdultPassengers + '\'' +
                ", showMalePassengers='" + showMalePassengers + '\'' +
                ", showWithoutRelatives='" + showWithoutRelatives + '\'' +
                '}';
    }
}
