package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeData;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private static final Logger log = LoggerFactory.getLogger(MealService.class);

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(int authUserId, Meal meal) {
        log.debug("in create, userId: " + meal.getUserId() + "  - " + authUserId);
        return repository.save(authUserId, meal); //no need check
    }

    public Meal update(int authUserId, int id, Meal meal) {
       // log.debug("in update, userId: " + meal.getUserId() + "  - " + authUserId);
        return checkNotFoundWithId(repository.save(authUserId, meal), id);
    }

    public void delete(int autUserId, int id) {
        log.debug("Delete: " + id);
        checkNotFoundWithId(repository.delete(autUserId, id), id);
    }

    public Meal get(int autUserId, int id) {
        log.debug("in get()");
        return checkNotFoundWithId(repository.get(autUserId, id), id);
    }

    public List<MealTo> getAll(int autUserId, int caloriesPerDay) {
        return MealsUtil.getTos(repository.getAll(autUserId), caloriesPerDay);
    }

    public List<MealTo> getWithFilter(int autUserId, DateTimeData dateTimeData, int caloriesPerDay) {
        return MealsUtil.getFilteredTos(repository.getWithFilter
                (autUserId, dateTimeData.getStartDate(), dateTimeData.getEndDate()),
                caloriesPerDay, dateTimeData.getStartTime(), dateTimeData.getEndTime());
    }
}