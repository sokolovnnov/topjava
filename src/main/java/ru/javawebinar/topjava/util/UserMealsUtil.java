package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded1(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }


    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> resultList = new ArrayList<>();
        Map<LocalDate, Integer> map = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            map.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);
        }

        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                resultList.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        map.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }

        return resultList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded1(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> map = mealList.stream().collect(Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, Integer::sum));

        List<UserMealWithExceed> resultList = mealList.stream()
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(),
                userMeal.getDescription(), userMeal.getCalories(), map.get(userMeal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        resultList = resultList.stream().filter(userMealWithExceed -> TimeUtil.isBetween(userMealWithExceed.getDateTime().toLocalTime(),
                startTime, endTime)).collect(Collectors.toList());

        return resultList;
    }
}
