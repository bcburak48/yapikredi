
package com.yapikredi.yapikredi;

import com.yapikredi.yapikredi.config.SmartLocaleResolver;
import com.yapikredi.yapikredi.entity.EmployeeDayoff;
import com.yapikredi.yapikredi.entity.Employee;
import com.yapikredi.yapikredi.repository.EmployeeRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class YapikrediApplication {

    @Autowired
    EmployeeRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(YapikrediApplication.class, args);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/mes");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setCacheSeconds((int) TimeUnit.HOURS.toSeconds(1));
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new SmartLocaleResolver();
    }

    @Bean
    InitializingBean sendDatabase() {
        return () -> {

            Calendar calendarStart = Calendar.getInstance();
            calendarStart.set(Calendar.YEAR, 2020);
            calendarStart.set(Calendar.MONTH, 06);
            calendarStart.set(Calendar.DATE, 10);

            Employee e=new Employee();
            e.setEmployeeNo("1234");
            e.setName("Burak");
            e.setLastName("Can");
            e.setStartDate(calendarStart);


            EmployeeDayoff dayoff=new EmployeeDayoff();
            dayoff.setStatus("CONFIRMED");
            dayoff.setDays(3);


            List<EmployeeDayoff> dayoffList = new ArrayList<EmployeeDayoff>();
            dayoffList.add(dayoff);

            e.setList(dayoffList);

            repository.save(e);
        };
    }
}