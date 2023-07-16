package com.nro.footballmanager.dto;

import com.nro.footballmanager.entity.Team;
import com.nro.footballmanager.entity.enums.RoleEnum;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PlayerDto {
    private Long id;
    private String name;
    private Integer goalsScored;
    private RoleEnum role;
    private Long teamId;
    private String nameTeam;
}
