/*
 * This file is generated by jOOQ.
 */
package com.rameshputalapattu.tstracker.jooq.model.tables;


import com.rameshputalapattu.tstracker.jooq.model.DefaultSchema;
import com.rameshputalapattu.tstracker.jooq.model.Keys;
import com.rameshputalapattu.tstracker.jooq.model.tables.records.TimesheetRecord;

import java.time.LocalDate;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function4;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Timesheet extends TableImpl<TimesheetRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>timesheet</code>
     */
    public static final Timesheet TIMESHEET = new Timesheet();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TimesheetRecord> getRecordType() {
        return TimesheetRecord.class;
    }

    /**
     * The column <code>timesheet.ID</code>.
     */
    public final TableField<TimesheetRecord, Integer> ID = createField(DSL.name("ID"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>timesheet.date</code>.
     */
    public final TableField<TimesheetRecord, LocalDate> DATE = createField(DSL.name("date"), SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>timesheet.task</code>.
     */
    public final TableField<TimesheetRecord, String> TASK = createField(DSL.name("task"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>timesheet.hours</code>.
     */
    public final TableField<TimesheetRecord, Integer> HOURS = createField(DSL.name("hours"), SQLDataType.INTEGER, this, "");

    private Timesheet(Name alias, Table<TimesheetRecord> aliased) {
        this(alias, aliased, null);
    }

    private Timesheet(Name alias, Table<TimesheetRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>timesheet</code> table reference
     */
    public Timesheet(String alias) {
        this(DSL.name(alias), TIMESHEET);
    }

    /**
     * Create an aliased <code>timesheet</code> table reference
     */
    public Timesheet(Name alias) {
        this(alias, TIMESHEET);
    }

    /**
     * Create a <code>timesheet</code> table reference
     */
    public Timesheet() {
        this(DSL.name("timesheet"), null);
    }

    public <O extends Record> Timesheet(Table<O> child, ForeignKey<O, TimesheetRecord> key) {
        super(child, key, TIMESHEET);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<TimesheetRecord, Integer> getIdentity() {
        return (Identity<TimesheetRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<TimesheetRecord> getPrimaryKey() {
        return Keys.TIMESHEET__PK_TIMESHEET;
    }

    @Override
    public Timesheet as(String alias) {
        return new Timesheet(DSL.name(alias), this);
    }

    @Override
    public Timesheet as(Name alias) {
        return new Timesheet(alias, this);
    }

    @Override
    public Timesheet as(Table<?> alias) {
        return new Timesheet(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Timesheet rename(String name) {
        return new Timesheet(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Timesheet rename(Name name) {
        return new Timesheet(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Timesheet rename(Table<?> name) {
        return new Timesheet(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, LocalDate, String, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function4<? super Integer, ? super LocalDate, ? super String, ? super Integer, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function4<? super Integer, ? super LocalDate, ? super String, ? super Integer, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}