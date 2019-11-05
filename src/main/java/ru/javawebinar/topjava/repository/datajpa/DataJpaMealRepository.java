package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;


import static ru.javawebinar.topjava.util.DateTimeUtil.getEndExclusive;
import static ru.javawebinar.topjava.util.DateTimeUtil.getStartInclusive;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private static final Sort SORT_DATE = Sort.by(Sort.Direction.DESC, "datetime");

    @Autowired
    private CrudMealRepository crudRepository;
    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(crudUserRepository.getOne(userId));
        return crudRepository.save(meal);
    }

//    @Override
//    public boolean delete(int id, int userId) {
//        return crudRepository.delete(id, userId) != 0;
//    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteMealByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findById(id).filter(meal -> meal.getUser().getId() == userId).orElse(null);
//        Meal meal = crudRepository.getMealById(id);
//        if (meal.getUser().getId() != userId) return null;
//        else return meal;
    }

//    @Override
//    public Meal get(int id, int userId) {
//        Meal meal = crudRepository.getOne(id);
//        if (meal.getUser().getId() != userId) return null;
//        else return meal;
//    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAllByUserIdOrderByDateTimeDesc(userId);
    }

//    @Override
//    public List<Meal> getAll(int userId) {
//        return crudRepository.getAll(userId);
//    }

    @Override
    public List<Meal> getBetweenInclusive(LocalDate startDate, LocalDate endDate, int userId) {
        return crudRepository.getBetween(getStartInclusive(startDate), getEndExclusive(endDate), userId);
    }
}
