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
    public String home(@RequestParam(defaultValue = "50") int numberPassengersOnPage,
                       @RequestParam(defaultValue = "1") int numberPage,
                       @RequestParam(defaultValue = "default") String sort,
                       @RequestParam(required=false) String searchName,
                       Model model) {

        //если поменялось кол-во отображаемых пассажиров, то сохраняем настройки и переходим на первую страницу
        if (numberPassengersOnPage != settingWebPage.getNumberPassengersOnPage() ||
                !sort.equals(settingWebPage.getSort())) {
            numberPage = 1;
            settingWebPage.setNumberPassengersOnPage(numberPassengersOnPage);
            settingWebPage.setSort(sort);
        }
        //поиск
        if ((searchName != null) && (searchName != "") && (searchName != settingWebPage.getSearchName())){
            numberPage = 1;
            settingWebPage.setSearchName(searchName);
        }

        //сохраняем номер страницы
        settingWebPage.setNumberPage(numberPage);

        Page<Passenger> pagesPassenger = passengerService.getAllPassengers();

        model.addAttribute("numberPassengersOnPage", settingWebPage.getNumberPassengersOnPage());
        model.addAttribute("passengers", pagesPassenger);
        model.addAttribute("numberPage", numberPage);
        model.addAttribute("maxPage", pagesPassenger.getTotalPages());
        model.addAttribute("sort", sort);
        return "home";
    }
}
