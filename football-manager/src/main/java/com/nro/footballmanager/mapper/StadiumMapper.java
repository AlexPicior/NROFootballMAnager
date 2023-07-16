package com.nro.footballmanager.mapper;

import com.nro.footballmanager.dto.StadiumDto;
import com.nro.footballmanager.entity.Stadium;
import com.nro.footballmanager.form.StadiumForm;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StadiumMapper {

    public Stadium formToStadium(StadiumForm stadiumForm)
    {
        Stadium stadium = new Stadium();
        stadium.setName(stadiumForm.getName());
        stadium.setLocation(stadiumForm.getLocation());

        return stadium;
    }

    public StadiumDto stadiumToDto(Stadium stadium)
    {
        StadiumDto stadiumDto = new StadiumDto();
        stadiumDto.setId(stadium.getId());
        stadiumDto.setName(stadium.getName());
        stadiumDto.setLocation(stadium.getLocation());

        return stadiumDto;
    }

}
