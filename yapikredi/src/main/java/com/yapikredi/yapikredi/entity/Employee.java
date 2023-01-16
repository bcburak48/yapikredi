package com.yapikredi.yapikredi.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 4)
    private String employeeNo;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private Calendar startDate;
    @Temporal(TemporalType.DATE)
    private Calendar endDate;
    @OneToMany(cascade=CascadeType.ALL)
    private List<EmployeeDayoff> list;
}
