package com.nro.footballmanager.form;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StadiumForm {
    private String name;
    private String location;
}
