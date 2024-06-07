package com.example.demo3.repos;

import com.example.demo3.models.Projet;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet,Integer> {

    @Override
    <S extends Projet> List<S> findAll(Example<S> example);
}
