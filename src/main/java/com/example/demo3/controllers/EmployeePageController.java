package com.example.demo3.controllers;

import com.example.demo3.models.*;
import com.example.demo3.services.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/employee")
@CrossOrigin
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePageController {

    @Autowired
    private EmployeeService userService;


    @GetMapping("")
    public String getEmployees(Authentication authentication,Model mdl){
        System.out.println("accessing /employee");
        mdl.addAttribute("employeesList", userService.getAllEmployees());
        return "EmployeeAffectation";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable (value="id") int id,Model mdl){
        userService.deleteEmployee(id);
        mdl.addAttribute("employeesList", userService.getAllEmployees());
        return "EmployeeAffectation";
    }
}
