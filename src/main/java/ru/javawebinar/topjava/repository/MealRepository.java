package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    // null if not found, when updated
    Meal save(int authUserId, Meal meal);

    // false if not found
    boolean delete(int authUserId, int id);

    // null if not found
    Meal get(int authUserId, int id);

    Collection<Meal> getAll(int authUserId);

    Collection<Meal> getWithFilter(int authUserId, LocalDate startDate, LocalDate endDate);
}
