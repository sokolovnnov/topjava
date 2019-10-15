package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
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
        MealsUtil.MEALS.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int authUserId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(authUserId);
            log.debug("save in repo, id = " + meal.getId() + ", userId = " + meal.getUserId());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);     //meal replace oldMeal                                                                         //todo delete проверяет есть ли в мапе meal c таким id (первый параметр)
    }                                                                                                                                                        //и заменяет value на meal (если есть), а если нет, то

    @Override
    public boolean delete(int authUserId, int id) {
        log.debug("Delete: " + id);
        try {
            if (!isCheckUserId(authUserId, id)) return false;
            return repository.remove(id) != null;
        }
        catch (NullPointerException n){
            return false;
        }
    }

    @Override
    public Meal get(int authUserId, int id) {
        try {
            if (!isCheckUserId(authUserId, id)) return null;
            return repository.get(id);
        }
        catch (NullPointerException n) {
            return null;
        }
    }

    @Override
    public Collection<Meal> getAll(int authUserId) {
        return filterByUserId(authUserId);
    }

    //возвращает List<Meal> для одного пользователя, обрезаный по датам, отсортированный наоборот
    @Override
    public Collection<Meal> getWithFilter(int authUserId, LocalDate startDate, LocalDate endDate) {

        return filterByUserId(authUserId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }

    private boolean isCheckUserId(int authUserId, int mealId) {
        return authUserId == repository.get(mealId).getUserId();
    }

    private Collection<Meal> filterByUserId(int authUserId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == authUserId)
                .sorted(Comparator.comparing((Meal meal) -> meal.getDateTime()).reversed())
                .collect(Collectors.toList());
    }
}

