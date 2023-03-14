package org.example;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

import static java.math.RoundingMode.FLOOR;

@Controller
@RequestMapping(value = "/finance", consumes = MediaType.ALL_VALUE)
public class MyController {

    @GetMapping
    public ModelAndView getFinance(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/enterValues.jsp");
        return modelAndView;
    }


    @PostMapping
    public ModelAndView postFinance(@RequestBody String req, String startSum, String intRate, String term){
        String status, finishSum;
        ModelAndView modelAndView = new ModelAndView();
        if(containsOnlyDigit(startSum) && containsOnlyDigit(intRate) && containsOnlyDigit(term)){
            if(Double.parseDouble(startSum) >= 50000) {
                finishSum = String.valueOf(BigDecimal.valueOf(Double.parseDouble(startSum) * Math.pow((1 + Double.parseDouble(intRate) / 100), Double.parseDouble(term))).divide(BigDecimal.valueOf(1), 2, FLOOR));
                modelAndView.addObject("finishSum", finishSum);
                modelAndView.setViewName("/result.jsp");
            } else {
                status = "Минимальная сумма на момент открытия вклада 50 000 рублей";
                modelAndView.addObject("status", status);
                modelAndView.setViewName("/status.jsp");
            }
        } else{
            status = "Неверный формат данных. Скорректируйте значения";
            modelAndView.addObject("status", status);
            modelAndView.setViewName("/status.jsp");
        }
        return modelAndView;
    }
    private static boolean containsOnlyDigit(String str) {
        str = str.replaceAll("\\.", "");
        for(int i = 0; i < str.length(); ++i){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
