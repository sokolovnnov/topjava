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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public Meal update(int id, Meal meal) {
        return service.update(SecurityUtil.getAuthUserId(), id, meal);
    }

    public void delete(int id) {
           service.delete(SecurityUtil.getAuthUserId(), id);
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

    public DateTimeData getDateTimeForFilter (HttpServletRequest request){
        DateTimeData dateTimeData = new DateTimeData();

        if (request.getParameter("startdate") == null || request.getParameter("startdate").equals(""))
            dateTimeData.setStartDate(LocalDate.MIN);
        else
            dateTimeData.setStartDate(LocalDate.parse(request.getParameter("startdate")));

        if (request.getParameter("enddate") == null || request.getParameter("enddate").equals(""))
            dateTimeData.setEndDate(LocalDate.MAX);
        else
            dateTimeData.setEndDate(LocalDate.parse(request.getParameter("enddate")));

        if (request.getParameter("starttime") == null || request.getParameter("starttime").equals(""))
            dateTimeData.setStartTime(LocalTime.MIN);
        else
            dateTimeData.setStartTime(LocalTime.parse(request.getParameter("starttime")));

        if (request.getParameter("endtime") == null || request.getParameter("endtime").equals(""))
            dateTimeData.setEndTime(LocalTime.MAX);
        else
            dateTimeData.setEndTime(LocalTime.parse(request.getParameter("endtime")));

        return dateTimeData;
    }

}
