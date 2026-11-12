package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.SpecialityDTO;
import com.tecsup.petclinic.entities.Speciality;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialityMapper {

    Speciality mapToEntity(SpecialityDTO dto);

    SpecialityDTO mapToDto(Speciality entity);
}