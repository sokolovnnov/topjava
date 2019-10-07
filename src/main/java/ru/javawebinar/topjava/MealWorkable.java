package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public interface MealWorkable {
    Meal createNewMeal(LocalDateTime localDateTime, String description, int calories);
    Meal readMeal(int id);
    void updateMeal(int id, LocalDateTime newLocalDateTime, String newDescription, int newCalories);
    void deleteMeal(int id);
    void addMeal(Meal meal);
}
