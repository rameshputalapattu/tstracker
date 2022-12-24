package com.rameshputalapattu.tstracker.controllers;

import com.rameshputalapattu.tstracker.dao.TimeSheetDAO;
import com.rameshputalapattu.tstracker.model.TimeSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TSController {
    @Autowired
    private TimeSheetDAO timeSheetDAO;

    @GetMapping("/ts")
    public List<TimeSheet> getTimeSheets() {


        return timeSheetDAO.list();

    }

    @PostMapping("/ts/create")
    public void createTimeSheet(@RequestBody TimeSheet timeSheet) {

        timeSheetDAO.create(timeSheet);
    }

    @GetMapping("/ts/{id}")
    public TimeSheet getTimeSheetById(@PathVariable int id) {
        return timeSheetDAO.get(id).orElse(null);
    }
}
