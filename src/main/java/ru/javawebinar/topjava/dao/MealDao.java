package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

public interface MealDao{

    void create(Meal meal);
    Meal getById(int id);
    void update(Meal meal);
    void delete(Meal meal);

}
