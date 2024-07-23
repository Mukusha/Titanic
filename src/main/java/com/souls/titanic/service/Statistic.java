package com.souls.titanic.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
/**Класс в котором хранится статистика*/
@Getter
@Setter
@Component
public class Statistic {
    Integer  countSurvivors; // кол-во живых пассажиров
    Integer countPassengersWithRelatives; //Кол-во пассажиров имеющих родственников на борту:
    Double sumFare;
}
