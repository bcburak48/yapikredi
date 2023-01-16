package com.yapikredi.yapikredi.service;

import com.yapikredi.yapikredi.entity.Employee;
import com.yapikredi.yapikredi.entity.EmployeeDayoff;
import com.yapikredi.yapikredi.model.PublicHoliday;
import com.yapikredi.yapikredi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeDayoffService {
    EmployeeRepository employeeRepository;

    public EmployeeDayoffService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String takeDayoff(String employeeNo, Calendar start, Calendar end) throws ParseException {

        Employee employee = employeeRepository.findEmployeeByEmployeeNo(employeeNo);

        if(employee == null){
            return null;
        }

        int totalExistDayoffs = totalExistDayoffs(employee.getStartDate());
        int usedDays = usedDayOffs(employee.getList());
        int requestedBusinessDay = calculateWorkDays(start, end);
        int currentDayoffs = totalExistDayoffs - usedDays;

        if (requestedBusinessDay <= currentDayoffs) {
            EmployeeDayoff dayoff = new EmployeeDayoff();
            dayoff.setStartDate(start);
            dayoff.setEndDate(end);
            dayoff.setStatus("WAITING");
            dayoff.setDays(requestedBusinessDay);
            employee.getList().add(dayoff);
            employeeRepository.save(employee);
            return "SENDED TO ADMIN";
        }else{
            if (doesFirstYear(employee.getStartDate())){
                if (requestedBusinessDay < 6 - (usedDays)) {
                    EmployeeDayoff dayoff = new EmployeeDayoff();
                    dayoff.setStartDate(start);
                    dayoff.setEndDate(end);
                    dayoff.setStatus("WAITING");
                    dayoff.setDays(requestedBusinessDay);
                    employee.getList().add(dayoff);
                    employeeRepository.save(employee);
                    return "SENDED TO ADMIN";
                } else {
                    return "REJECTED " + requestedBusinessDay + " DAYS, ADVANCE: " + String.valueOf(5 - usedDays);
                }
            }
            return "REJECTED " + requestedBusinessDay + " DAYS, YOUR EXIST DAYOFFS: " + currentDayoffs;
        }
    }
    public int totalExistDayoffs(Calendar startDate) {
        int years = calculateYears(startDate);
        if (years < 1) {
            return 0;
        } else if (0 < years && years < 6) {
            return (years) * 15;
        } else if (5 < years && years < 11) {
            return (5 * 15) + ((years - 5) * 18);
        } else {
            return (5 * 15) + (5 * 18) + ((years - 10) * 24);
        }
    }

    public int calculateYears(Calendar date){
        return Math.toIntExact(ChronoUnit.DAYS.between(date.toInstant(), Calendar.getInstance().toInstant()) / 365);
    }
    public int usedDayOffs(List<EmployeeDayoff> dayoffs) {
        int sum = 0;
        for (EmployeeDayoff dayoff : dayoffs) {
            if (dayoff.getStatus().equals("CONFIRMED") || dayoff.getStatus().equals("WAITING")) {
                sum += dayoff.getDays();
            }
        }
        return sum;
    }
    public Boolean doesFirstYear(Calendar startDate) {
        return ((ChronoUnit.DAYS.between(startDate.toInstant(), Calendar.getInstance().toInstant()) / 365) < 1)  ? true : false;
    }
    public int calculateWorkDays(Calendar startDate, Calendar endDate) throws ParseException {

        List<Calendar> holidayList = getPublicHolidays();

        List<Integer> holidayDayOfYearList = new ArrayList<Integer>();

        for (Calendar date : holidayList) {
            holidayDayOfYearList.add(date.get(Calendar.DAY_OF_YEAR));
        }
        int workDays = 0;
        if (startDate.getTimeInMillis() == endDate.getTimeInMillis()) {
            return 0;
        }
        do {
            startDate.add(Calendar.DAY_OF_MONTH, 1);
            if (startDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                    && startDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
                    && !holidayDayOfYearList.contains((Integer) startDate.get(Calendar.DAY_OF_YEAR))) {
                ++workDays;
            }
        } while (startDate.getTimeInMillis() < endDate.getTimeInMillis());
        return workDays;
    }
    public List<Calendar> getPublicHolidays() throws ParseException {

        RestTemplate restTemplate = new RestTemplate();
        PublicHoliday[] publicHolidays = restTemplate.getForObject("https://date.nager.at/api/v3/publicholidays/"+ Year.now().getValue() +"/TR", PublicHoliday[].class);

        List<Calendar> officialHolidayList = new ArrayList<Calendar>();

        for (PublicHoliday item : publicHolidays) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Date date = sdf.parse(item.getDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            officialHolidayList.add(calendar);
        }
        return officialHolidayList;
    }
    public Object getDayoffList(String employeeNo) {
        return employeeRepository.findEmployeeByEmployeeNo(employeeNo).getList();
    }
}