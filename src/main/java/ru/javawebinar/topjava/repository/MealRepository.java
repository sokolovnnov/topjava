package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal);

//    Meal save(Meal meal, int userId);

    // false if not found
    boolean delete(int id);

    // null if not found
    Meal get(int id);

    Collection<Meal> getAll();

    Collection<Meal> getAllbyUser(int userId);

    Collection<Meal> getWithFilter(int userId, LocalDate startDate, LocalDate endDate);
}
