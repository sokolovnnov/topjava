package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.MealDataArray;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MealMapDao implements MealDao {

    public MealDataArray mealDataArray = new MealDataArray();
    private ConcurrentHashMap<Integer, Meal> mealMap = mealDataArray.getMealMap();

//        mealMap.put(1, new Meal(1,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 100));
//        mealMap.put(2, new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 200));
//        mealMap.put(3, new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 300));
//        mealMap.put(4, new Meal(4, LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "Завтрак", 400));
//        mealMap.put(5, new Meal(5, LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "Завтрак", 500));
//        mealMap.put(6, new Meal(6, LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "Завтрак", 600));
//        mealMap.put(7, new Meal(7, LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "Завтрак", 700));


    @Override
    public void create(Meal meal) { //add new meal to DB
        meal.setId(mealDataArray.atomicCount.incrementAndGet());
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
