package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDataArray implements MealWorkable {
    public MealDataArray()
    {
        addToMemory(create(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        addToMemory(create(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        addToMemory(create(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 400));
        addToMemory(create(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        addToMemory(create(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        addToMemory(create(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        addToMemory(create(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public List<Meal> meals = new ArrayList<>();

    private static AtomicInteger atomicCount = new AtomicInteger(-1);

    @Override
    public Meal create(LocalDateTime localDateTime, String description, int calories) {
        int count = atomicCount.incrementAndGet();
        return new Meal(count, localDateTime, description, calories);
    }

    @Override
    public void update(int id, LocalDateTime newLocalDateTime, String newDescription, int newCalories) {
        Meal meal = getById(id);
        meal.setCalories(newCalories);
        meal.setDescription(newDescription);
        meal.setDateTime(newLocalDateTime);
    }

    @Override
    public void delete(int id) {
        Meal foRemove = getById(id);
        meals.remove(foRemove);
    }

    @Override
    public void addToMemory(Meal meal) {
        meals.add(meal);
    }

    public Meal getById(int id) {
        for (Meal meal : meals) {
            if (meal.getId() == id) return meal;
        }
        return null;
    }
}
