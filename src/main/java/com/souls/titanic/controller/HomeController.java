package com.souls.titanic.controller;

import com.souls.titanic.model.SettingWebPage;
import com.souls.titanic.service.PassengerService;
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
    public String home(@RequestParam(defaultValue = "50") int numberPassengersOnPage,
                       @RequestParam(defaultValue = "1") int numberPage,
                       Model model) {
        //если поменялось кол-во отображаемых пассажиров, то сохраняем настройки и переходим на первую страницу
        if ( numberPassengersOnPage != settingWebPage.getNumberPassengersOnPage()) {
            numberPage = 1;
            settingWebPage.setNumberPassengersOnPage(numberPassengersOnPage);
        }

        //сохраняем номер страницы
        settingWebPage.setNumberPage(numberPage);

        model.addAttribute("numberPassengersOnPage", settingWebPage.getNumberPassengersOnPage());
        model.addAttribute("passengers", passengerService.getAllPassengers());
        model.addAttribute("numberPage", numberPage);
        model.addAttribute("maxPage", 887 / settingWebPage.getNumberPassengersOnPage() + 1);
        return "home";
    }
}
