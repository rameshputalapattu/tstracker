package com.rameshputalapattu.tstracker.controllers;

import com.rameshputalapattu.tstracker.dao.TimeSheetDAO;
import com.rameshputalapattu.tstracker.model.AllTimeSheetResponse;
import com.rameshputalapattu.tstracker.model.TimeSheet;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.client.RestTemplate;


@RestController
@Slf4j
public class TSController {
    @Autowired
    private TimeSheetDAO timeSheetDAO;
    @Value("${props.app-name}")
    private String appName;

    @Value("${spring.datasource.url}")
    private String dataSourceURL;

    @Autowired
    private RestTemplate restTemplate;
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

    @GetMapping("/ts/totalhours")
    public int getTotalHours() {

        log.info("calling the total hours api");
        log.info("Injected URL="+dataSourceURL);
        return timeSheetDAO.totalHours();
    }

    @GetMapping("/ts/missingdays")
    public List<LocalDate> getMissingHours(@RequestParam int year,@RequestParam int month) {
        List<LocalDate> allDays = timeSheetDAO.allDays();
        return getMissingDates(allDays,year,month);

    }

    private List<LocalDate> getMissingDates(List<LocalDate> dates, int year, int month) {
        // Get the first and last days of the month
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        // Create a list to store the missing dates
        List<LocalDate> missingDates = new ArrayList<>();

        // Iterate over all the days in the month and check for missing dates
        for (LocalDate date = firstDayOfMonth; !date.isAfter(lastDayOfMonth); date = date.plusDays(1)) {

            if (!dates.contains(date) && !date.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                missingDates.add(date);
            }
        }

        return missingDates;
    }

    @GetMapping("/ts/all")
    public AllTimeSheetResponse getAllTimeSheets() {
        AllTimeSheetResponse timeSheetResponse = new AllTimeSheetResponse();
        timeSheetResponse.setTimeSheets(timeSheetDAO.list());
        return timeSheetResponse;
    }

    @GetMapping("/ts/name")
    public String getAppName() {
        return appName;
    }

    @GetMapping(value = "/ts/download", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<InputStreamResource> download() throws IOException {


        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=timesheet.xlsx");
        List<TimeSheet> timeSheets = timeSheetDAO.list();
        InputStreamResource resource = writeSpreadSheet(timeSheets);
        return ResponseEntity.ok().headers(headers).body(resource);


    }

    private InputStreamResource writeSpreadSheet(List<TimeSheet> timeSheets) throws IOException {
        // Create the Excel workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Timesheet");

        // Create a bold font
        Font font = workbook.createFont();
        font.setBold(true);

        // Create a cell style
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);

        // Create the header row
        Row headerRow = sheet.createRow(0);
        Cell cell = headerRow.createCell(0);
        cell.setCellValue("Date");
        cell.setCellStyle(style);

        cell = headerRow.createCell(1);
        cell.setCellValue("Task");
        cell.setCellStyle(style);

        cell = headerRow.createCell(2);
        cell.setCellValue("Hours");
        cell.setCellStyle(style);

        int rowNum = 1;
        for (TimeSheet row : timeSheets) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue((String) row.getDate().toString());
            Cell taskCell = dataRow.createCell(1);
            taskCell.setCellValue((String) row.getTask());
            // Get the cell style
            CellStyle taskStyle = taskCell.getCellStyle();

            // Set the wrap text property
            taskStyle.setWrapText(true);

            // Apply the updated style to the cell
            taskCell.setCellStyle(taskStyle);


            dataRow.createCell(2).setCellValue((Integer) row.getHours());
        }

        // Resize the columns to fit the content
        for (int i = 0; i < 3; i++) {
           if (i != 1) {
                sheet.autoSizeColumn(i);
            }

           if (i == 1) {
               sheet.setColumnWidth(1, 50 * 256);
           }

        }

        // Write the workbook to an output stream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
        return resource;




    }


    @PostMapping("/ts/ingestfromh2")
    public String tsIngest() {


       ResponseEntity<AllTimeSheetResponse>  resp = restTemplate.getForEntity("http://localhost:8082/ts/all", AllTimeSheetResponse.class);
       AllTimeSheetResponse allTimeSheetResponse = resp.getBody();
        assert allTimeSheetResponse != null;
        allTimeSheetResponse.getTimeSheets().stream().forEach(it -> {
           timeSheetDAO.create(it);
       });
       return "successful ingest";
    }

    


}
