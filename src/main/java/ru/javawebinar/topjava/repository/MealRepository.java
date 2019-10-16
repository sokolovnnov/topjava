package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    // null if not found, when updated
    Meal create(int authUserId, Meal meal);

    Meal update(int authUserId, int id, Meal meal);

    // false if not found
    boolean delete(int authUserId, int id);

    // null if not found
    Meal get(int authUserId, int id);

    List<Meal> getAll(int authUserId);

    List<Meal> getWithFilter(int authUserId, LocalDate startDate, LocalDate endDate);
}
