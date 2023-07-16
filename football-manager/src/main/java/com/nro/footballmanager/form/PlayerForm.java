package com.nro.footballmanager.form;

import com.nro.footballmanager.entity.Team;
import com.nro.footballmanager.entity.enums.RoleEnum;
import lombok.Data;

import java.util.Optional;

@Data
public class PlayerForm {
    private String name;
    private RoleEnum role;
    private Integer goalsScored;
    private Long teamId;
}
