package com.yapikredi.yapikredi.model;

import lombok.Data;

import java.util.Calendar;

@Data
public class PublicHoliday {
    String date;
    String localName;
    String name;
    String countryCode;
    Boolean fixed;
    Boolean global;

}