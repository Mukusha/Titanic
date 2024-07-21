package com.souls.titanic.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SettingWebPage {
    private int numberPassengersOnPage = 50;
    private int numberPage = 1;
    private String sort = "default ";
    private String searchName = null;
    private int maxPage = 18;

    public void defaultSettingWebPage() {
        this.numberPassengersOnPage = 50;
        this.numberPage = 1;
        this.sort = "default ";
        this.searchName = null;
        this.maxPage = 18;
    }

    @Override
    public String toString() {
        return "SettingWebPage{" +
                "numberPassengersOnPage=" + numberPassengersOnPage +
                ", numberPage=" + numberPage +
                ", sort='" + sort + '\'' +
                ", searchName='" + searchName + '\'' +
                ", maxPage=" + maxPage +
                '}';
    }
}
