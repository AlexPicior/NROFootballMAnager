package com.nro.footballmanager.service;

import com.nro.footballmanager.dto.StadiumDto;
import com.nro.footballmanager.form.StadiumForm;

import java.util.List;

public interface StadiumService {
    StadiumDto createStadium(StadiumForm stadiumForm);
    List<StadiumDto> getAllStadiums();
    StadiumDto getStadiumById(Long id);
    StadiumDto updateStadium(Long id, StadiumForm stadiumForm);
    void deleteAllStadiums();
    void deleteStadiumById(Long id);
}
