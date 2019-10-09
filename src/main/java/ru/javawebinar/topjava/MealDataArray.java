package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.util.concurrent.ConcurrentHashMap;

public class MealDataArray {
    private ConcurrentHashMap<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    public ConcurrentHashMap<Integer, Meal> getMealMap(){
        return mealMap;
    }
}
