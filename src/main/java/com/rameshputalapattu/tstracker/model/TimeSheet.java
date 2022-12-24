package com.rameshputalapattu.tstracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TimeSheet {

    private String task;
    private Date date;
    private int hours;

}
