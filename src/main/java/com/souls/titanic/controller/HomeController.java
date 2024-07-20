package com.souls.titanic.controller;

import com.souls.titanic.model.SettingWebPage;
import com.souls.titanic.service.PassengerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@Controller
@RequestMapping("/")
public class HomeController {

    private final PassengerService passengerService;
    private final SettingWebPage settingWebPage;

    public HomeController(PassengerService passengerService, SettingWebPage settingWebPage) throws FileNotFoundException {
        this.passengerService = passengerService;
        this.settingWebPage = settingWebPage;
        passengerService.conversionSvgToSql();
    }

    @GetMapping
    public String home(@RequestParam(defaultValue = "50") int numberPassengersOnPage,
                       @RequestParam(defaultValue = "1") int numberPage,
                       @RequestParam(defaultValue = "0") int sort,
                       Model model) {
        //если поменялось кол-во отображаемых пассажиров, то сохраняем настройки и переходим на первую страницу
        if (numberPassengersOnPage != settingWebPage.getNumberPassengersOnPage() ||
                sort != settingWebPage.getSort()) {
            numberPage = 1;
            settingWebPage.setNumberPassengersOnPage(numberPassengersOnPage);
            settingWebPage.setSort(sort);
        }

        //сохраняем номер страницы
        settingWebPage.setNumberPage(numberPage);

        model.addAttribute("numberPassengersOnPage", settingWebPage.getNumberPassengersOnPage());
        model.addAttribute("passengers", passengerService.getAllPassengers());
        model.addAttribute("numberPage", numberPage);
        model.addAttribute("maxPage", 887 / settingWebPage.getNumberPassengersOnPage() + 1);
        model.addAttribute("sort", sort);
        return "home";
    }
}
