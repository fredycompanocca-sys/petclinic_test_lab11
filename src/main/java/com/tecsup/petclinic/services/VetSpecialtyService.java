package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;

import java.util.List;

public interface VetSpecialtyService {

    /**
     * Asigna una especialidad a un veterinario
     */
    VetSpecialtyDTO create(VetSpecialtyDTO vetSpecialtyDTO);

    /**
     * Actualiza los datos de una asignación
     */
    VetSpecialtyDTO update(VetSpecialtyDTO vetSpecialtyDTO);

    /**
     * Elimina la relación entre un veterinario y una especialidad
     */
    void delete(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

    /**
     * Busca una asignación por veterinario y especialidad
     */
    VetSpecialtyDTO findById(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

    /**
     * Busca las especialidades de un veterinario
     */
    List<VetSpecialtyDTO> findByVetId(Integer vetId);

    /**
     * Busca los veterinarios de una especialidad
     */
    List<VetSpecialtyDTO> findBySpecialtyId(Integer specialtyId);

    /**
     * Lista todas las asignaciones
     */
    List<VetSpecialtyDTO> findAll();
}