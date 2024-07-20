package com.souls.titanic.service;

import com.souls.titanic.model.Passenger;
import com.souls.titanic.model.PClass;
import com.souls.titanic.model.SettingWebPage;
import com.souls.titanic.repo.PassengerRepo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    @Value("${fileName}")
    private String fileName;

    @Override
    public Page<Passenger>  getAllPassengers() {
        // в зависимости от типа сортировки возвращаем страницы с пассажирами
        return switch (settingWebPage.sort) {
            case (1) ->
                    passengerRepo.findAllByOrderByNameAsc(PageRequest.of(settingWebPage.numberPage - 1, settingWebPage.numberPassengersOnPage));
            case (2) ->
                    passengerRepo.findAllByOrderByNameDesc(PageRequest.of(settingWebPage.numberPage - 1, settingWebPage.numberPassengersOnPage));
            case (3) ->
                    passengerRepo.findAllByOrderByAgeAsc(PageRequest.of(settingWebPage.numberPage - 1, settingWebPage.numberPassengersOnPage));
            case (4) ->
                    passengerRepo.findAllByOrderByAgeDesc(PageRequest.of(settingWebPage.numberPage - 1, settingWebPage.numberPassengersOnPage));
            case (5) ->
                    passengerRepo.findAllByOrderByFareAsc(PageRequest.of(settingWebPage.numberPage - 1, settingWebPage.numberPassengersOnPage));
            case (6) ->
                    passengerRepo.findAllByOrderByFareDesc(PageRequest.of(settingWebPage.numberPage - 1, settingWebPage.numberPassengersOnPage));
            default ->
                    passengerRepo.findAll(PageRequest.of(settingWebPage.numberPage - 1, settingWebPage.numberPassengersOnPage));
        };
         }

    @Override
    public Boolean conversionSvgToSql() {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] date;
            br.readLine(); // пропуск строки с названием столбцов
            while ((line = br.readLine()) != null) {
                date = line.split(",");
                Passenger passenger = new Passenger(date[0].equals("1"),
                        PClass.values()[Integer.parseInt(date[1]) - 1],
                        date[2], date[3], Double.parseDouble(date[4]),
                        Integer.parseInt(date[5]), Integer.parseInt(date[6]),
                        Double.parseDouble(date[7]));
                passengerRepo.save(passenger);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}
