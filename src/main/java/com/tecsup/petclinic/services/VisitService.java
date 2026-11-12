package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface VisitService {

    /**
     * Crea una visita
     */
    VisitDTO create(VisitDTO visitDTO);

    /**
     * Actualiza una visita
     */
    VisitDTO update(VisitDTO visitDTO);

    /**
     * Elimina una visita por id
     */
    void delete(Integer id) throws VisitNotFoundException;

    /**
     * Busca una visita por id
     */
    VisitDTO findById(Integer id) throws VisitNotFoundException;

    /**
     * Busca las visitas de una mascota
     */
    List<VisitDTO> findByPetId(Integer petId);

    /**
     * Busca las visitas atendidas por un veterinario
     */
    List<VisitDTO> findByVetId(Integer vetId);

    /**
     * Busca las visitas de una fecha
     */
    List<VisitDTO> findByVisitDate(LocalDate visitDate);

    /**
     * Lista todas las visitas
     */
    List<VisitDTO> findAll();
}