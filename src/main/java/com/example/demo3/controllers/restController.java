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

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@AllArgsConstructor
@NoArgsConstructor
public class restController {
    private EmployeeService userService;
    private ProjetService projetService;

    @GetMapping("/getEmployees")
    public ResponseEntity<List<Employee>> getEmployees(){
        return new ResponseEntity<>(userService.getAllEmployees(),HttpStatus.OK);
    }

    @GetMapping("/getProjets")
    public ResponseEntity<List<Projet>> getProjets(){
        return new ResponseEntity<>(projetService.getAllProjets(),HttpStatus.OK);
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeRequest req){
        Employee e = userService.addEmployee(Employee.builder().email(req.getEmail())
            .name(req.getName())
            .password(req.getPassword())
            .skills(Arrays.asList(req.getSkills().split(";")))
            .build());
        return new  ResponseEntity<>(e,HttpStatus.OK);
    }

    @PostMapping("/addProjet")
    public ResponseEntity<Projet> addProjet(@RequestBody ProjetRequest req){
        Projet e = projetService.addProjet(Projet.builder()
            .name(req.getName())
            .budget(req.getBudget())
            
            .build());
        return new  ResponseEntity<>(e,HttpStatus.OK);
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody EmployeeRequest req,@PathVariable int id){
        Employee e = userService.getById(id);
        e = userService.addEmployee(Employee.builder().email(req.getEmail())
            .name(req.getName())
            .id(e.getId())
            .password(req.getPassword())
            .skills(Arrays.asList(req.getSkills().split(";")))
            .build());
        return new  ResponseEntity<>(e,HttpStatus.OK);
    }

    @PutMapping("/updateProjet/{id}")
    public ResponseEntity<Projet> updateProjet(@RequestBody ProjetRequest req,@PathVariable int id){
        Projet e = projetService.getById(id);
        e = projetService.addProjet(Projet.builder()
        .id(e.getId())
            .name(req.getName())
            .budget(req.getBudget())
            
            .build());
        return new  ResponseEntity<>(e,HttpStatus.OK);
    }

    @PutMapping("/deleteEmployee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id){
        userService.deleteEmployee(id);
        return new  ResponseEntity<>("employee deleted",HttpStatus.OK);
    }

    @PutMapping("/deleteProjet/{id}")
    public ResponseEntity<?> deleteProjet(@PathVariable int id){
        projetService.deleteProjet(id);
        return new  ResponseEntity<>("projet deleted",HttpStatus.OK);
    }

    
}
