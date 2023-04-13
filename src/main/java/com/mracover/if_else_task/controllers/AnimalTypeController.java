package com.mracover.if_else_task.controllers;

import com.mracover.if_else_task.DTO.request.RequestAnimalTypeDTO;
import com.mracover.if_else_task.DTO.response.ResponseAnimalTypeDTO;
import com.mracover.if_else_task.mappers.request.RequestAnimalTypeMapper;
import com.mracover.if_else_task.mappers.response.ResponseAnimalTypeMapper;
import com.mracover.if_else_task.models.animalModels.AnimalType;
import com.mracover.if_else_task.services.AnimalTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/animals/types")
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;
    private final RequestAnimalTypeMapper requestAnimalTypeMapper;
    private final ResponseAnimalTypeMapper responseAnimalTypeMapper;

    public AnimalTypeController(AnimalTypeService animalTypeService,
                                RequestAnimalTypeMapper requestAnimalTypeMapper,
                                ResponseAnimalTypeMapper responseAnimalTypeMapper) {
        this.animalTypeService = animalTypeService;
        this.requestAnimalTypeMapper = requestAnimalTypeMapper;
        this.responseAnimalTypeMapper = responseAnimalTypeMapper;
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<ResponseAnimalTypeDTO> getAccountById(@PathVariable("typeId") @NotNull @Positive Long id) {
        AnimalType animalType = animalTypeService.findAnimalTypeById(id);
        return new ResponseEntity<>(responseAnimalTypeMapper.AnimalTypeToDTO(animalType), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHIPPER')")
    @PostMapping
    public ResponseEntity<ResponseAnimalTypeDTO> addNewAnimalType(@Valid @RequestBody RequestAnimalTypeDTO requestAnimalTypeDTO) {
        AnimalType animalType = animalTypeService.addNewAnimalType(requestAnimalTypeMapper.dtoToAnimalType(requestAnimalTypeDTO));
        return new ResponseEntity<>(responseAnimalTypeMapper.AnimalTypeToDTO(animalType), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHIPPER')")
    @PutMapping("/{typeId}")
    public ResponseEntity<ResponseAnimalTypeDTO> updateAnimalType(@PathVariable("typeId") @NotNull @Positive Long id,
                                                                  @Valid @RequestBody RequestAnimalTypeDTO requestAnimalTypeDTO) {
        requestAnimalTypeDTO.setId(id);
        AnimalType animalType = animalTypeService.updateAnimalType(requestAnimalTypeMapper.dtoToAnimalType(requestAnimalTypeDTO));
        return new ResponseEntity<>(responseAnimalTypeMapper.AnimalTypeToDTO(animalType), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{typeId}")
    public ResponseEntity<Void> deleteAnimalType(@PathVariable("typeId") @NotNull @Positive Long id) {
        animalTypeService.deleteAnimalTypeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
