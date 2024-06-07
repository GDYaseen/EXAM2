package com.example.demo3.services;

import com.example.demo3.models.*;
import com.example.demo3.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public Employee addEmployee(Employee c){
        if(employeeRepository.findByEmail(c.getEmail())!=null){
            return null;
        }
        return employeeRepository.save(c);
    }
    public Employee findByEmail(String email){
        return employeeRepository.findByEmail(email);
    }
    public Employee getById(int id){
        return employeeRepository.findById(id).get();
    }
    public Employee updateEmployee(Employee c){
        if(employeeRepository.findByEmail(c.getEmail())!=null){
            return null;
        }
        return employeeRepository.save(c);
    }
    public String deleteEmployee(int i){
        try {
            employeeRepository.deleteById(i);
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee user = employeeRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return user;
    }
}
