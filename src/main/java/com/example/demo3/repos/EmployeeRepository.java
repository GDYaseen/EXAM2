package com.example.demo3.repos;

import com.example.demo3.models.Employee;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    @Override
    <S extends Employee> List<S> findAll(Example<S> example);

    public Employee findByEmail(String email);
}
