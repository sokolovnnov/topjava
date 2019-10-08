package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDataArray {

    private ConcurrentHashMap<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    public AtomicInteger atomicCount = new AtomicInteger(7);

    public MealDataArray(){
        mealMap.put(1, new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 100));
        mealMap.put(2, new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 200));
        mealMap.put(3, new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 300));
        mealMap.put(4, new Meal(4, LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "Завтрак", 400));
        mealMap.put(5, new Meal(5, LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "Завтрак", 500));
        mealMap.put(6, new Meal(6, LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "Завтрак", 600));
        mealMap.put(7, new Meal(7, LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "Завтрак", 700));
    }

    public ConcurrentHashMap<Integer, Meal> getMealMap(){
        return mealMap;
    }
}
