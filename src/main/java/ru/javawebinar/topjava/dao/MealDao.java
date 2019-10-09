package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao{

    Meal create(Meal meal);
    Meal getById(int id);
    void update(Meal meal);
    void delete(Meal meal);
    List<Meal> getAll();

}
