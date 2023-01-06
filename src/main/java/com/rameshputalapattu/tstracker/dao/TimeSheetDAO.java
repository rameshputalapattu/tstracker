package com.rameshputalapattu.tstracker.dao;

import com.rameshputalapattu.tstracker.model.TimeSheet;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Component
public class TimeSheetDAO implements DAO<TimeSheet> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(TimeSheetDAO.class);

    private final RowMapper<TimeSheet> rowMapper = (rs,rowNum) -> {
        TimeSheet timeSheet = new TimeSheet();
        timeSheet.setDate(rs.getDate("date"));
        timeSheet.setTask(rs.getString("task"));
        timeSheet.setHours(rs.getInt("hours"));
        return timeSheet;
    };

    @Override
    public List<TimeSheet> list() {

        String sqlQuery = """
                select date,task,hours 
                from timesheet
                order by date
                """;

        return jdbcTemplate.query(sqlQuery,rowMapper);


    }

    @Override
    public void create(TimeSheet timeSheet) {

        String insertQuery = "insert into timesheet(date,task,hours) values(?,?,?)";
        jdbcTemplate.update(insertQuery,timeSheet.getDate(),
                                        timeSheet.getTask(),
                                        timeSheet.getHours());


    }

    @Override
    public Optional<TimeSheet> get(int id) {
        String getSQL = """
                select date,task,hours 
                from timesheet
                where id = ?
                """;
        TimeSheet timeSheet = null;
        try {
            timeSheet = jdbcTemplate.queryForObject(getSQL,rowMapper,id);
        } catch (DataAccessException dex) {
           log.info("Error getting the object "+dex);
        }

        return Optional.ofNullable(timeSheet);
    }

    @Override
    public void update(int id) {

    }

    @Override
    public void delete(int id) {

    }

    public int totalHours() {
        String sql = """
                select sum(hours) as total_hours 
                from timesheet
                
                """;

        return jdbcTemplate.queryForObject(sql,Integer.class);

    }

    public List<Date> allDays() {
        String sql = """
                select distinct date 
                from timesheet
                """;

        return jdbcTemplate.query(sql,(rs,rowNum) -> {
             return rs.getDate("date");
        });
    }
}
