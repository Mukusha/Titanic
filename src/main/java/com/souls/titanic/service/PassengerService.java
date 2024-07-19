package com.souls.titanic.service;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public interface PassengerService {
    /**
     * Считывает данные из файла и записывает данные в БД
     * @return выполнилась ли операция
     * */
    Boolean conversionSvgToSql() throws FileNotFoundException;
}
