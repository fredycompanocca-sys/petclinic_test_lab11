package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialityDTO;
import com.tecsup.petclinic.exceptions.SpecialityNotFoundException;

import java.util.List;

public interface SpecialityService {

    /**
     * Crea una especialidad
     */
    SpecialityDTO create(SpecialityDTO specialityDTO);

    /**
     * Actualiza una especialidad
     */
    SpecialityDTO update(SpecialityDTO specialityDTO);

    /**
     * Elimina una especialidad por id
     */
    void delete(Integer id) throws SpecialityNotFoundException;

    /**
     * Busca una especialidad por id
     */
    SpecialityDTO findById(Integer id) throws SpecialityNotFoundException;

    /**
     * Busca especialidades por nombre
     */
    List<SpecialityDTO> findByName(String name);

    /**
     * Busca especialidades por consultorio
     */
    List<SpecialityDTO> findByOffice(String office);

    /**
     * Lista todas las especialidades
     */
    List<SpecialityDTO> findAll();
}