package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.dto.StadiumDto;
import com.nro.footballmanager.entity.Game;
import com.nro.footballmanager.entity.Stadium;
import com.nro.footballmanager.form.StadiumForm;
import com.nro.footballmanager.mapper.StadiumMapper;
import com.nro.footballmanager.repository.GameRepository;
import com.nro.footballmanager.repository.StadiumRepository;
import com.nro.footballmanager.service.StadiumService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StadiumServiceImpl  implements StadiumService {

    private final StadiumRepository stadiumRepository;
    private final StadiumMapper stadiumMapper;

    private final GameRepository gameRepository;

    public StadiumDto createStadium(StadiumForm stadiumForm)
    {
        Stadium createdStadium = stadiumRepository.save(stadiumMapper.formToStadium(stadiumForm));
        return stadiumMapper.stadiumToDto(createdStadium);
    }


    public List<StadiumDto> getAllStadiums()
    {
        List<Stadium> stadiums = stadiumRepository.findAll();
        List<StadiumDto> stadiumsDto = new ArrayList<>();
        for (Stadium stadium: stadiums) {
            stadiumsDto.add(stadiumMapper.stadiumToDto(stadium));
        }

        return stadiumsDto;
    }

    public StadiumDto getStadiumById(Long id)
    {
        Optional<Stadium> stadiumOptional = stadiumRepository.findById(id);

        if(stadiumOptional.isPresent())
        {
            Stadium stadium = stadiumOptional.get();
            return stadiumMapper.stadiumToDto(stadium);
        }
        else
        {
            throw new EntityNotFoundException("Stadium not found");
        }
    }

    public Stadium getStadiumEntityById(Long id)
    {
        Optional<Stadium> stadiumOptional = stadiumRepository.findById(id);

        if(stadiumOptional.isPresent())
        {
            return stadiumOptional.get();

        }
        else
        {
            throw new EntityNotFoundException("Stadium not found");
        }
    }


    public StadiumDto updateStadium(Long id, StadiumForm stadiumForm)
    {
        Optional<Stadium> stadiumOptional = stadiumRepository.findById(id);

        if(stadiumOptional.isPresent())
        {
            Stadium stadium = stadiumOptional.get();

            if(stadiumForm.getName() != null)
            {
                stadium.setName(stadiumForm.getName());
            }
            if(stadiumForm.getLocation() != null)
            {
                stadium.setLocation(stadiumForm.getLocation());
            }

            Stadium createdStadium = stadiumRepository.save(stadium);
            return stadiumMapper.stadiumToDto(createdStadium);
        }
        else
        {
            throw new EntityNotFoundException("Stadium not found");
        }
    }


    public void deleteAllStadiums()
    {
        List<Game> games = gameRepository.findAll();
        for (Game game: games) {
            game.setStadium(null);
        }
        gameRepository.saveAll(games);
        stadiumRepository.deleteAll();
    }

    public void deleteStadiumById(Long id)
    {
        Optional<Stadium> stadiumOptional = stadiumRepository.findById(id);

        if(stadiumOptional.isPresent())
        {
            List<Game> games = gameRepository.findByStadium(stadiumOptional.get());
            for (Game game: games) {
                game.setStadium(null);
            }
            stadiumRepository.delete(stadiumOptional.get());
        }
        else
        {
            throw new EntityNotFoundException("Stadium not found");
        }
    }
}













