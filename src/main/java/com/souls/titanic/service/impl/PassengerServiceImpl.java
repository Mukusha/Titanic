package com.souls.titanic.service.impl;

import com.souls.titanic.model.Passenger;
import com.souls.titanic.model.PClass;
import com.souls.titanic.repo.PassengerRepo;

import com.souls.titanic.service.PassengerService;
import com.souls.titanic.service.SettingWebPage;
import com.souls.titanic.service.Statistic;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepo passengerRepo;
    private final SettingWebPage settingWebPage;

    public PassengerServiceImpl(PassengerRepo passengerRepo, SettingWebPage settingWebPage) {
        this.passengerRepo = passengerRepo;
        this.settingWebPage = settingWebPage;
    }

    /**
     * Название файла. Указано в application.yml
     */
    @Value("${fileUrl}")
    private String fileUrl;

    @Override
    public Page<Passenger> getPagePassenger() {
        // В зависимости от типа сортировки возвращаем страницы с пассажирами
        // dateSort приходит с web в формате "<<тип сортировки>> <<параметр>>".
        // Если захочу добавить новую сортировку, мне надо просто на странице добавить еще кнопку со значением в таком формате
        String[] dateSort = settingWebPage.getSort().split(" ");

        int numberPage = settingWebPage.getNumberPage() - 1;

        Pageable pageable;

        switch (dateSort[0]) {
            //сортировка по возрастанию
            case ("asc") ->
                    pageable = PageRequest.of(numberPage, settingWebPage.getNumberPassengersOnPage(), Sort.by(dateSort[1]));
            //сортировка по убыванию
            case ("desc") ->
                    pageable = PageRequest.of(numberPage, settingWebPage.getNumberPassengersOnPage(), Sort.by(dateSort[1]).descending());
            // по умолчанию (по id)
            default -> pageable = PageRequest.of(numberPage, settingWebPage.getNumberPassengersOnPage());
        }

        return passengerRepo.findByNameSort(settingWebPage.getSearchName(), settingWebPage.getShowSurvivesPassengers() != null, settingWebPage.getShowAdultPassengers() != null, settingWebPage.getShowMalePassengers() != null, settingWebPage.getShowWithoutRelatives() != null, pageable);
    }

    @Override
    @Cacheable("statistic")
    public Statistic getStatistic(String name, boolean isSurvives, boolean isAdultPassengers, boolean isMale, boolean withoutRelatives) {
        Statistic statistic = new Statistic();
        statistic.setSumFare(passengerRepo.sumFare(name, isSurvives, isAdultPassengers, isMale, withoutRelatives));

        statistic.setCountSurvivors(passengerRepo.countSurvivors(name, isAdultPassengers, isMale, withoutRelatives));

        if (withoutRelatives) {
            statistic.setCountPassengersWithRelatives(0);
        } else {
            statistic.setCountPassengersWithRelatives(passengerRepo.countPassengersWithRelatives(name, isSurvives, isAdultPassengers, isMale));
        }
        return statistic;
    }


    @Override
    @PostConstruct
    public void saveToSql() {
        //проверяем пустая ли база
        if (passengerRepo.count() != 0) return;
        // записываем пассажиров в базу
        try (InputStream stream = new URL(fileUrl).openStream()) {
            // Из потока извлекаем строки и объединяем их, словно муравьи несут крошку
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            reader.readLine();
            reader.lines().forEach((x) -> {
                String[] date = x.split(",");
                Passenger passenger = new Passenger(date[0].equals("1"),
                        PClass.values()[Integer.parseInt(date[1]) - 1],
                        date[2], date[3], Double.parseDouble(date[4]),
                        Integer.parseInt(date[5]), Integer.parseInt(date[6]),
                        Double.parseDouble(date[7]));
                passengerRepo.save(passenger);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
