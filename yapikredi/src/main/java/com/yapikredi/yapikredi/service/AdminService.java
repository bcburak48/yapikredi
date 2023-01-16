package com.yapikredi.yapikredi.service;

import com.yapikredi.yapikredi.entity.EmployeeDayoff;
import com.yapikredi.yapikredi.repository.EmployeeDayoffRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    EmployeeDayoffRepository dayoffRepository;

    public AdminService(EmployeeDayoffRepository dayoffRepository) {
        this.dayoffRepository = dayoffRepository;
    }

    public void confirmDayoff(Long id){
        EmployeeDayoff dayoff = dayoffRepository.findById(id).get();
        dayoff.setStatus("CONFIRMED");
        dayoffRepository.save(dayoff);
    }

    public void rejectDayoff(Long id) {
        EmployeeDayoff dayoff = dayoffRepository.findById(id).get();
        dayoff.setStatus("REJECTED");
        dayoffRepository.save(dayoff);
    }

    public EmployeeDayoff findDayoffs(String status) {
        return dayoffRepository.findEmployeeDayoffsByStatus(status);
    }
}
