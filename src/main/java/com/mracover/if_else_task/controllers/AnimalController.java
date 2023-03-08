package com.mracover.if_else_task.controllers;

import com.mracover.if_else_task.DTO.request.animal.RequestPutAnimalTypeDTO;
import com.mracover.if_else_task.DTO.request.animal.RequestGetAnimalDTO;
import com.mracover.if_else_task.DTO.request.animal.RequestPostAnimalDTO;
import com.mracover.if_else_task.DTO.request.animal.RequestPutAnimalDTO;
import com.mracover.if_else_task.DTO.response.ResponseAnimalDTO;
import com.mracover.if_else_task.components.GenderAnimal;
import com.mracover.if_else_task.components.LifeStatusAnimal;
import com.mracover.if_else_task.mappers.request.RequestGetAndPutAnimalMapper;
import com.mracover.if_else_task.mappers.request.RequestPostAnimalMapper;
import com.mracover.if_else_task.mappers.response.ResponseAnimalMapper;
import com.mracover.if_else_task.models.Animal;
import com.mracover.if_else_task.services.AnimalService;
import com.mracover.if_else_task.validators.ValidateString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;
import java.util.List;

@Validated
@RestController
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final RequestGetAndPutAnimalMapper requestGetAndPutAnimalMapper;
    private final RequestPostAnimalMapper requestPostAnimalMapper;
    private final ResponseAnimalMapper responseAnimalMapper;

    public AnimalController(AnimalService animalService,
                            RequestGetAndPutAnimalMapper requestGetAndPutAnimalMapper,
                            RequestPostAnimalMapper requestPostAnimalMapper,
                            ResponseAnimalMapper responseAnimalMapper) {
        this.animalService = animalService;
        this.requestGetAndPutAnimalMapper = requestGetAndPutAnimalMapper;
        this.requestPostAnimalMapper = requestPostAnimalMapper;
        this.responseAnimalMapper = responseAnimalMapper;
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<ResponseAnimalDTO> getAnimalById(@PathVariable("animalId") @NotNull @Positive Long id) {
        Animal animal = animalService.findAnimalById(id);
        return new ResponseEntity<>(responseAnimalMapper.animalToDTO(animal), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResponseAnimalDTO>> getAnimalsByParameters(
            @RequestParam(value = "startDateTime", required = false) String startDateTime,
            @RequestParam(value = "endDateTime", required = false) String endDateTime,
            @RequestParam(value = "chipperId", required = false) Integer chipperId,
            @RequestParam(value = "chippingLocationId", required = false) Long chippingLocationId,
            @RequestParam(value = "lifeStatus", required = false) @ValidateString(enumClazz = LifeStatusAnimal.class) String lifeStatus,
            @RequestParam(value = "gender", required = false) @ValidateString(enumClazz = GenderAnimal.class) String gender,
            @RequestParam(value = "from", defaultValue = "0", required = false) @NotNull @PositiveOrZero Integer from,
            @RequestParam(value = "size", defaultValue = "10", required = false) @NotNull @Positive Integer size) {
        OffsetDateTime offsetStartDateTime = null;
        OffsetDateTime offsetEndDateTime = null;
        if (startDateTime != null) {
            offsetStartDateTime = OffsetDateTime.parse(startDateTime);
        }
        if (endDateTime != null) {
            offsetEndDateTime = OffsetDateTime.parse(endDateTime);
        }

        RequestGetAnimalDTO requestGetAnimalDTO = new RequestGetAnimalDTO();
        requestGetAnimalDTO.setChipperId(chipperId);
        requestGetAnimalDTO.setChippingLocationId(chippingLocationId);
        requestGetAnimalDTO.setLifeStatus(lifeStatus);
        requestGetAnimalDTO.setGender(gender);
        List<Animal> animalList = animalService.findAnimalsByParameters(
                requestGetAndPutAnimalMapper.dtoToAnimal(requestGetAnimalDTO), offsetStartDateTime, offsetEndDateTime, from, size);
        return new ResponseEntity<>(responseAnimalMapper.animalListToListDTO(animalList), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseAnimalDTO> addNewAnimal(@Valid @RequestBody RequestPostAnimalDTO requestPostAnimalDTO) {
        for (Long s : requestPostAnimalDTO.getAnimalTypes()) {
            if (s == null || s <= 0 ) {
                throw new ValidationException();
            }
        }
        if (requestPostAnimalDTO.getAnimalTypes().isEmpty()) {
            throw new ValidationException();
        }

        Animal animal = animalService.addNewAnimal(requestPostAnimalMapper.dtoToAnimal(requestPostAnimalDTO));
        return new ResponseEntity<>(responseAnimalMapper.animalToDTO(animal), HttpStatus.CREATED);
    }

    @PostMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<ResponseAnimalDTO> addTypeAnimalToAnimal(@PathVariable("animalId") @NotNull @Positive Long animalId,
                                                                   @PathVariable("typeId") @NotNull @Positive Long typeId) {
        Animal animal = animalService.addAnimalTypeToAnimal(animalId, typeId);
        return new ResponseEntity<>(responseAnimalMapper.animalToDTO(animal), HttpStatus.CREATED);
    }

    @PutMapping("/{animalId}")
    public ResponseEntity<ResponseAnimalDTO> updateAnimal(@PathVariable("animalId") @NotNull @Positive Long id,
                                                          @Valid @RequestBody RequestPutAnimalDTO requestPutAnimalDTO) {
        Animal animal = requestGetAndPutAnimalMapper.dtoPutToAnimal(requestPutAnimalDTO);
        animal.setId(id);
        return new ResponseEntity<>(responseAnimalMapper.animalToDTO(animalService.updateAnimal(animal)), HttpStatus.OK);
    }

    @PutMapping("/{animalId}/types")
    public ResponseEntity<ResponseAnimalDTO> updateTypeAnimal(@PathVariable("animalId") @NotNull @Positive Long id,
                                                              @Valid @RequestBody RequestPutAnimalTypeDTO requestPutAnimalTypeDTO) {
        return new ResponseEntity<>(responseAnimalMapper.animalToDTO(animalService.updateAnimalType(id, requestPutAnimalTypeDTO)), HttpStatus.OK);
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable("animalId") @NotNull @Positive Long id) {
        animalService.deleteAnimalById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<ResponseAnimalDTO> deleteAnimalTypeFromAnimal(@PathVariable("animalId") @NotNull @Positive Long animalId,
                                                                        @PathVariable("typeId") @NotNull @Positive Long typeId) {
        return new ResponseEntity<>(responseAnimalMapper.animalToDTO(animalService.deleteAnimalTypeFromAnimal(animalId, typeId)), HttpStatus.OK);
    }
}
