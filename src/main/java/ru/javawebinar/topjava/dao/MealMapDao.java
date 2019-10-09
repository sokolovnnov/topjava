package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.MealDataArray;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMapDao implements MealDao {

    private MealDataArray mealDataArray;
    private ConcurrentHashMap<Integer, Meal> mealMap;
    private AtomicInteger atomicCount;

    public MealMapDao(){
        mealDataArray = new MealDataArray();
        mealMap = mealDataArray.getMealMap();
        atomicCount = new AtomicInteger();
        this.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 100)) ;
        this.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 100)) ;
        this.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 100)) ;
        this.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 100)) ;
        this.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 100)) ;
        this.create(new Meal(LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "Завтрак", 501)) ;
        this.create(new Meal(LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "Завтрак", 500)) ;
        this.create(new Meal(LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "Завтрак", 500)) ;
        this.create(new Meal(LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "Завтрак", 500)) ;
    }

    @Override
    public Meal create(Meal meal) { //add new meal to DB
        meal.setId(atomicCount.incrementAndGet());
        return mealMap.put(meal.getId(), meal);
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
