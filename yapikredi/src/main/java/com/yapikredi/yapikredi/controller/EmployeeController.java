package com.yapikredi.yapikredi.controller;

import com.yapikredi.yapikredi.service.EmployeeDayoffService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Logger;

@RestController
@RequestMapping("/employee")
@Api(value = "Employee Api")
@Validated
public class EmployeeController {

    static Logger log = Logger.getLogger(EmployeeController.class.getName());
    @Autowired
    EmployeeDayoffService dayoffService;

    @PostMapping(value = "/takedayoff")
    public ResponseEntity takeDayoff(@RequestBody String employeeNo, @RequestBody Calendar startDate, @RequestBody Calendar endDate) throws ParseException {


        log.info(employeeNo);
        log.info(startDate.toString());
        log.info(endDate.toString());

        return ResponseEntity.ok(dayoffService.takeDayoff(employeeNo, startDate, endDate));
    }

    @GetMapping(value = "/dayoff")
    public ResponseEntity getDayoff(@RequestParam String employeeNo){
        return ResponseEntity.ok(dayoffService.getDayoffList(employeeNo));
    }
}
