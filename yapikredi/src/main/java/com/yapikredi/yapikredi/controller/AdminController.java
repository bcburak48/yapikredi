package com.yapikredi.yapikredi.controller;

import com.yapikredi.yapikredi.service.AdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(value = "Admin Api")
public class AdminController {
    @Autowired
    AdminService adminService;
    @GetMapping(value = "/dayoff")
    public ResponseEntity findWaitingDayoffs() {
        return ResponseEntity.ok(adminService.findDayoffs("WAITING"));
    }

    @PostMapping(value = "/confirm")
    public ResponseEntity confirmDayoff(@RequestParam Long id) {

        adminService.confirmDayoff(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/reject")
    public ResponseEntity rejectDayoff(@RequestParam Long id) {

        adminService.rejectDayoff(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
