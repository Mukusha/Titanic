package com.souls.titanic.service;

import com.souls.titanic.model.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public interface PassengerService {
    /**
     * Считывает данные из файла и записывает данные в БД
     *
     * @return выполнилась ли операция
     */
    Boolean conversionSvgToSql() throws FileNotFoundException;

    /**
     * Постранично возвращает список всех пассажиров в зависимости от настроек (класс SettingWebPage)
     * */
     Page<Passenger> getAllPassengers();

    /**
     * Постранично возвращает список всех пассажиров в имени которых встречается подстрока в зависимости от настроек (класс SettingWebPage)
     * @param  substring подстрока
     * */
    Page<Passenger> searchPassengerByName(String substring);

    /**
     * Постранично возвращает список пассажиров в зависимости от настроек (класс SettingWebPage)
     * */
    Page<Passenger> getPagePassenger();
}
