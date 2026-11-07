package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;

import java.util.List;

public interface OwnerService {

    /**
     * Crea un propietario
     */
    OwnerDTO create(OwnerDTO ownerDTO);

    /**
     * Actualiza un propietario
     */
    OwnerDTO update(OwnerDTO ownerDTO);

    /**
     * Elimina un propietario por id
     */
    void delete(Integer id) throws OwnerNotFoundException;

    /**
     * Busca un propietario por id
     */
    OwnerDTO findById(Integer id) throws OwnerNotFoundException;

    /**
     * Busca propietarios por nombre
     */
    List<OwnerDTO> findByFirstName(String firstName);

    /**
     * Busca propietarios por apellido
     */
    List<OwnerDTO> findByLastName(String lastName);

    /**
     * Busca propietarios por ciudad
     */
    List<OwnerDTO> findByCity(String city);

    /**
     * Lista todos los propietarios
     */
    List<OwnerDTO> findAll();
}