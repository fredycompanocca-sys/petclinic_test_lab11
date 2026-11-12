package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VetSpecialtyMapper {

    @Mapping(source = "vetId", target = "id.vetId")
    @Mapping(source = "specialtyId", target = "id.specialtyId")
    VetSpecialty mapToEntity(VetSpecialtyDTO dto);

    @Mapping(source = "id.vetId", target = "vetId")
    @Mapping(source = "id.specialtyId", target = "specialtyId")
    VetSpecialtyDTO mapToDto(VetSpecialty entity);
}