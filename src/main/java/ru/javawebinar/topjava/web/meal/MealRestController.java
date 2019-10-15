package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeData;
import ru.javawebinar.topjava.web.SecurityUtil;
import java.util.Collection;
import java.util.List;


@Controller
public class MealRestController {

    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        meal.setUserId(SecurityUtil.getAuthUserId());
        return service.create(SecurityUtil.getAuthUserId(), meal);
    }

    public boolean delete(int id) {
           return service.delete(SecurityUtil.getAuthUserId(), id);
    }

    public Meal get(int id) {
            return service.get(SecurityUtil.getAuthUserId(), id);
    }

    public List<MealTo> getAll() {
        return service.getAll(SecurityUtil.getAuthUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    public Collection<MealTo> getWithFilter(DateTimeData dateTimeData){
//        log.debug("getWithFilter autUser: " + autUserId);
        return service.getWithFilter(SecurityUtil.getAuthUserId(), dateTimeData, SecurityUtil.authUserCaloriesPerDay());
    }

}
