package com.nro.footballmanager.controller;

import com.nro.footballmanager.dto.StadiumDto;
import com.nro.footballmanager.form.StadiumForm;
import com.nro.footballmanager.service.implementation.StadiumServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stadiums")
@CrossOrigin(origins = {"http://localhost:8090", "http://localhost:3000"})
public class StadiumController {

    private final StadiumServiceImpl stadiumService;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<StadiumDto> addStadium(@RequestBody StadiumForm stadiumForm)
    {
        StadiumDto stadiumDto = stadiumService.createStadium(stadiumForm);

        return new ResponseEntity<>(stadiumDto, HttpStatus.CREATED);
    }


    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<StadiumDto>> getAllStadiums()
    {
        List<StadiumDto> stadiumsDto = stadiumService.getAllStadiums();

        return new ResponseEntity<>(stadiumsDto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<StadiumDto> getStadiumById(@PathVariable Long id)
    {
        try {
            StadiumDto stadiumDto = stadiumService.getStadiumById(id);

            return new ResponseEntity<>(stadiumDto, HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<StadiumDto> updateStadium(@PathVariable Long id, @RequestBody StadiumForm stadiumForm)
    {
        try {
            StadiumDto stadiumDto = stadiumService.updateStadium(id, stadiumForm);

            return new ResponseEntity<>(stadiumDto, HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(path = "")
    public ResponseEntity<HttpStatus> deleteAllStadiums()
    {
        stadiumService.deleteAllStadiums();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteStadiumById(@PathVariable Long id)
    {
        try {
            stadiumService.deleteStadiumById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}











