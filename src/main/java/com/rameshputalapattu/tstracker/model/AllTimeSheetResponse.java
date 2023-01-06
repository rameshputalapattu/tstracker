package com.rameshputalapattu.tstracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AllTimeSheetResponse {
    private List<TimeSheet> timeSheets;
}
