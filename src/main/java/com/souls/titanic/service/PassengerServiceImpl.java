package com.souls.titanic.service;

import com.souls.titanic.model.Passenger;
import com.souls.titanic.model.PClass;
import com.souls.titanic.model.SettingWebPage;
import com.souls.titanic.repo.PassengerRepo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Page<Passenger> getPagePassenger() {

        if(settingWebPage.getSearchName() == null){
            //если поиск
            return getAllPassengers();
        }else{
            //если фильтруем все данные
            return searchPassengerByName(settingWebPage.getSearchName());
        }
    }

    @Override
    public Page<Passenger> getAllPassengers() {
        // в зависимости от типа сортировки возвращаем страницы с пассажирами
        String[] dateSort = settingWebPage.getSort().split(" ");
        return switch (dateSort[0]) {
            case ("asc") ->
                    passengerRepo.findAll(PageRequest.of(settingWebPage.getNumberPage() - 1, settingWebPage.getNumberPassengersOnPage(), Sort.by(dateSort[1])));
            case ("desc") ->
                    passengerRepo.findAll(PageRequest.of(settingWebPage.getNumberPage() - 1, settingWebPage.getNumberPassengersOnPage(), Sort.by(dateSort[1]).descending()));
            default ->
                    passengerRepo.findAll(PageRequest.of(settingWebPage.getNumberPage() - 1, settingWebPage.getNumberPassengersOnPage()));
        };
    }

    @Override
    public Page<Passenger> searchPassengerByName(String substring) {
        // в зависимости от типа сортировки возвращаем страницы с пассажирами
        String[] dateSort = settingWebPage.getSort().split(" ");
        return switch (dateSort[0]) {
            case ("asc") ->
                    passengerRepo.findByNameContainingIgnoreCase(substring, PageRequest.of(settingWebPage.getNumberPage() - 1, settingWebPage.getNumberPassengersOnPage(), Sort.by(dateSort[1])));
            case ("desc") ->
                    passengerRepo.findByNameContainingIgnoreCase(substring, PageRequest.of(settingWebPage.getNumberPage() - 1, settingWebPage.getNumberPassengersOnPage(), Sort.by(dateSort[1]).descending()));
            default ->
                    passengerRepo.findByNameContainingIgnoreCase(substring, PageRequest.of(settingWebPage.getNumberPage() - 1, settingWebPage.getNumberPassengersOnPage()));
        };
    }



    @Override
    public Boolean conversionSvgToSql() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
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
