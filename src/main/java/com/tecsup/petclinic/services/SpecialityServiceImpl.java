package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialityDTO;
import com.tecsup.petclinic.entities.Speciality;
import com.tecsup.petclinic.exceptions.SpecialityNotFoundException;
import com.tecsup.petclinic.mappers.SpecialityMapper;
import com.tecsup.petclinic.repositories.SpecialityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository specialityRepository;
    private final SpecialityMapper specialityMapper;

    @Override
    public SpecialityDTO create(SpecialityDTO specialityDTO) {

        Speciality newSpeciality = specialityRepository.save(specialityMapper.mapToEntity(specialityDTO));

        return specialityMapper.mapToDto(newSpeciality);
    }

    @Override
    public SpecialityDTO update(SpecialityDTO specialityDTO) {

        Speciality updatedSpeciality = specialityRepository.save(specialityMapper.mapToEntity(specialityDTO));

        return specialityMapper.mapToDto(updatedSpeciality);
    }

    @Override
    public void delete(Integer id) throws SpecialityNotFoundException {

        SpecialityDTO speciality = findById(id);

        specialityRepository.delete(this.specialityMapper.mapToEntity(speciality));
    }

    @Override
    public SpecialityDTO findById(Integer id) throws SpecialityNotFoundException {

        Optional<Speciality> speciality = specialityRepository.findById(id);

        if (!speciality.isPresent())
            throw new SpecialityNotFoundException("Record not found...!");

        return this.specialityMapper.mapToDto(speciality.get());
    }

    @Override
    public List<SpecialityDTO> findByName(String name) {

        List<Speciality> specialities = specialityRepository.findByName(name);

        specialities.forEach(speciality -> log.info("{}", speciality));

        return specialities
                .stream()
                .map(this.specialityMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecialityDTO> findByOffice(String office) {

        List<Speciality> specialities = specialityRepository.findByOffice(office);

        specialities.forEach(speciality -> log.info("{}", speciality));

        return specialities
                .stream()
                .map(this.specialityMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecialityDTO> findAll() {

        return specialityRepository.findAll()
                .stream()
                .map(this.specialityMapper::mapToDto)
                .collect(Collectors.toList());
    }
}