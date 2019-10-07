package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public interface MealWorkable {
    Meal create(LocalDateTime localDateTime, String description, int calories);
    Meal getById(int id);
    void update(int id, LocalDateTime newLocalDateTime, String newDescription, int newCalories);
    void delete(int id);
    void addToMemory(Meal meal);
}
