package com.rameshputalapattu.tstracker.dao;

import com.rameshputalapattu.tstracker.jooq.model.Tables;
import com.rameshputalapattu.tstracker.model.TimeSheet;

import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Slf4j
public class TimeSheetDAO implements DAO<TimeSheet> {

    private final String dataSourceURL;

    private final DSLContext dslContext;

    public TimeSheetDAO(String dataSourceURL,String dataSourceUserName,String dataSourcePassword) throws SQLException {
        this.dataSourceURL = dataSourceURL;
        String user = dataSourceUserName;
        String password = dataSourcePassword;
        Connection conn = DriverManager.getConnection(this.dataSourceURL, user, password);
        this.dslContext = DSL.using(conn, SQLDialect.SQLITE);
    }
    @Override
    public List<TimeSheet> list() {

        Result<Record3<LocalDate,String,Integer>> result = this.dslContext.select(Tables.TIMESHEET.DATE,
                Tables.TIMESHEET.TASK,
                Tables.TIMESHEET.HOURS

                ).from(Tables.TIMESHEET).fetch();


       return result.stream().map(it -> {
        return    new TimeSheet(it.getValue(Tables.TIMESHEET.DATE),
        it.getValue(Tables.TIMESHEET.TASK),
        it.getValue(Tables.TIMESHEET.HOURS));}).toList();

    }

    @Override
    public void create(TimeSheet timeSheet) {

        this.dslContext
                .insertInto(Tables.TIMESHEET)
                .columns(Tables.TIMESHEET.DATE,
                        Tables.TIMESHEET.TASK,
                        Tables.TIMESHEET.HOURS)
                .values(timeSheet.getDate(),
                        timeSheet.getTask(),
                        timeSheet.getHours())
                .execute();


    }

    @Override
    public Optional<TimeSheet> get(int id) {

        return this.dslContext.select()
                .from(Tables.TIMESHEET)
                .where(Tables.TIMESHEET.ID.eq(id))
                 .fetchOptional(it -> new TimeSheet(it.get(Tables.TIMESHEET.DATE),
                         it.get(Tables.TIMESHEET.TASK),
                         it.get(Tables.TIMESHEET.HOURS)
                         ));


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
        Record res = this.dslContext
                .fetchOne(sql);

        assert res != null;
        return res.get(0,Integer.class);


    }

    public List<LocalDate> allDays() {
        String sql = """
                select distinct date 
                from timesheet
                """;

       return this.dslContext
                .select(Tables.TIMESHEET.DATE)
                .distinctOn(Tables.TIMESHEET.DATE)
                .from(Tables.TIMESHEET)
                .fetch(it -> it.getValue(Tables.TIMESHEET.DATE) );


    }
}
