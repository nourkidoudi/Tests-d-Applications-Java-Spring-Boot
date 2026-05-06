package com.example;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeService {
    private final EmployeRepository repository;

    public EmployeService(EmployeRepository repository) {
        this.repository = repository;
    }

    public List<Employe> listerTous() {
        return repository.findAll();
    }

    public Optional<Employe> trouverParId(Long id) {
        return repository.findById(id);
    }

    public Employe creer(EmployeDTO dto) {
        Employe employe = new Employe(dto.getNom(), dto.getDepartement(), dto.getSalaire());
        return repository.save(employe);
    }

    public void supprimer(Long id) {
        repository.deleteById(id);
    }
}
