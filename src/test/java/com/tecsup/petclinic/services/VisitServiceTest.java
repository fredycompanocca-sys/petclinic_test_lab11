package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    // ------------ BÚSQUEDA ---------------

    @Test
    public void testFindVisitById() {

        final String DESCRIPTION_EXPECTED = "rabies shot";
        final Integer PET_ID_EXPECTED = 7;
        final BigDecimal COST_EXPECTED = new BigDecimal("45.00");
        Integer ID = 1;

        VisitDTO visit = null;

        try {
            visit = this.visitService.findById(ID);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(DESCRIPTION_EXPECTED, visit.getDescription());
        assertEquals(PET_ID_EXPECTED, visit.getPetId());
        assertEquals(0, COST_EXPECTED.compareTo(visit.getCost()));
    }

    @Test
    public void testFindVisitByPetId() {

        // La mascota 7 (Samantha) tiene 2 visitas en los datos de prueba
        Integer PET_ID = 7;
        int SIZE_EXPECTED = 2;

        List<VisitDTO> visits = this.visitService.findByPetId(PET_ID);

        assertEquals(SIZE_EXPECTED, visits.size());
    }

    @Test
    public void testFindVisitByVetId() {

        // La veterinaria 2 (Helen Leary) atendió 2 visitas en los datos de prueba
        Integer VET_ID = 2;
        int SIZE_EXPECTED = 2;

        List<VisitDTO> visits = this.visitService.findByVetId(VET_ID);

        assertEquals(SIZE_EXPECTED, visits.size());
    }

    @Test
    public void testFindVisitByVisitDate() {

        LocalDate VISIT_DATE = LocalDate.of(2024, 1, 15);
        int SIZE_EXPECTED = 1;

        List<VisitDTO> visits = this.visitService.findByVisitDate(VISIT_DATE);

        assertEquals(SIZE_EXPECTED, visits.size());
    }

    @Test
    public void testFindAllVisits() {

        // Los datos de prueba traen 6 visitas; otras pruebas pueden agregar más
        int MIN_SIZE_EXPECTED = 6;

        List<VisitDTO> visits = this.visitService.findAll();

        assertTrue(visits.size() >= MIN_SIZE_EXPECTED);
    }

    // ------------ CREACIÓN ---------------

    @Test
    public void testCreateVisit() {

        Integer PET_ID = 9;
        Integer VET_ID = 5;
        LocalDate VISIT_DATE = LocalDate.of(2026, 11, 11);
        String DESCRIPTION = "vaccination";
        BigDecimal COST = new BigDecimal("80.50");

        VisitDTO visitDTO = VisitDTO.builder()
                .petId(PET_ID)
                .vetId(VET_ID)
                .visitDate(VISIT_DATE)
                .description(DESCRIPTION)
                .cost(COST)
                .build();

        VisitDTO newVisitDTO = this.visitService.create(visitDTO);

        log.info("VISIT CREATED :" + newVisitDTO);

        assertNotNull(newVisitDTO.getId());
        assertEquals(PET_ID, newVisitDTO.getPetId());
        assertEquals(VET_ID, newVisitDTO.getVetId());
        assertEquals(VISIT_DATE, newVisitDTO.getVisitDate());
        assertEquals(DESCRIPTION, newVisitDTO.getDescription());
        assertEquals(0, COST.compareTo(newVisitDTO.getCost()));
    }

    // ------------ ACTUALIZACIÓN ---------------

    @Test
    public void testUpdateVisit() {

        Integer PET_ID = 10;
        Integer VET_ID = 5;
        LocalDate VISIT_DATE = LocalDate.of(2026, 11, 12);
        String DESCRIPTION = "general checkup";
        BigDecimal COST = new BigDecimal("60.00");

        Integer UP_PET_ID = 11;
        Integer UP_VET_ID = 6;
        LocalDate UP_VISIT_DATE = LocalDate.of(2026, 11, 20);
        String UP_DESCRIPTION = "dental cleaning";
        BigDecimal UP_COST = new BigDecimal("120.75");

        VisitDTO visitDTO = VisitDTO.builder()
                .petId(PET_ID)
                .vetId(VET_ID)
                .visitDate(VISIT_DATE)
                .description(DESCRIPTION)
                .cost(COST)
                .build();

        // ------------ Create ---------------

        log.info(">" + visitDTO);
        VisitDTO visitCreated = this.visitService.create(visitDTO);
        log.info(">>" + visitCreated);

        // ------------ Update ---------------

        visitCreated.setPetId(UP_PET_ID);
        visitCreated.setVetId(UP_VET_ID);
        visitCreated.setVisitDate(UP_VISIT_DATE);
        visitCreated.setDescription(UP_DESCRIPTION);
        visitCreated.setCost(UP_COST);

        VisitDTO visitUpdated = this.visitService.update(visitCreated);
        log.info(">>>>" + visitUpdated);

        //            EXPECTED          ACTUAL
        assertEquals(visitCreated.getId(), visitUpdated.getId());
        assertEquals(UP_PET_ID, visitUpdated.getPetId());
        assertEquals(UP_VET_ID, visitUpdated.getVetId());
        assertEquals(UP_VISIT_DATE, visitUpdated.getVisitDate());
        assertEquals(UP_DESCRIPTION, visitUpdated.getDescription());
        assertEquals(0, UP_COST.compareTo(visitUpdated.getCost()));
    }

    // ------------ ELIMINACIÓN ---------------

    @Test
    public void testDeleteVisit() {

        VisitDTO visitDTO = VisitDTO.builder()
                .petId(12)
                .vetId(5)
                .visitDate(LocalDate.of(2026, 11, 13))
                .description("deworming")
                .cost(new BigDecimal("35.00"))
                .build();

        // ------------ Create ---------------

        VisitDTO newVisitDTO = this.visitService.create(visitDTO);
        log.info("" + newVisitDTO);

        // ------------ Delete ---------------

        try {
            this.visitService.delete(newVisitDTO.getId());
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        assertThrows(VisitNotFoundException.class,
                () -> this.visitService.findById(newVisitDTO.getId()));
    }
}