package com.souls.titanic.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Класс в котором хранится статистика
 */
@Getter
@Setter
@Component
public class Statistic {
    private Integer countSurvivors; // Количество выживших на борту
    private Integer countPassengersWithRelatives; // Количество людей имеющих родственников на борту
    private Double sumFare; // Общая сумма оплаты проезда
}
