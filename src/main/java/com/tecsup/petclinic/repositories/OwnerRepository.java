package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository
        extends JpaRepository<Owner, Integer> {

    // Fetch owners by first name
    List<Owner> findByFirstName(String firstName);

    // Fetch owners by last name
    List<Owner> findByLastName(String lastName);

    // Fetch owners by city
    List<Owner> findByCity(String city);

    @Override
    List<Owner> findAll();
}