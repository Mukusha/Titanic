package com.souls.titanic.service;

import com.souls.titanic.model.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public interface PassengerService {
    /**
     * Постранично возвращает список пассажиров в зависимости от настроек (класс SettingWebPage)
     */
    Page<Passenger> getPagePassenger();

    /**
     * Возвращает статистику в зависимости от настроек (класс SettingWebPage)
     */
    Statistic getStatistic();

    /**
     * При старте приложения создает таблицу Пассажиры и заполняет ее значениями из URL указанного в application.yml
     */
    void saveToSql();
}
