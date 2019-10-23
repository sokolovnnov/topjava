package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
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
                .addValue("time_date", meal.getDateTime());
        if (meal.isNew()) {
            mapSqlParameterSource.addValue("user_id", userId);
            Number number = insertMeal.executeAndReturnKey(mapSqlParameterSource);
            meal.setId(number.intValue());
        } else {
            int update = jdbcTemplate.update("UPDATE meals " +
                            "SET description = ?, calories = ?, time_date = ? " +
                            "WHERE id = ? AND user_id = ?",
                    mapSqlParameterSource.getValue("description"),
                    mapSqlParameterSource.getValue("calories"),
                    mapSqlParameterSource.getValue("time_date"),
                    meal.getId(), userId);
            if (update == 0) return null;
        }
        return meal;
    }

    @Override//ok
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE from meals WHERE id=? and user_id=?", id, userId) != 0;
    }

    @Override//ok
    public Meal get(int id, int userId) {
            return DataAccessUtils.singleResult(jdbcTemplate.query("SELECT * FROM meals WHERE id=? and user_id=?",
                    mealRowMapper, id, userId));
    }

    @Override//ok
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY time_date DESC", mealRowMapper, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals " +
                        "WHERE user_id =? AND time_date BETWEEN ? AND ? " +
                        "ORDER BY time_date DESC", mealRowMapper, userId, startDate, endDate);
    }

    private static final class MealRowMapper implements RowMapper<Meal> {

        @Override
        public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {
            Meal meal = new Meal();
            meal.setId(rs.getInt("id"));
            meal.setDescription(rs.getString("description"));
            meal.setCalories(rs.getInt("calories"));
            meal.setDateTime(rs.getObject("time_date", LocalDateTime.class));
            return meal;
        }
    }

}
