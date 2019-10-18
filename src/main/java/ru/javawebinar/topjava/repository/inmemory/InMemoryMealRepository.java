package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);                                               //meal replace oldMeal                                                                         //todo delete проверяет есть ли в мапе meal c таким id (первый параметр)
    }                                                                                                                          //и заменяет value на meal (если есть), а если нет, то

//    @Override
//    public Meal update(int authUserId, int id, Meal meal) {
//        if (isWrongUserId(authUserId, id)) return null;
//        return repository.computeIfPresent(meal.getId(), (mealId, oldMeal) -> meal);     //meal replace oldMeal                                                                         //todo delete проверяет есть ли в мапе meal c таким id (первый параметр)
//    }                                                                                                                                                        //и заменяет value на meal (если есть), а если нет, то

    @Override
    public boolean delete(int authUserId, int id) {
        log.debug("Delete: " + id);
            if (isWrongUserId(authUserId, id)) return false;
            return repository.remove(id) != null;
    }

    @Override
    public Meal get(int authUserId, int id) {
            log.debug("start get " + authUserId+" " + id);
            if (isWrongUserId(authUserId, id)) return null;
            return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int authUserId) {
        return filterAndSorted(authUserId, meal -> true);
    }

    @Override
    public List<Meal> getWithFilter(int authUserId, LocalDate startDate, LocalDate endDate) {
        return filterAndSorted(authUserId, meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate));
    }

    private boolean isWrongUserId(int authUserId, int id) {
        if (repository.get(id) == null) return true;
        return authUserId != repository.get(id).getUserId();
    }

    private List<Meal> filterAndSorted(int authUserId, Predicate<Meal> filter){
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == authUserId)
                .filter(filter)
                .sorted(Comparator.comparing((Meal meal) -> meal.getDateTime()).reversed())
                .collect(Collectors.toList());
    }
}

