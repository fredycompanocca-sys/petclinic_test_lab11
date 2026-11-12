package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialityDTO;
import com.tecsup.petclinic.exceptions.SpecialityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class SpecialityServiceTest {

    @Autowired
    private SpecialityService specialityService;

    // ------------ BÚSQUEDA ---------------

    @Test
    public void testFindSpecialityById() {

        final String NAME_EXPECTED = "radiology";
        Integer ID = 1;

        SpecialityDTO speciality = null;

        try {
            speciality = this.specialityService.findById(ID);
        } catch (SpecialityNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(NAME_EXPECTED, speciality.getName());
    }

    @Test
    public void testFindSpecialityByName() {

        String FIND_NAME = "surgery";
        int SIZE_EXPECTED = 1;

        List<SpecialityDTO> specialities = this.specialityService.findByName(FIND_NAME);

        assertEquals(SIZE_EXPECTED, specialities.size());
    }

    @Test
    public void testFindSpecialityByOffice() {

        String OFFICE = "Terranova";
        int SIZE_EXPECTED = 1;

        List<SpecialityDTO> specialities = this.specialityService.findByOffice(OFFICE);

        assertEquals(SIZE_EXPECTED, specialities.size());
    }

    @Test
    public void testFindAllSpecialities() {

        // Los datos de prueba traen 3 especialidades; otras pruebas pueden agregar más
        int MIN_SIZE_EXPECTED = 3;

        List<SpecialityDTO> specialities = this.specialityService.findAll();

        assertTrue(specialities.size() >= MIN_SIZE_EXPECTED);
    }

    // ------------ CREACIÓN ---------------

    @Test
    public void testCreateSpeciality() {

        String NAME = "cardiology";
        String OFFICE = "Arequipa Norte";
        Integer OPEN_HOUR = 7;
        Integer CLOSE_HOUR = 15;

        SpecialityDTO specialityDTO = SpecialityDTO.builder()
                .name(NAME)
                .office(OFFICE)
                .openHour(OPEN_HOUR)
                .closeHour(CLOSE_HOUR)
                .build();

        SpecialityDTO newSpecialityDTO = this.specialityService.create(specialityDTO);

        log.info("SPECIALITY CREATED :" + newSpecialityDTO);

        assertNotNull(newSpecialityDTO.getId());
        assertEquals(NAME, newSpecialityDTO.getName());
        assertEquals(OFFICE, newSpecialityDTO.getOffice());
        assertEquals(OPEN_HOUR, newSpecialityDTO.getOpenHour());
        assertEquals(CLOSE_HOUR, newSpecialityDTO.getCloseHour());
    }

    // ------------ ACTUALIZACIÓN ---------------

    @Test
    public void testUpdateSpeciality() {

        String NAME = "dermatology";
        String OFFICE = "Cusco Centro";
        Integer OPEN_HOUR = 8;
        Integer CLOSE_HOUR = 14;

        String UP_NAME = "dermatology advanced";
        String UP_OFFICE = "Cusco Sur";
        Integer UP_OPEN_HOUR = 9;
        Integer UP_CLOSE_HOUR = 17;

        SpecialityDTO specialityDTO = SpecialityDTO.builder()
                .name(NAME)
                .office(OFFICE)
                .openHour(OPEN_HOUR)
                .closeHour(CLOSE_HOUR)
                .build();

        // ------------ Create ---------------

        log.info(">" + specialityDTO);
        SpecialityDTO specialityCreated = this.specialityService.create(specialityDTO);
        log.info(">>" + specialityCreated);

        // ------------ Update ---------------

        specialityCreated.setName(UP_NAME);
        specialityCreated.setOffice(UP_OFFICE);
        specialityCreated.setOpenHour(UP_OPEN_HOUR);
        specialityCreated.setCloseHour(UP_CLOSE_HOUR);

        SpecialityDTO specialityUpdated = this.specialityService.update(specialityCreated);
        log.info(">>>>" + specialityUpdated);

        //            EXPECTED          ACTUAL
        assertEquals(specialityCreated.getId(), specialityUpdated.getId());
        assertEquals(UP_NAME, specialityUpdated.getName());
        assertEquals(UP_OFFICE, specialityUpdated.getOffice());
        assertEquals(UP_OPEN_HOUR, specialityUpdated.getOpenHour());
        assertEquals(UP_CLOSE_HOUR, specialityUpdated.getCloseHour());
    }

    // ------------ ELIMINACIÓN ---------------

    @Test
    public void testDeleteSpeciality() {

        SpecialityDTO specialityDTO = SpecialityDTO.builder()
                .name("neurology")
                .office("Puno Plaza")
                .openHour(10)
                .closeHour(16)
                .build();

        // ------------ Create ---------------

        SpecialityDTO newSpecialityDTO = this.specialityService.create(specialityDTO);
        log.info("" + newSpecialityDTO);

        // ------------ Delete ---------------

        try {
            this.specialityService.delete(newSpecialityDTO.getId());
        } catch (SpecialityNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        assertThrows(SpecialityNotFoundException.class,
                () -> this.specialityService.findById(newSpecialityDTO.getId()));
    }
}