package com.yapikredi.yapikredi.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
public class EmployeeDayoff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String status;
    private int days;
    @Temporal(TemporalType.DATE)
    private Calendar startDate;
    @Temporal(TemporalType.DATE)
    private Calendar endDate;

}
