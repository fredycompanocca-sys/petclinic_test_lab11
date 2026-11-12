package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialityRepository
        extends JpaRepository<Speciality, Integer> {

    // Fetch specialities by name
    List<Speciality> findByName(String name);

    // Fetch specialities by office
    List<Speciality> findByOffice(String office);

    @Override
    List<Speciality> findAll();
}