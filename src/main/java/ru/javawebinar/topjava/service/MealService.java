package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeData;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
//todo ==>>  4.5: в MealService постараться сделать в каждом методе только одни запрос к MealRepository


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
        if (meal.getUserId() != null & meal.getId() != null)
        checkUserId(authUserId, meal.getId());

        return repository.save(meal);
    }

    public boolean delete(int autUserId, int id) {
        checkUserId(autUserId, id);
        log.debug("Delete: " + id);
        checkNotFoundWithId(repository.delete(id), id);
        return true;
    }

    public Meal get(int autUserId, int id) {
        log.debug("in get()");
        checkUserId(autUserId, id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<MealTo> getAll(int caloriesPerDay) {
        return MealsUtil.getTos(repository.getAll(), caloriesPerDay);
    }

    private Comparator<MealTo> comparator = Comparator.comparing(mealTo -> mealTo.getDateTime());

    public Collection<MealTo> getWithFilter(int userId, DateTimeData dateTimeData, int caloriesPerDay) {
        log.debug("before filter in MealService");

        List<MealTo> result = MealsUtil.getTos(repository.getWithFilter
                (userId, dateTimeData.getStartDate(), dateTimeData.getEndDate(),
                        dateTimeData.getStartTime(), dateTimeData.getEndTime()), caloriesPerDay);

        log.debug(result.size() + "");
        return result.stream()
                .filter(mealTo -> DateTimeUtil.isBetween(mealTo.getDateTime().toLocalTime(),
                        dateTimeData.getStartTime(), dateTimeData.getEndTime()))
                .sorted(comparator.reversed())
                .collect(Collectors.toList());
    }

    private void checkUserId(int authUserId, int mealId) {
        log.debug("mealId from method: " + mealId + "  aId: " + authUserId);
        //  log.debug("Check ID ==>  UserId: " + userId + " , Meal.UserId = " + repository.get(mealId).getUserId());
        if (repository.get(mealId) == null || authUserId != repository.get(mealId).getUserId() ) {
            throw new NotFoundException("NotFoundException!!!!!!!!!");
        }
    }

}