package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.MealDataArray;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMapDao implements MealDao {

//    public AtomicInteger atomicCount = new AtomicInteger(7);
    public MealDataArray mealDataArray = new MealDataArray();
    public ConcurrentHashMap<Integer, Meal> mealMap = mealDataArray.getMealMap();

    @Override
    public void create(Meal meal) { //add new meal to DB
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public Meal getById(int id) {
        return mealMap.get(id);
    }

    @Override
    public void update(Meal meal) { //замена
        mealMap.replace(meal.getId(), meal);
    }

    @Override
    public void delete(Meal meal) {
        mealMap.remove(meal.getId());
    }

    public List<Meal> getAll(){
        return new ArrayList<>(mealMap.values());
    }
}
