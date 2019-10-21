package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertMeal;
    private final MealRowMapper mealRowMapper = new MealRowMapper();

    @Autowired
    public JdbcMealRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate).withTableName("meals").usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("timedate", meal.getDateTime());
        if(meal.isNew()){
            mapSqlParameterSource.addValue("userid", userId);
            Number number = insertMeal.executeAndReturnKey(mapSqlParameterSource);
            meal.setId(number.intValue());
        }
        else {
            int update = jdbcTemplate.update("UPDATE meals " +
                            "SET description = ?, calories = ?, timedate = ? " +
                            "WHERE id = ? AND userid = ?",
                    mapSqlParameterSource.getValue("description"),
                    mapSqlParameterSource.getValue("calories"),
                    mapSqlParameterSource.getValue("timedate"),
                    meal.getId(), userId);
            if (update == 0) return null;
        }
        return meal;
    }

    @Override//ok
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE from meals WHERE id=? and userid=?", id, userId) != 0;
    }

    @Override//ok
    public Meal get(int id, int userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM meals WHERE id=? and userid=?", mealRowMapper, id, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override//ok
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE userid=? ORDER BY timedate DESC", mealRowMapper, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals " +
                        "WHERE userid =? AND timedate BETWEEN ? AND ? " +
                        "ORDER BY timedate DESC", mealRowMapper, userId, startDate, endDate);
    }

    public static final class MealRowMapper implements RowMapper<Meal> {

        @Override
        public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {
            Meal meal = new Meal();
            meal.setId(rs.getInt("id"));
            meal.setDescription(rs.getString("description"));
            meal.setCalories(rs.getInt("calories"));
            meal.setDateTime(rs.getObject("timedate", LocalDateTime.class));
            return meal;
        }
    }

}
