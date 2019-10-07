package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDataArryaList implements MealWorkable {
    public MealDataArryaList()
    {
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 400));
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public List<Meal> meals = new ArrayList<>();

    public void firstInit() {
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 400));
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        addMeal(createNewMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public List<Meal> getMeals() {
        return meals;
    }
    static AtomicInteger atomicCount = new AtomicInteger(0);
    @Override
    public Meal createNewMeal(LocalDateTime localDateTime, String description, int calories) {
        int count = atomicCount.incrementAndGet();
        return new Meal(count, localDateTime, description, calories);
    }

    @Override
    public Meal readMeal(int id) {
        return getMealById(id);
    }

    @Override
    public void updateMeal(int id, LocalDateTime newlocalDateTime, String newDescription, int newCalories) {
        Meal meal = getMealById(id);
        meal.setCalories(newCalories);
        meal.setDescription(newDescription);
        meal.setDateTime(newlocalDateTime);
    }

    @Override
    public void deleteMeal(int id) {
        meals.remove(meals.get(id));
    }

    @Override
    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    private Meal getMealById(Integer id) {
        for (Meal meal : meals) {
            if (meal.getId() == id) return meal;
        }
        return null;
    }
}
