package com.rameshputalapattu.tstracker.dao;

import com.rameshputalapattu.tstracker.jooq.model.Tables;
import com.rameshputalapattu.tstracker.model.TimeSheet;

import lombok.extern.slf4j.Slf4j;

import org.jooq.*;
;
import org.jooq.impl.DSL;



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

    public TimeSheetDAO(String dataSourceURL,
                        String dataSourceUserName,
                        String dataSourcePassword,
                        String jooqDialect) throws SQLException {
        this.dataSourceURL = dataSourceURL;
        String user = dataSourceUserName;
        String password = dataSourcePassword;
        Connection conn = DriverManager.getConnection(this.dataSourceURL, user, password);

        this.dslContext = DSL.using(conn, SQLDialect.valueOf(jooqDialect));
    }
    @Override
    public List<TimeSheet> list() {

        return this.dslContext.select(Tables.TIMESHEET.DATE,
                Tables.TIMESHEET.TASK,
                Tables.TIMESHEET.HOURS

                ).from(Tables.TIMESHEET).fetchInto(TimeSheet.class);

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
                .fetchOptionalInto(TimeSheet.class);



    }

    @Override
    public void update(int id,TimeSheet timeSheet) {

        this.dslContext.update(Tables.TIMESHEET)
                .set(Tables.TIMESHEET.DATE,timeSheet.getDate())
                .set(Tables.TIMESHEET.TASK,timeSheet.getTask())
                .set(Tables.TIMESHEET.HOURS,timeSheet.getHours())
                .execute();

    }

    @Override
    public void delete(int id) {

    }

    public int totalHours() {
        String sql = """
                select sum(hours) as total_hours 
                from timesheet
                
                """;

        return this.dslContext.resultQuery(sql).fetchOneInto(Integer.class);


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
