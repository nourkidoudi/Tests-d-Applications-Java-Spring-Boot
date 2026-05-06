package com.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
    List<Employe> findByDepartement(String departement);

    @Query("SELECT e FROM Employe e WHERE e.salaire > :seuil")
    List<Employe> findBySalaireSuperieurA(@Param("seuil") double seuil);
}
