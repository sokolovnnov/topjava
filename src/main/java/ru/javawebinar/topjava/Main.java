package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello Topjava Enterprise!");

        List<MealTo> mealList = MealsUtil.getFiltered(new MealDataArryaList().meals, LocalTime.MIN, LocalTime.MAX, MealsUtil.getDefaultCaloriesPerDay());
        for (MealTo mealTo: mealList){
            System.out.println(mealTo.getId());
        }
    }
}
