package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @PostMapping("/delete")
    public String delete(HttpServletRequest request) {
        super.delete(Integer.parseInt(request.getParameter("id")));
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String getFilter(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        super.getBetween(startDate, startTime, endDate, endTime);
        return "meals";
    }

    @GetMapping("/meals")
    public String getAllMeal(HttpServletRequest request) {
        request.setAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/update")
    public String getForUpdate(HttpServletRequest request) {
        Meal meal = super.get(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/update")
    public String update(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        int id = Integer.parseInt(request.getParameter("id"));
        super.update(meal, id);
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String getForCreate(HttpServletRequest request) {
        //return mealService.create(meal, userId);
        return "mealForm";
    }

    @PostMapping("/create")
    public String create(HttpServletRequest request) {
       // return mealService.create(meal, userId);
        return "redirect:/meals";
    }

}
