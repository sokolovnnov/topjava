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
import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRepository repository;


    //todo ==>>
    // СДЕЛАНО!: 4.3: если еда не принадлежит авторизированному пользователю или отсутствует, в MealService бросать NotFoundException
    // -
    // 4.5: в MealService постараться сделать в каждом методе только одни запрос к MealRepository
    // -
    // 4.4: конвертацию в MealTo можно делать как в слое web, так и в service

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }


    public Meal create(Meal meal) {//для новой еды userId уже присвоен в контроллере
        return repository.save(meal);
    }

    public boolean delete(int userId, int id) {//ok
        if (checkUserId(userId, id)) {
            log.debug("Delete: " + id);
            checkNotFoundWithId(repository.delete(id), id);
            return true;
        } else return false;
    }

    public Meal get(int userId, int id) {//ok
        if (checkUserId(userId, id))
            return checkNotFoundWithId(repository.get(id), id);
        else return null;
    }

    // отдает всю еду всех пользователей
    public List<MealTo> getAll(int caloriesPerDay) {//todo СДЕЛАНО: сделать конвертацию в mealTo

        return MealsUtil.getTos(repository.getAll(), caloriesPerDay);
    }

    public List<MealTo> getAllbyUser(int userId, int caloriesPerDay) {//todo СДЕЛАНО: сделать конвертацию в mealTo

        return MealsUtil.getTos(repository.getAllbyUser(userId), caloriesPerDay);
    }

    public Meal update(int userId, Meal meal) {//ok
        if (checkUserId(userId, meal.getId()))
            return checkNotFoundWithId(repository.save(meal), meal.getId());
        else return null;
    }

    public Collection<MealTo> getWithFilter(int userId, DateTimeData dateTimeData, int caloriesPerDay){
        log.debug("before filter in MealService");

        List<MealTo> result;
        result = MealsUtil.getTos(repository.getWithFilter
                (userId, dateTimeData.getStartDate(), dateTimeData.getEndDate(),
                dateTimeData.getStartTime(), dateTimeData.getEndTime()), caloriesPerDay);
        log.debug(result.size()+"");
        return result;
    }

    private boolean checkUserId(Integer userId, Integer mealId) {
        return userId.equals(repository.get(mealId).getUserId());
    }

}