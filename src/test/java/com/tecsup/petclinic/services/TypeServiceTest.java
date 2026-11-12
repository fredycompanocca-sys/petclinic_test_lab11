package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class TypeServiceTest {

    @Autowired
    private PetTypeService petTypeService;

    // ------------ BÚSQUEDA ---------------

    @Test
    public void testFindTypeById() {

        final String NAME_EXPECTED = "hamster";
        final Integer LIFESPAN_EXPECTED = 3;
        final String CARE_LEVEL_EXPECTED = "low";
        Integer ID = 5;

        PetTypeDTO petType = null;

        try {
            petType = this.petTypeService.findById(ID);
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(NAME_EXPECTED, petType.getName());
        assertEquals(LIFESPAN_EXPECTED, petType.getAverageLifespan());
        assertEquals(CARE_LEVEL_EXPECTED, petType.getCareLevel());
    }

    @Test
    public void testFindTypeByName() {

        String FIND_NAME = "dog";
        int SIZE_EXPECTED = 1;

        List<PetTypeDTO> petTypes = this.petTypeService.findByName(FIND_NAME);

        assertEquals(SIZE_EXPECTED, petTypes.size());
    }

    @Test
    public void testFindTypeByActive() {

        // Solo "snake" está inactivo en los datos de prueba
        boolean ACTIVE = false;
        int SIZE_EXPECTED = 1;

        List<PetTypeDTO> petTypes = this.petTypeService.findByActive(ACTIVE);

        assertEquals(SIZE_EXPECTED, petTypes.size());
    }

    @Test
    public void testFindTypeByCareLevel() {

        // Solo "hamster" tiene nivel de cuidado "low" en los datos de prueba
        String CARE_LEVEL = "low";
        int SIZE_EXPECTED = 1;

        List<PetTypeDTO> petTypes = this.petTypeService.findByCareLevel(CARE_LEVEL);

        assertEquals(SIZE_EXPECTED, petTypes.size());
    }

    @Test
    public void testFindAllTypes() {

        // Los datos de prueba traen 8 tipos; otras pruebas pueden agregar más
        int MIN_SIZE_EXPECTED = 8;

        List<PetTypeDTO> petTypes = this.petTypeService.findAll();

        assertTrue(petTypes.size() >= MIN_SIZE_EXPECTED);
    }

    // ------------ CREACIÓN ---------------

    @Test
    public void testCreateType() {

        String NAME = "parrot";
        String DESCRIPTION = "Talking bird";
        Boolean ACTIVE = true;
        String SIZE_CATEGORY = "small";
        Integer AVERAGE_LIFESPAN = 25;
        String CARE_LEVEL = "medium";

        PetTypeDTO petTypeDTO = PetTypeDTO.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .active(ACTIVE)
                .sizeCategory(SIZE_CATEGORY)
                .averageLifespan(AVERAGE_LIFESPAN)
                .careLevel(CARE_LEVEL)
                .build();

        PetTypeDTO newPetTypeDTO = this.petTypeService.create(petTypeDTO);

        log.info("TYPE CREATED :" + newPetTypeDTO);

        assertNotNull(newPetTypeDTO.getId());
        assertEquals(NAME, newPetTypeDTO.getName());
        assertEquals(DESCRIPTION, newPetTypeDTO.getDescription());
        assertEquals(ACTIVE, newPetTypeDTO.getActive());
        assertEquals(SIZE_CATEGORY, newPetTypeDTO.getSizeCategory());
        assertEquals(AVERAGE_LIFESPAN, newPetTypeDTO.getAverageLifespan());
        assertEquals(CARE_LEVEL, newPetTypeDTO.getCareLevel());
    }

    // ------------ ACTUALIZACIÓN ---------------

    @Test
    public void testUpdateType() {

        String NAME = "guinea pig";
        String DESCRIPTION = "Small rodent";
        String SIZE_CATEGORY = "small";
        Integer AVERAGE_LIFESPAN = 6;
        String CARE_LEVEL = "medium";

        String UP_NAME = "guinea pig adult";
        String UP_DESCRIPTION = "Adult small rodent";
        String UP_SIZE_CATEGORY = "medium";
        Integer UP_AVERAGE_LIFESPAN = 8;
        String UP_CARE_LEVEL = "medium-high";

        PetTypeDTO petTypeDTO = PetTypeDTO.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .sizeCategory(SIZE_CATEGORY)
                .averageLifespan(AVERAGE_LIFESPAN)
                .careLevel(CARE_LEVEL)
                .build();

        // ------------ Create ---------------

        log.info(">" + petTypeDTO);
        PetTypeDTO petTypeCreated = this.petTypeService.create(petTypeDTO);
        log.info(">>" + petTypeCreated);

        // ------------ Update ---------------

        petTypeCreated.setName(UP_NAME);
        petTypeCreated.setDescription(UP_DESCRIPTION);
        petTypeCreated.setSizeCategory(UP_SIZE_CATEGORY);
        petTypeCreated.setAverageLifespan(UP_AVERAGE_LIFESPAN);
        petTypeCreated.setCareLevel(UP_CARE_LEVEL);

        PetTypeDTO petTypeUpdated = this.petTypeService.update(petTypeCreated);
        log.info(">>>>" + petTypeUpdated);

        //            EXPECTED          ACTUAL
        assertEquals(petTypeCreated.getId(), petTypeUpdated.getId());
        assertEquals(UP_NAME, petTypeUpdated.getName());
        assertEquals(UP_DESCRIPTION, petTypeUpdated.getDescription());
        assertEquals(UP_SIZE_CATEGORY, petTypeUpdated.getSizeCategory());
        assertEquals(UP_AVERAGE_LIFESPAN, petTypeUpdated.getAverageLifespan());
        assertEquals(UP_CARE_LEVEL, petTypeUpdated.getCareLevel());
    }

    // ------------ ELIMINACIÓN ---------------

    @Test
    public void testDeleteType() {

        PetTypeDTO petTypeDTO = PetTypeDTO.builder()
                .name("fish")
                .description("Aquatic animal")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(5)
                .careLevel("medium")
                .build();

        // ------------ Create ---------------

        PetTypeDTO newPetTypeDTO = this.petTypeService.create(petTypeDTO);
        log.info("" + newPetTypeDTO);

        // ------------ Delete ---------------

        try {
            this.petTypeService.delete(newPetTypeDTO.getId());
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        assertThrows(PetTypeNotFoundException.class,
                () -> this.petTypeService.findById(newPetTypeDTO.getId()));
    }
}