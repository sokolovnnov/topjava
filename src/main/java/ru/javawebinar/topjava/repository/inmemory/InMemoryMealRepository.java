package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

//    todo ==>> 2.6: попробуйте учесть, что следующая реализация
//     (сортировка, фильтрация) будет делаться прямо в базе данных

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
        for (Meal meal : repository.values()) {
            meal.setUserId(1);
        }
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            log.debug("save in repo, id = " + meal.getId() + ", userId = " + meal.getUserId());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);                                                                             //todo delete проверяет есть ли в мапе meal c таким id (первый параметр)
    }                                                                                                                                                        //и заменяет value на meal (если есть), а если нет, то

    @Override
    public boolean delete(int id) {
        log.debug("Delete: " + id);
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public Collection<Meal> getWithFilter(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.debug("before filter in InMemoryMealRepozitory");
        log.debug("userId = " + userId);
        log.debug(getAll().size() + " ++");
        log.debug("Start date: " + startDate);
        log.debug("End date: " + endDate);
        log.debug("Start time: " + startTime);
        log.debug("End time: " + endTime);

        List<Meal> result = getAll().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
        if (result.size() == 0) {
            log.debug("meal's list is empty for userId:" + userId);
            throw new NotFoundException("Empty list!");             //todo   Что нужно возвращать, когда список пуст?

        } else {
            log.debug(result.size() + "");
            log.debug("User id in meal = " + result.get(0).getUserId());
            return result;
        }
    }
}

