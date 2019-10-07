package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealTo {

    public int id;

    public int getId() {
        return id;
    }

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    //    private final Supplier<Boolean> excess;
//    private final AtomicBoolean excess;
    private final boolean excess;

    public MealTo(int id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
        this.id = id;
    }

//    public Boolean getExcess() {
//        return excess.get();
//    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}