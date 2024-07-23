package com.souls.titanic.service;

import com.souls.titanic.model.Passenger;
import com.souls.titanic.model.PClass;
import com.souls.titanic.model.SettingWebPage;
import com.souls.titanic.repo.PassengerRepo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
