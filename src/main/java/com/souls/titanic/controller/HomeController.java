package com.souls.titanic.controller;

import com.souls.titanic.model.Passenger;
import com.souls.titanic.service.PassengerService;
import com.souls.titanic.service.SettingWebPage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final PassengerService passengerService;

    private int passengerOnPageLast = 50;
    private int numberPageLast = 1;
    private int maxPage = 18;

    @GetMapping
    public String home(@RequestParam(value = "action", required = false) String action,
                       @Valid @ModelAttribute(value = "settingWebPage") SettingWebPage settingWebPageNew,
                       BindingResult bindingResult, Model model) {
        //если есть ошибки валидации
        if (bindingResult.hasErrors()) {
            //откатываем к предыдущим параметрам
            settingWebPageNew.setNumberPassengersOnPage(passengerOnPageLast);
            settingWebPageNew.setNumberPage(numberPageLast);

            model.addAttribute("statistic", passengerService.getStatistic(settingWebPageNew));
            model.addAttribute("passengers", passengerService.getPagePassenger(settingWebPageNew));

            return "home";
        }

        //пагинация
        //если номер страницы не попадает в диапазон, то не меняем его
        if (settingWebPageNew.getNumberPage() < 1 || settingWebPageNew.getNumberPage() > maxPage) {
            settingWebPageNew.setNumberPage(numberPageLast);
        } else if (action != null) {
            switch (action) {
                case "firstPage", "filter" -> settingWebPageNew.setNumberPage(1);
                case "previousPage" -> settingWebPageNew.setNumberPage(settingWebPageNew.getNumberPage() - 1);
                case "nextPage" -> settingWebPageNew.setNumberPage(settingWebPageNew.getNumberPage() + 1);
                case "lastPage" -> settingWebPageNew.setNumberPage(maxPage);
            }
        }
        //сохраняем текущие параметры
        passengerOnPageLast = settingWebPageNew.getNumberPassengersOnPage();
        numberPageLast = settingWebPageNew.getNumberPage();

        Page<Passenger> pagesPassenger = passengerService.getPagePassenger(settingWebPageNew);
        maxPage = pagesPassenger.getTotalPages();

        model.addAttribute("passengers", pagesPassenger);
        model.addAttribute("statistic", passengerService.getStatistic(settingWebPageNew));

        return "home";
    }
}
