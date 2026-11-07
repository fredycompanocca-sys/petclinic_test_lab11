package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    // ------------ BÚSQUEDA ---------------

    @Test
    public void testFindVetById() {

        final String FIRST_NAME_EXPECTED = "James";
        Integer ID = 1;

        VetDTO vet = null;

        try {
            vet = this.vetService.findById(ID);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(FIRST_NAME_EXPECTED, vet.getFirstName());
    }

    @Test
    public void testFindVetByFirstName() {

        String FIND_NAME = "James";
        int SIZE_EXPECTED = 1;

        List<VetDTO> vets = this.vetService.findByFirstName(FIND_NAME);

        assertEquals(SIZE_EXPECTED, vets.size());
    }

    @Test
    public void testFindVetByLastName() {

        String FIND_LAST_NAME = "Carter";
        int SIZE_EXPECTED = 1;

        List<VetDTO> vets = this.vetService.findByLastName(FIND_LAST_NAME);

        assertEquals(SIZE_EXPECTED, vets.size());
    }

    @Test
    public void testFindVetByActive() {

        // Solo Sharon Jenkins está inactiva en los datos de prueba
        boolean ACTIVE = false;
        int SIZE_EXPECTED = 1;

        List<VetDTO> vets = this.vetService.findByActive(ACTIVE);

        assertEquals(SIZE_EXPECTED, vets.size());
    }

    // ------------ CREACIÓN ---------------

    @Test
    public void testCreateVet() {

        String FIRST_NAME = "Fredy";
        String LAST_NAME = "Companocca";
        String EMAIL = "fredy.companocca@petclinic.com";
        String PHONE = "959123456";
        Boolean ACTIVE = true;

        VetDTO vetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .phone(PHONE)
                .active(ACTIVE)
                .build();

        VetDTO newVetDTO = this.vetService.create(vetDTO);

        log.info("VET CREATED :" + newVetDTO.toString());

        assertNotNull(newVetDTO.getId());
        assertEquals(FIRST_NAME, newVetDTO.getFirstName());
        assertEquals(LAST_NAME, newVetDTO.getLastName());
        assertEquals(EMAIL, newVetDTO.getEmail());
        assertEquals(PHONE, newVetDTO.getPhone());
        assertEquals(ACTIVE, newVetDTO.getActive());
    }

    // ------------ ACTUALIZACIÓN ---------------

    @Test
    public void testUpdateVet() {

        String FIRST_NAME = "Ana";
        String LAST_NAME = "Torres";
        String EMAIL = "ana.torres@petclinic.com";
        String PHONE = "958000111";

        String UP_FIRST_NAME = "Ana Maria";
        String UP_LAST_NAME = "Torres Ruiz";
        String UP_EMAIL = "ana.maria@petclinic.com";
        String UP_PHONE = "958000222";

        VetDTO vetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .phone(PHONE)
                .active(true)
                .build();

        // ------------ Create ---------------

        log.info(">" + vetDTO);
        VetDTO vetCreated = this.vetService.create(vetDTO);
        log.info(">>" + vetCreated);

        // ------------ Update ---------------

        vetCreated.setFirstName(UP_FIRST_NAME);
        vetCreated.setLastName(UP_LAST_NAME);
        vetCreated.setEmail(UP_EMAIL);
        vetCreated.setPhone(UP_PHONE);

        VetDTO vetUpdated = this.vetService.update(vetCreated);
        log.info(">>>>" + vetUpdated);

        //            EXPECTED          ACTUAL
        assertEquals(vetCreated.getId(), vetUpdated.getId());
        assertEquals(UP_FIRST_NAME, vetUpdated.getFirstName());
        assertEquals(UP_LAST_NAME, vetUpdated.getLastName());
        assertEquals(UP_EMAIL, vetUpdated.getEmail());
        assertEquals(UP_PHONE, vetUpdated.getPhone());
    }

    // ------------ ELIMINACIÓN ---------------

    @Test
    public void testDeleteVet() {

        VetDTO vetDTO = VetDTO.builder()
                .firstName("Luis")
                .lastName("Mamani")
                .email("luis.mamani@petclinic.com")
                .phone("957111222")
                .active(true)
                .build();

        // ------------ Create ---------------

        VetDTO newVetDTO = this.vetService.create(vetDTO);
        log.info("" + newVetDTO);

        // ------------ Delete ---------------

        try {
            this.vetService.delete(newVetDTO.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        assertThrows(VetNotFoundException.class,
                () -> this.vetService.findById(newVetDTO.getId()));
    }
}