package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;

import java.util.List;

public interface PetTypeService {

    /**
     * Crea un tipo de mascota
     */
    PetTypeDTO create(PetTypeDTO petTypeDTO);

    /**
     * Actualiza un tipo de mascota
     */
    PetTypeDTO update(PetTypeDTO petTypeDTO);

    /**
     * Elimina un tipo de mascota por id
     */
    void delete(Integer id) throws PetTypeNotFoundException;

    /**
     * Busca un tipo de mascota por id
     */
    PetTypeDTO findById(Integer id) throws PetTypeNotFoundException;

    /**
     * Busca tipos de mascota por nombre
     */
    List<PetTypeDTO> findByName(String name);

    /**
     * Busca tipos de mascota por estado activo
     */
    List<PetTypeDTO> findByActive(Boolean active);

    /**
     * Busca tipos de mascota por nivel de cuidado
     */
    List<PetTypeDTO> findByCareLevel(String careLevel);

    /**
     * Lista todos los tipos de mascota
     */
    List<PetTypeDTO> findAll();
}