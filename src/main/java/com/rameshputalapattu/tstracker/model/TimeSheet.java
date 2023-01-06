package com.rameshputalapattu.tstracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimeSheet {
    private LocalDate date;
    private String task;
    private int hours;
}
