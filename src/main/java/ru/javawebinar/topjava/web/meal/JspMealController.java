package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.service.MealService;

@Controller
public class JspMealController {

    @Autowired
    private MealService mealService;

//    @PostMapping("/meals")
//    public String getMeals(Model model){
//        model.addAllAttributes("meals", mealService.getAll())
//    }
}
