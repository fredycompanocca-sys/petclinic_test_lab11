package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;

import java.util.List;

public interface VetService {

    /**
     * Crea un veterinario
     */
    VetDTO create(VetDTO vetDTO);

    /**
     * Actualiza un veterinario
     */
    VetDTO update(VetDTO vetDTO);

    /**
     * Elimina un veterinario por id
     */
    void delete(Integer id) throws VetNotFoundException;

    /**
     * Busca un veterinario por id
     */
    VetDTO findById(Integer id) throws VetNotFoundException;

    /**
     * Busca veterinarios por nombre
     */
    List<VetDTO> findByFirstName(String firstName);

    /**
     * Busca veterinarios por apellido
     */
    List<VetDTO> findByLastName(String lastName);

    /**
     * Busca veterinarios por estado activo
     */
    List<VetDTO> findByActive(Boolean active);

    /**
     * Lista todos los veterinarios
     */
    List<VetDTO> findAll();
}