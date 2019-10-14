package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.MealServlet;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(8);

    {
        MealsUtil.MEALS.forEach(this::save);
    }
    //    todo СДЕЛАНО 2.2:
    //     get update delete - следите, чтобы не было NPE (NullPointException может быть, если в хранилище отсутствует юзер или еда).


//    todo СДЕЛАНО: ==>>  2.3: Фильтрацию по датам сделать в репозитории т.к. из базы будем брать сразу отфильтрованные по дням записи.
//     НЕ СДЕЛАНО!!! ==>> Следите чтобы первый и последний день не были обрезаны, иначе сумма калорий будет неверная.
//
//    todo СДЕЛАНО(Кидает NFE): ==>> 2.4: Если запрашивается список и он пустой, не возвращайте NULL!
//     По пустому списку можно легко итерироваться без риска NullPoinException.
//
//    todo ==>>  2.5: Не дублируйте код в getAll и метод с фильтрацией
//
//    todo ==>> 2.6: попробуйте учесть, что следующая реализация (сортировка, фильтрация) будет делаться прямо в базе данных
    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(SecurityUtil.getAuthUserId());
            log.debug("save in repo, id = "+ meal.getId() + ", userId = " + meal.getUserId());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal); //todo delete проверяет есть ли в мапе meal c таким id (первый параметр)
    }                                                                            //и заменяет value на meal (если есть), а если нет, то

//    public Meal save(Meal meal, int userId){
//        meal.setUserId(userId);
//        return save(meal);
//    }

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
        Collection<Meal> result = repository.values();
        log.debug("sise in getAll = " + result.size());
//        if (result.size() == 0) throw new NotFoundException("список еды пуст");
//        else
            return  result;
    }

    public Collection<Meal> getAllbyUser(int userId) {
        Collection<Meal> result = repository.values().stream().filter(meal -> meal.getUserId()==userId).collect(Collectors.toList());
        if (result.size() == 0) throw new NotFoundException("список еды пуст");
        else return  result;
    }

    @Override
    public Collection<Meal> getWithFilter(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        log.debug("before filter in InMemoryMealRepozitory");
        log.debug("userId = " + userId);
        log.debug(getAll().size()+ " ++");
        log.debug("Start date: " + startDate);
        log.debug("End date: " + endDate);
        log.debug("Start time: " + startTime);
        log.debug("End time: " + endTime);

        List<Meal> result = getAll().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
        if (result.size() == 0) {
            log.debug("meal's list is empty for userId:" + userId);
            return result;
            //throw new NotFoundException("список еды пуст");

        }
        else {
            log.debug(result.size() + "");
            log.debug("User id in meal = " + result.get(0).getUserId());
            return result;
        }
    }

    //todo сделать фильтацию по времени




}

