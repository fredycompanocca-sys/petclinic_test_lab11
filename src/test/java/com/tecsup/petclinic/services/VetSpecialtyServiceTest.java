package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    // ------------ BÚSQUEDA ---------------

    @Test
    public void testFindVetSpecialtyById() {

        // Linda Douglas (vet 3) tiene la especialidad surgery (2)
        Integer VET_ID = 3;
        Integer SPECIALTY_ID = 2;
        final LocalDate CERTIFICATION_DATE_EXPECTED = LocalDate.of(2012, 3, 20);
        final Integer YEARS_EXPECTED = 11;

        VetSpecialtyDTO vetSpecialty = null;

        try {
            vetSpecialty = this.vetSpecialtyService.findById(VET_ID, SPECIALTY_ID);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(CERTIFICATION_DATE_EXPECTED, vetSpecialty.getCertificationDate());
        assertEquals(YEARS_EXPECTED, vetSpecialty.getYearsExperience());
        assertTrue(vetSpecialty.getPrimarySpecialty());
    }

    @Test
    public void testFindSpecialtiesByVetId() {

        // Linda Douglas (vet 3) tiene 2 especialidades: surgery y dentistry
        Integer VET_ID = 3;
        int SIZE_EXPECTED = 2;

        List<VetSpecialtyDTO> vetSpecialties = this.vetSpecialtyService.findByVetId(VET_ID);

        assertEquals(SIZE_EXPECTED, vetSpecialties.size());
    }

    @Test
    public void testFindVetsBySpecialtyId() {

        // La especialidad surgery (2) la tienen 2 veterinarios: Linda Douglas y Rafael Ortega
        Integer SPECIALTY_ID = 2;
        int SIZE_EXPECTED = 2;

        List<VetSpecialtyDTO> vetSpecialties = this.vetSpecialtyService.findBySpecialtyId(SPECIALTY_ID);

        assertEquals(SIZE_EXPECTED, vetSpecialties.size());
    }

    @Test
    public void testFindAllVetSpecialties() {

        // Los datos de prueba traen 5 asignaciones; otras pruebas pueden agregar más
        int MIN_SIZE_EXPECTED = 5;

        List<VetSpecialtyDTO> vetSpecialties = this.vetSpecialtyService.findAll();

        assertTrue(vetSpecialties.size() >= MIN_SIZE_EXPECTED);
    }

    @Test
    public void testFindVetSpecialtyNotFound() {

        // El veterinario 1 no tiene la especialidad surgery (2)
        assertThrows(VetSpecialtyNotFoundException.class,
                () -> this.vetSpecialtyService.findById(1, 2));
    }

    // ------------ ASIGNACIÓN (CREACIÓN) ---------------

    @Test
    public void testCreateVetSpecialty() {

        Integer VET_ID = 1;
        Integer SPECIALTY_ID = 3;
        LocalDate CERTIFICATION_DATE = LocalDate.of(2020, 5, 10);
        Integer YEARS_EXPERIENCE = 3;
        Boolean PRIMARY = false;
        String NOTES = "Dental training";

        VetSpecialtyDTO vetSpecialtyDTO = VetSpecialtyDTO.builder()
                .vetId(VET_ID)
                .specialtyId(SPECIALTY_ID)
                .certificationDate(CERTIFICATION_DATE)
                .yearsExperience(YEARS_EXPERIENCE)
                .primarySpecialty(PRIMARY)
                .notes(NOTES)
                .build();

        VetSpecialtyDTO newVetSpecialtyDTO = this.vetSpecialtyService.create(vetSpecialtyDTO);

        log.info("VET SPECIALTY CREATED :" + newVetSpecialtyDTO);

        assertEquals(VET_ID, newVetSpecialtyDTO.getVetId());
        assertEquals(SPECIALTY_ID, newVetSpecialtyDTO.getSpecialtyId());
        assertEquals(CERTIFICATION_DATE, newVetSpecialtyDTO.getCertificationDate());
        assertEquals(YEARS_EXPERIENCE, newVetSpecialtyDTO.getYearsExperience());
        assertFalse(newVetSpecialtyDTO.getPrimarySpecialty());
        assertEquals(NOTES, newVetSpecialtyDTO.getNotes());
    }

    // ------------ ACTUALIZACIÓN ---------------

    @Test
    public void testUpdateVetSpecialty() {

        Integer VET_ID = 6;
        Integer SPECIALTY_ID = 3;
        LocalDate CERTIFICATION_DATE = LocalDate.of(2020, 5, 10);
        Integer YEARS_EXPERIENCE = 3;
        String NOTES = "Initial training";

        LocalDate UP_CERTIFICATION_DATE = LocalDate.of(2021, 5, 10);
        Integer UP_YEARS_EXPERIENCE = 4;
        String UP_NOTES = "Updated training";

        VetSpecialtyDTO vetSpecialtyDTO = VetSpecialtyDTO.builder()
                .vetId(VET_ID)
                .specialtyId(SPECIALTY_ID)
                .certificationDate(CERTIFICATION_DATE)
                .yearsExperience(YEARS_EXPERIENCE)
                .primarySpecialty(false)
                .notes(NOTES)
                .build();

        // ------------ Create ---------------

        log.info(">" + vetSpecialtyDTO);
        VetSpecialtyDTO vetSpecialtyCreated = this.vetSpecialtyService.create(vetSpecialtyDTO);
        log.info(">>" + vetSpecialtyCreated);

        // ------------ Update ---------------

        vetSpecialtyCreated.setCertificationDate(UP_CERTIFICATION_DATE);
        vetSpecialtyCreated.setYearsExperience(UP_YEARS_EXPERIENCE);
        vetSpecialtyCreated.setPrimarySpecialty(true);
        vetSpecialtyCreated.setNotes(UP_NOTES);

        VetSpecialtyDTO vetSpecialtyUpdated = this.vetSpecialtyService.update(vetSpecialtyCreated);
        log.info(">>>>" + vetSpecialtyUpdated);

        //            EXPECTED          ACTUAL
        assertEquals(VET_ID, vetSpecialtyUpdated.getVetId());
        assertEquals(SPECIALTY_ID, vetSpecialtyUpdated.getSpecialtyId());
        assertEquals(UP_CERTIFICATION_DATE, vetSpecialtyUpdated.getCertificationDate());
        assertEquals(UP_YEARS_EXPERIENCE, vetSpecialtyUpdated.getYearsExperience());
        assertTrue(vetSpecialtyUpdated.getPrimarySpecialty());
        assertEquals(UP_NOTES, vetSpecialtyUpdated.getNotes());

        // ------------ Verificación en la base ---------------

        try {
            VetSpecialtyDTO fromDb = this.vetSpecialtyService.findById(VET_ID, SPECIALTY_ID);
            assertEquals(UP_YEARS_EXPERIENCE, fromDb.getYearsExperience());
            assertEquals(UP_NOTES, fromDb.getNotes());
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }
    }

    // ------------ ELIMINACIÓN ---------------

    @Test
    public void testDeleteVetSpecialty() {

        Integer VET_ID = 1;
        Integer SPECIALTY_ID = 1;

        VetSpecialtyDTO vetSpecialtyDTO = VetSpecialtyDTO.builder()
                .vetId(VET_ID)
                .specialtyId(SPECIALTY_ID)
                .certificationDate(LocalDate.of(2022, 1, 15))
                .yearsExperience(2)
                .primarySpecialty(false)
                .notes("Temporary assignment")
                .build();

        // ------------ Create ---------------

        VetSpecialtyDTO newVetSpecialtyDTO = this.vetSpecialtyService.create(vetSpecialtyDTO);
        log.info("" + newVetSpecialtyDTO);

        // ------------ Delete ---------------

        try {
            this.vetSpecialtyService.delete(VET_ID, SPECIALTY_ID);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        assertThrows(VetSpecialtyNotFoundException.class,
                () -> this.vetSpecialtyService.findById(VET_ID, SPECIALTY_ID));
    }
}