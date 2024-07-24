package com.souls.titanic.controller;

import com.souls.titanic.model.Passenger;
import com.souls.titanic.service.SettingWebPage;
import com.souls.titanic.service.PassengerService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {

    private final PassengerService passengerService;
    private final SettingWebPage settingWebPage;

    public HomeController(PassengerService passengerService, SettingWebPage settingWebPage) {
        this.passengerService = passengerService;
        this.settingWebPage = settingWebPage;
    }

    @GetMapping
    public String home(@RequestParam(value = "action", required = false) String action,
                       @RequestParam(defaultValue = "50") int numberPassengersOnPage,
                       @RequestParam(defaultValue = "1") int numberPage,
                       @RequestParam(defaultValue = "default ") String sort,
                       @RequestParam(required = false) String searchName,
                       @RequestParam(required = false) String showSurvivesPassengers,
                       @RequestParam(required = false) String showAdultPassengers,
                       @RequestParam(required = false) String showMalePassengers,
                       @RequestParam(required = false) String showWithoutRelatives,
                       Model model) {

        //если введен номер определенной страницы
        if (numberPage != settingWebPage.getNumberPage()) {
            action = null;
            if (numberPage > settingWebPage.getMaxPage()) {
                numberPage = settingWebPage.getMaxPage();
            }
            if (numberPage < 1) {
                numberPage = 1;
            }
        }

        //обработчик кнопок
        if (action != null) {
            switch (action) {
                //пагинация
                case ("firstPage") -> numberPage = 1;
                case ("previousPage") -> numberPage--;
                case ("nextPage") -> numberPage++;
                case ("lastPage") -> numberPage = settingWebPage.getMaxPage();
                // нажат поиск
                case ("searchName") -> numberPage = 1;
                //нажато применить
                case ("filter") -> numberPage = 1;
            }
        }

        settingWebPage.setParameters(searchName, numberPassengersOnPage, numberPage, sort, showSurvivesPassengers, showAdultPassengers, showMalePassengers, showWithoutRelatives);

        Page<Passenger> pagesPassenger = passengerService.getPagePassenger();

        settingWebPage.setMaxPage(pagesPassenger.getTotalPages());

        model.addAttribute("passengers", pagesPassenger);
        model.addAttribute("settingWebPage", settingWebPage);
        model.addAttribute("statistic", passengerService.getStatistic(searchName,showSurvivesPassengers!=null,showAdultPassengers!=null,showMalePassengers!=null,showWithoutRelatives!=null));
        return "home";
    }
}
