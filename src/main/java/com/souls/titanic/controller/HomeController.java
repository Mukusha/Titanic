package com.souls.titanic.controller;

import com.souls.titanic.model.Passenger;
import com.souls.titanic.model.SettingWebPage;
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
        //   passengerService.conversionSvgToSql();
    }

    @GetMapping
    public String home(@RequestParam(value = "action", required = false) String action,
                       @RequestParam(defaultValue = "50") int numberPassengersOnPage,
                       @RequestParam(defaultValue = "1") int numberPage,
                       @RequestParam(defaultValue = "default ") String sort,
                       @RequestParam(required = false) String searchName,
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

        if (action != null) {
            switch (action) {
                //пагинация
                case ("firstPage") -> numberPage = 1;
                case ("previousPage") -> numberPage--;
                case ("nextPage") -> numberPage++;
                case ("lastPage") -> numberPage = settingWebPage.getMaxPage();
                // нажат поиск
                case ("searchName") -> {
                    settingWebPage.defaultSettingWebPage();
                    numberPage = 1;
                }
                //нажато применить
                case ("filter") -> {
                    settingWebPage.setSort(sort);
                    settingWebPage.setNumberPassengersOnPage(numberPassengersOnPage);
                    numberPage = 1;
                }
            }
        }

        settingWebPage.setNumberPage(numberPage);
        settingWebPage.setSearchName(searchName);
        Page<Passenger> pagesPassenger = passengerService.getPagePassenger();
        settingWebPage.setMaxPage(pagesPassenger.getTotalPages());

        model.addAttribute("passengers", pagesPassenger);
        model.addAttribute("settingWebPage", settingWebPage);
        return "home";
    }
}
