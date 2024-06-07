package com.example.demo3.services;

import com.example.demo3.models.Projet;
import com.example.demo3.repos.ProjetRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetService {

    private final ProjetRepository projetRepository;

    public List<Projet> getAllProjets() {
        return projetRepository.findAll();
    }

    public Projet getById(int id){
        return projetRepository.findById(id).get();
    }
    public Projet addProjet(Projet p){
        return projetRepository.save(p);
    }

    public String deleteProjet(int i){
        try {
            projetRepository.deleteById(i);
            return "Deletion was a success";
        } catch (Exception e) {
            return "Deletion failed";
        }
    }
}
