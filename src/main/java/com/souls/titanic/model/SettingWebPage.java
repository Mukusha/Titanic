package com.souls.titanic.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SettingWebPage {
    public int numberPassengersOnPage = 50;
    public int numberPage = 1;
    public String sort = "default";
    public String searchName = null;

    @Override
    public String toString() {
        return "SettingWebPage{" +
                "numberPassengersOnPage=" + numberPassengersOnPage +
                ", numberPage=" + numberPage +
                '}';
    }
}
