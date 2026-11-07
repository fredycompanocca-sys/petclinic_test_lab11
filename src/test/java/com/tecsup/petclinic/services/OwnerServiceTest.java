package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    // ------------ BÚSQUEDA ---------------

    @Test
    public void testFindOwnerById() {

        final String FIRST_NAME_EXPECTED = "George";
        Integer ID = 1;

        OwnerDTO owner = null;

        try {
            owner = this.ownerService.findById(ID);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(FIRST_NAME_EXPECTED, owner.getFirstName());
    }

    @Test
    public void testFindOwnerByFirstName() {

        String FIND_NAME = "George";
        int SIZE_EXPECTED = 1;

        List<OwnerDTO> owners = this.ownerService.findByFirstName(FIND_NAME);

        assertEquals(SIZE_EXPECTED, owners.size());
    }

    @Test
    public void testFindOwnerByLastName() {

        String FIND_LAST_NAME = "Davis";
        int SIZE_EXPECTED = 2;

        List<OwnerDTO> owners = this.ownerService.findByLastName(FIND_LAST_NAME);

        assertEquals(SIZE_EXPECTED, owners.size());
    }

    @Test
    public void testFindOwnerByCity() {

        String CITY = "Madison";
        int SIZE_EXPECTED = 4;

        List<OwnerDTO> owners = this.ownerService.findByCity(CITY);

        assertEquals(SIZE_EXPECTED, owners.size());
    }

    // ------------ CREACIÓN ---------------

    @Test
    public void testCreateOwner() {

        String FIRST_NAME = "Fredy";
        String LAST_NAME = "Companocca";
        String ADDRESS = "Av. Tecsup 123";
        String CITY = "Arequipa";
        String TELEPHONE = "959123456";

        OwnerDTO ownerDTO = OwnerDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .address(ADDRESS)
                .city(CITY)
                .telephone(TELEPHONE)
                .build();

        OwnerDTO newOwnerDTO = this.ownerService.create(ownerDTO);

        log.info("OWNER CREATED :" + newOwnerDTO.toString());

        assertNotNull(newOwnerDTO.getId());
        assertEquals(FIRST_NAME, newOwnerDTO.getFirstName());
        assertEquals(LAST_NAME, newOwnerDTO.getLastName());
        assertEquals(ADDRESS, newOwnerDTO.getAddress());
        assertEquals(CITY, newOwnerDTO.getCity());
        assertEquals(TELEPHONE, newOwnerDTO.getTelephone());
    }

    // ------------ ACTUALIZACIÓN ---------------

    @Test
    public void testUpdateOwner() {

        String FIRST_NAME = "Ana";
        String LAST_NAME = "Torres";
        String ADDRESS = "Calle Lima 100";
        String CITY = "Cusco";
        String TELEPHONE = "958000111";

        String UP_FIRST_NAME = "Ana Maria";
        String UP_LAST_NAME = "Torres Ruiz";
        String UP_ADDRESS = "Calle Lima 200";
        String UP_CITY = "Arequipa";
        String UP_TELEPHONE = "958000222";

        OwnerDTO ownerDTO = OwnerDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .address(ADDRESS)
                .city(CITY)
                .telephone(TELEPHONE)
                .build();

        // ------------ Create ---------------

        log.info(">" + ownerDTO);
        OwnerDTO ownerCreated = this.ownerService.create(ownerDTO);
        log.info(">>" + ownerCreated);

        // ------------ Update ---------------

        ownerCreated.setFirstName(UP_FIRST_NAME);
        ownerCreated.setLastName(UP_LAST_NAME);
        ownerCreated.setAddress(UP_ADDRESS);
        ownerCreated.setCity(UP_CITY);
        ownerCreated.setTelephone(UP_TELEPHONE);

        OwnerDTO ownerUpdated = this.ownerService.update(ownerCreated);
        log.info(">>>>" + ownerUpdated);

        //            EXPECTED          ACTUAL
        assertEquals(ownerCreated.getId(), ownerUpdated.getId());
        assertEquals(UP_FIRST_NAME, ownerUpdated.getFirstName());
        assertEquals(UP_LAST_NAME, ownerUpdated.getLastName());
        assertEquals(UP_ADDRESS, ownerUpdated.getAddress());
        assertEquals(UP_CITY, ownerUpdated.getCity());
        assertEquals(UP_TELEPHONE, ownerUpdated.getTelephone());
    }

    // ------------ ELIMINACIÓN ---------------

    @Test
    public void testDeleteOwner() {

        OwnerDTO ownerDTO = OwnerDTO.builder()
                .firstName("Luis")
                .lastName("Mamani")
                .address("Jr. Puno 300")
                .city("Juliaca")
                .telephone("957111222")
                .build();

        // ------------ Create ---------------

        OwnerDTO newOwnerDTO = this.ownerService.create(ownerDTO);
        log.info("" + newOwnerDTO);

        // ------------ Delete ---------------

        try {
            this.ownerService.delete(newOwnerDTO.getId());
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        assertThrows(OwnerNotFoundException.class,
                () -> this.ownerService.findById(newOwnerDTO.getId()));
    }
}